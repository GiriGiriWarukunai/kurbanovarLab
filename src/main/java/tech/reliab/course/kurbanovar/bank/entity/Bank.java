package tech.reliab.course.kurbanovar.bank.entity;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private Integer id;
    private String name;
    private ArrayList<BankOffice> bankOffices;
    private ArrayList<BankATM> bankATMS;
    private ArrayList<Employee> employees;
    private ArrayList<User> clients;
    private Integer rating;
    private Double money;
    private Double interestRate;

    public Bank(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.bankOffices = new ArrayList<>();
        this.bankATMS = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.clients = new ArrayList<>();
        Random random = new Random();
        this.rating = random.nextInt(0, 90);
        this.money = random.nextDouble(0, 1000000000);
        this.interestRate = 20.0- this.getRating() /5.0;;
    }

    @Override
    public String toString(){
        return  "\nid: " + id +
                "\nНазвание банка: " + name +
                "\nКоличество офисов: " + getBankOffices().size() +
                "\nКоличество банкоматов: " + getBankATMS().size() +
                "\nКоличество сотрудников: " + getEmployees().size() +
                "\nКоличество клиентов: " + getClients().size() +
                "\nРейтинг: " + rating +
                "\nДенежная сумма: " + String.format("%.2f",money) +
                "\nПроцентная ставка: " + String.format("%.2f",interestRate);
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public ArrayList<BankOffice> getBankOffices() {
        return bankOffices;
    }

    public void setBankOffices(ArrayList<BankOffice> bankOffices) {
        this.bankOffices = bankOffices;
    }

    public void addBankOffice(BankOffice bankOffice) {
        this.bankOffices.add(bankOffice);
    }

    public ArrayList<BankATM> getBankATMS() {
        return bankATMS;
    }

    public void setBankATMS(ArrayList<BankATM> bankATMS) {
        this.bankATMS = bankATMS;
    }

    public void addBankATM(BankATM bankATM) {
        this.bankATMS.add(bankATM) ;
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

    public ArrayList<User> getClients() {
        return clients;
    }

    public void setClients(ArrayList<User> clients) {
        this.clients = clients;
    }

    public void addClients(User client) {
        this.clients.add(client);
    }
}