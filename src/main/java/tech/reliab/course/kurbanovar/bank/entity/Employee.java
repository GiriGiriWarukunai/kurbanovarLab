package tech.reliab.course.kurbanovar.bank.entity;

import java.util.Date;

import tech.reliab.course.kurbanovar.bank.entity.common.Person;

public class Employee extends Person {
    private String job;
    private Bank bank;
    private Boolean distantWork;
    private BankOffice bankOffice;

    private BankATM bankATM;
    private Boolean canLend;
    private Double salary;

    public Employee(Integer id, String name, String surname, Date birthday,  String job, Double salary ) {

        super(id,name,surname,birthday);
        this.bank = null;
        this.job = job;
        this.salary = salary;
        this.distantWork = true;
        this.canLend = true;
        this.bankOffice= null;
        this.bankATM = null;
    }

    @Override
    public String toString() {
        String str =  "\nEmployee \nИмя: " + getFullName() +
                "\nДата рождения: " + birthday +
                "\nДолжность: " + job +
                "\nБанк: " + bank.getName();
        if (!distantWork){
            str += "\nРаботает в офисе " + bankOffice.getName();

            if (canLend){
                str += "\nМожет выдавать кредиты";
            }
            else{
                str += "\nНе Может выдавать кредиты";
            }
        }
        else{
            str += "\nРаботает удалённо";
        }

        str += "\nЗарплата: " + String.format("%.2f", salary);

        return str;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Boolean getDistantWork() {
        return distantWork;
    }

    public void setDistantWork(Boolean distantWork) {
        this.distantWork = distantWork;
    }

    public BankOffice getBankOffice() {
        return bankOffice;
    }

    public void setBankOffice(BankOffice bankOffice) {
        this.bankOffice = bankOffice;
    }

    public Boolean getCanLend() {
        return canLend;
    }

    public void setCanLend(Boolean canLend) {
        this.canLend = canLend;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public BankATM getBankATM() {
        return bankATM;
    }

    public void setBankATM(BankATM bankATM) {
        this.bankATM = bankATM;
    }
}
