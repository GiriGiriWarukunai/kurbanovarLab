package tech.reliab.course.kurbanovar.bank.service.impl;

import tech.reliab.course.kurbanovar.bank.entity.*;
import tech.reliab.course.kurbanovar.bank.service.BankOfficeService;

import java.util.ArrayList;
import java.util.Objects;

public class BankOfficeServiceImpl implements BankOfficeService {

    @Override
    public BankOffice create(Integer id, String name, String address, Boolean status, Double rentCost) {
        return new BankOffice(id, name, address, status, rentCost);
    }

    @Override
    public void addMoney(BankOffice office, Double sumMoney) {
        Double sumBank = office.getBank().getMoney();
        Double sumOffice = office.getMoney();
        office.setMoney(sumOffice + sumMoney);
        office.getBank().setMoney(sumBank + sumMoney);
    }

    @Override
    public void subtractMoney(BankOffice office, Double sumMoney) {
        Double sumBank = office.getBank().getMoney();
        Double sumOffice = office.getMoney();
        if (sumOffice < sumMoney)
            System.out.printf("У банка не хватает %.2f денег%n",sumMoney-sumOffice);
        else{
            office.setMoney(sumOffice - sumMoney);
            office.getBank().setMoney(sumBank - sumMoney);
        }
    }

    @Override
    public void addATM(BankOffice office, BankATM bankATM) {

        if (!office.getMaySetATM())
            System.out.println("В этот офис нельзя добавить банкомат");
        if (office.getBank() == null)
            System.out.println("Офис не принадлежит ни одному банку");
        if (bankATM.getBankOffice() != null)
            System.out.println("Банкомат установлен в другом банке");
        if (!Objects.equals(bankATM.getBank(), office.getBank()))
            System.out.println("Банкомат и офис принадлежат разным банкам");
        else {

            ArrayList<BankATM> array;
            if (office.getBankATMS() == null) {
                array = new ArrayList<>();
            } else {
                array = office.getBankATMS();
            }
            array.add(bankATM);
            office.setBankATMS(array);
            bankATM.setBankOffice(office);
        }

    }

    @Override
    public void deleteATM(BankOffice office, BankATM bankATM) {
        if (!Objects.equals(bankATM.getBankOffice(),office))
            return;
        ArrayList<BankATM> array = office.getBankATMS();
        array.remove(bankATM);
        if (array.isEmpty())
            office.setBankATMS(null);
        else
            office.setBankATMS(array);
        bankATM.setBankOffice(null);
    }

    @Override
    public void addEmployee(BankOffice office, Employee employee){
        if (!Objects.equals(employee.getBank(), office.getBank()))
            System.out.println("Сотрудник работает в другом банке");

        if (!employee.getDistantWork())
            System.out.println("Сотрудник работает удалённо");

        if (employee.getBankOffice() != null)
            System.out.println("Сотрудник работает в другом офисе");
        else {

            EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
            ArrayList<Employee> array;
            if (office.getEmployees() == null) {
                array = new ArrayList<>();
                array.add(employee);
            } else {
                array = office.getEmployees();
                array.add(employee);
            }
            office.setEmployees(array);
            employeeService.toOfficeWork(employee);
            employee.setBankOffice(office);
        }
    }

    @Override
    public void deleteEmployee(BankOffice office, Employee employee){
        if (!Objects.equals(employee.getBankOffice(),office))
            return;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        ArrayList<Employee> array = office.getEmployees();
        array.remove(employee);
        if (array.isEmpty())
            office.setBankATMS(null);
        else
            office.setEmployees(array);

        employeeService.toDistantWork(employee);
        employee.setBankOffice(null);
    }
}