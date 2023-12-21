package tech.reliab.course.kurbanovar.bank.entity;

import tech.reliab.course.kurbanovar.bank.entity.common.Account;

public class PaymentAccount extends Account {
   private Double sum;

    public PaymentAccount(Integer id, User user, Bank bank) {
        super(id,user,bank);
        this.sum = 0.0D;
    }

    @Override
    public String toString() {
        String str = "\nid " + id +
                "\nБанк: " + bank.getName() +
                "\nПользователь: " + user.getFullName() +
                "\nСумма денег: " + String.format("%.2f",sum) +
                "\n";
        return str;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
