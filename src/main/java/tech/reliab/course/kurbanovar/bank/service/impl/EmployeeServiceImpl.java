package tech.reliab.course.kurbanovar.bank.service.impl;

import tech.reliab.course.kurbanovar.bank.entity.Bank;
import tech.reliab.course.kurbanovar.bank.entity.BankATM;
import tech.reliab.course.kurbanovar.bank.entity.BankOffice;
import tech.reliab.course.kurbanovar.bank.entity.Employee;
import tech.reliab.course.kurbanovar.bank.exceptions.EmployeeException;
import tech.reliab.course.kurbanovar.bank.service.EmployeeService;

import java.util.Date;
import java.util.Objects;

public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public Employee create(Integer id, String name, String surname, Date birthday, String job, Double salary) {
        return new Employee(id, name, surname, birthday, job, salary);
    }

    @Override
    public void toDistantWork(Employee employee) {
        employee.setDistantWork(Boolean.TRUE);
        permissionForCredit(employee, false);
    }

    @Override
    public void toOfficeWork(Employee employee) {

        employee.setDistantWork(Boolean.FALSE);

    }

    @Override
    public void permissionForCredit(Employee employee, Boolean flag){
        employee.setCanLend(flag);

    }

    @Override
    public void setWorkerToBankomat(BankATM bankATM, Employee employee){
        try{
            if (!Objects.equals(bankATM.getBankOffice(),employee.getBankOffice())){
                throw new EmployeeException("Попытка привязать рабочего с банкоматом","Рабочий и банкомат не находятся в 1 офисе");
            }
            if (Objects.equals(bankATM.getEmployee(),employee))
                return;
            employee.setBankATM(bankATM);
            bankATM.setEmployee(employee);
            AtmServiceImpl atmService = new AtmServiceImpl();
            atmService.turnOnATM(bankATM);
        } catch (EmployeeException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void removeWorkerFromBankomat(BankATM bankATM, Employee employee){
        if (!Objects.equals(employee.getBankATM(),bankATM))
            return;

        AtmServiceImpl atmService = new AtmServiceImpl();
        bankATM.setEmployee(null);
        employee.setBankATM(null);
        atmService.turnOffATM(bankATM);
    }
}
