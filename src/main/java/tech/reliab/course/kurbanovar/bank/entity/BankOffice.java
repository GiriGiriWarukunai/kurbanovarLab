package tech.reliab.course.kurbanovar.bank.entity;

import java.util.ArrayList;

public class BankOffice{
    private Integer id;
    private String name;
    private Bank bank;
    private String address;
    private Boolean status;
    private Boolean maySetATM;
    private ArrayList<BankATM> bankATMS;
    private ArrayList<Employee> employees;
    private Boolean mayApplyCredit;
    private Boolean mayWithdrawMoney;
    private Boolean mayDepositMoney;
    private Double money;
    private Double rentCost;

    public BankOffice( Integer id, String name, String address, Boolean status,
                       Double rentCost) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.status = status;
        this.maySetATM = true;
        this.bankATMS = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.mayDepositMoney = true;
        this.mayApplyCredit = true;
        this.mayWithdrawMoney = true;
        this.money = 0.0D;
        this.rentCost = rentCost;
    }


    @Override
    public String toString() {
        String str =  "\nid: " + id + "\nНазвание офиса: " + name +
                "\nБанк: " + bank.getName() +
                "\nАдрес: " + address +
                "\nСтатус: ";
        if (status){
            str+= "работает";
        }
        else{
            str+= "не работает";
        }
        if (maySetATM){
            str += "\nМожно размещать банкоматы";
        }
        else{
            str += "\nНельзя размещать банкоматы";
        }

        if (maySetATM){
            str += "\nКоличество банкоматов: " + bankATMS.size();
        }

        str += "\nКоличество сотрудников: " + getEmployees().size();

        if (mayWithdrawMoney){
            str += "\nРаботает на выдачу денег";
        }
        else{
            str += "\nНе работает на выдачу денег";
        }

        if (mayDepositMoney){
            str += "\nМожно внести деньги";
        }
        else{
            str += "\nНельзя внести деньги";
        }
        if (mayApplyCredit){
            str += "\nВыдают кредит";
        }
        else{
            str += "\nНе выдают кредит";
        }
        str += "\nДенежная сумма: " + String.format("%.2f",money) +
                "\nАрендная плата: " + String.format("%.2f",rentCost);

        return str;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getMaySetATM() {
        return maySetATM;
    }

    public void setMaySetATM(Boolean maySetATM) {
        this.maySetATM = maySetATM;
    }

    public ArrayList<BankATM> getBankATMS() {
        return bankATMS;
    }

    public void setBankATMS(ArrayList<BankATM> bankATMS) {
        this.bankATMS = bankATMS;
    }

    public void addBankATMS(BankATM bankATM) {
        this.bankATMS.add(bankATM);
    }

    public Boolean getMayApplyCredit() {
        return mayApplyCredit;
    }

    public void setMayApplyCredit(Boolean mayApplyCredit) {
        this.mayApplyCredit = mayApplyCredit;
    }

    public Boolean getMayWithdrawMoney() {
        return mayWithdrawMoney;
    }

    public void setMayWithdrawMoney(Boolean mayWithdrawMoney) {
        this.mayWithdrawMoney = mayWithdrawMoney;
    }

    public Boolean getMayDepositMoney() {
        return mayDepositMoney;
    }

    public void setMayDepositMoney(Boolean mayDepositMoney) {
        this.mayDepositMoney = mayDepositMoney;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getRentCost() {
        return rentCost;
    }

    public void setRentCost(Double rentCost) {
        this.rentCost = rentCost;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployees(Employee employee) {
        this.employees.add(employee);
    }
}
