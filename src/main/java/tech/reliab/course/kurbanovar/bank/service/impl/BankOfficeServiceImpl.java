package tech.reliab.course.kurbanovar.bank.service.impl;

import tech.reliab.course.kurbanovar.bank.entity.*;
import tech.reliab.course.kurbanovar.bank.exceptions.BankOfficeException;
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
        try{
            Double sumBank = office.getBank().getMoney();
            Double sumOffice = office.getMoney();
            if (sumOffice < sumMoney)
                throw new BankOfficeException("Попытка cнять деньги",String.format( "У банка не хватает %f денег",sumMoney-sumOffice));
            office.setMoney(sumOffice - sumMoney);
            office.getBank().setMoney(sumBank - sumMoney);
        } catch (BankOfficeException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void addATM(BankOffice office, BankATM bankATM) {
        try{
            if (!office.getMaySetATM())
                throw new BankOfficeException("Попытка добавить банкомат","В этот офис нельзя добавить банкомат");
            if (office.getBank() == null)
                throw new BankOfficeException("Попытка добавить банкомат","Офис не принадлежит ни одному банку");
            if (bankATM.getBankOffice() != null)
                throw new BankOfficeException("Попытка добавить банкомат","Банкомат установлен в другом банке");
            if (!Objects.equals(bankATM.getBank(), office.getBank()))
                throw new BankOfficeException("Попытка добавить банкомат","Банкомат и офис принадлежат разным банкам");

            if (office.getBankATMS() == null) {
                ArrayList<BankATM> array = new ArrayList<>();
                array.add(bankATM);
                office.setBankATMS(array);
            }
            else{
                ArrayList<BankATM> array = office.getBankATMS();
                array.add(bankATM);
                office.setBankATMS(array);
            }
            bankATM.setBankOffice(office);
        } catch (BankOfficeException e) {
            System.err.println(e.getMessage());
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
        try{

            if (!Objects.equals(employee.getBank(), office.getBank()))
                throw new BankOfficeException("Попытка добавить Сотрудника","Сотрудник работает в другом банке");

            if (!employee.getDistantWork())
                throw new BankOfficeException("Попытка добавить Сотрудника","Сотрудник работает удалённо");

            if (employee.getBankOffice() != null)
                throw new BankOfficeException("Попытка добавить Сотрудника","Сотрудник Рабочий работает в другом офисе");

            EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
            ArrayList<Employee> array;
            if (office.getEmployees() == null) {
                array = new ArrayList<>();
                array.add(employee);
            }
            else{
                array = office.getEmployees();
                array.add(employee);
            }
            office.setEmployees(array);
            employeeService.toOfficeWork(employee);
            employee.setBankOffice(office);

        } catch (BankOfficeException e) {
            System.err.println(e.getMessage());
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