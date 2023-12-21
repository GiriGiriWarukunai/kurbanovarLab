package tech.reliab.course.kurbanovar.bank.entity;

import tech.reliab.course.kurbanovar.bank.utils.StatusATM;

public class BankATM {
    private Integer id;
    private String name;
    private BankOffice bankOffice;
    private StatusATM status;
    private Bank bank;
    private  Employee employee;
    private Boolean canPayMoney;
    private Boolean canDepositMoney;
    private Double money;
    private Double maintenanceCost;


    public BankATM(Integer id, String name, StatusATM status, Double maintenanceCost) {

        this.id = id;
        this.name = name;
        this.status = status;
        this.canPayMoney = false;
        this.canDepositMoney = false;
        this.maintenanceCost = maintenanceCost;
        this.bank = null;
        this.employee = null;
        this.money = 0.0D;
        this.bankOffice = null;
    }

    @Override
    public String toString(){
        String str =  "\nid: " + id + "\nНазвание банкомата: " + name;
        if (bankOffice != null)
           str += "\nАдрес: " + bankOffice.getAddress();
        else {
            str += "Находится на складе банка.";
            return str;
        }
        str += "\nСтатус: ";
        switch (status){
            case Work-> str += "Работает";
            case NotWork-> str += "Не работает";
            case NoMoney-> str += "Нет денег";
        }
        str += "\nОбслуживающий сотрудник: " + employee.getFullName();
        if (canPayMoney){
            str += "\nРаботает на выдачу денег";
        }
        else{
            str += "\nНе работает на выдачу денег";
        }
        if (canDepositMoney){
            str += "\nМожно внести деньги";
        }
        else{
            str += "\nНельзя внести деньги";
        }
        str += "\nДенежная сумма: " + String.format("%.2f",money) +
                "\nСтоимость обслуживания: " + String.format("%.2f",maintenanceCost);
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

    public BankOffice getBankOffice() {
        return bankOffice;
    }

    public void setBankOffice(BankOffice bankOffice) {
        this.bankOffice = bankOffice;
    }

    public StatusATM getStatus() {
        return status;
    }

    public void setStatus(StatusATM status) {
        this.status = status;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Boolean getCanPayMoney() {
        return canPayMoney;
    }

    public void setCanPayMoney(Boolean canPayMoney) {
        this.canPayMoney = canPayMoney;
    }

    public Boolean getCanDepositMoney() {
        return canDepositMoney;
    }

    public void setCanDepositMoney(Boolean canDepositMoney) {
        this.canDepositMoney = canDepositMoney;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(Double maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }
}
