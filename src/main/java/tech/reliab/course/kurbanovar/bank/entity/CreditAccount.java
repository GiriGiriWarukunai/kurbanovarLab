package tech.reliab.course.kurbanovar.bank.entity;

import tech.reliab.course.kurbanovar.bank.entity.common.Account;
import tech.reliab.course.kurbanovar.bank.entity.jsonClasses.JsonCreditAcc;

import java.time.LocalDate;


public class CreditAccount extends Account {
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer countMonth;
    private Double amount;
    private Double mountsPayment;


    private Double interestRate;
    private Employee employee;
    private PaymentAccount paymentAccount;

    public CreditAccount(Integer id, User user, Bank bank, Employee employee, PaymentAccount paymentAccount,
                         LocalDate startDate, Integer countMonth, Double amount ) {
        super(id,user,bank);
        this.startDate = startDate;
        this.countMonth = countMonth;
        this.endDate = startDate.plusMonths(this.countMonth);
        this.amount = amount;
        this.interestRate = bank.getInterestRate();
        this.mountsPayment = (1+ interestRate/100)*amount/countMonth;
        this.employee = employee;
        this.paymentAccount = paymentAccount;
    }

    public void updateFromJsonClass(JsonCreditAcc jsonCreditAcc, Bank bank) {
        this.setId(jsonCreditAcc.getId());
        this.setBank(bank);
        this.getUser().setId(jsonCreditAcc.getUserID());
        this.getPaymentAccount().setId(jsonCreditAcc.getPayAccID());
        this.getEmployee().setId(jsonCreditAcc.getEmployeeID());
        this.setStartDate(LocalDate.parse(jsonCreditAcc.getStartDate()));
        this.setEndDate(LocalDate.parse(jsonCreditAcc.getEndDate()));
        this.setCountMonth(jsonCreditAcc.getCountMonth());
        this.setAmount(jsonCreditAcc.getAmount());
        this.setMountsPayment(jsonCreditAcc.getMountsPayment());
        this.setInterestRate(jsonCreditAcc.getInterestRate());
    }

    @Override
    public String toString() {
        String str = "\nid: " + id +
                "\nБанк: " + bank.getName() +
                "\nПользователь: " + user.getFullName() +
                "\nКоличество месяцев: " + countMonth +
                "\nПромежутки: " + startDate.toString() + " - " + endDate.toString() +
                "\nСумма кредита: " + String.format("%.2f",amount) +
                "\nПроцентная ставка: " + String.format("%.2f",interestRate) + "%" +
                "\nЕжемесячный платёж: " + String.format("%.2f",mountsPayment) +
                "\nСотрудник, который выдал кредит: " + employee.getFullName() +
                "\nId платёжного счёта: " + paymentAccount.getId().toString()+
                "\n";
        return str;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getCountMonth() {
        return countMonth;
    }

    public void setCountMonth(Integer countMonth) {
        this.countMonth = countMonth;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getMountsPayment() {
        return mountsPayment;
    }

    public void setMountsPayment(Double mountsPayment) {
        this.mountsPayment = mountsPayment;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public PaymentAccount getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(PaymentAccount paymentAccount) {
        this.paymentAccount = paymentAccount;
    }
}
