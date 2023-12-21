package tech.reliab.course.kurbanovar.bank.service.impl;

import tech.reliab.course.kurbanovar.bank.entity.Bank;
import tech.reliab.course.kurbanovar.bank.entity.PaymentAccount;
import tech.reliab.course.kurbanovar.bank.entity.User;

import tech.reliab.course.kurbanovar.bank.service.BankService;
import tech.reliab.course.kurbanovar.bank.service.PaymentAccountService;

import java.util.ArrayList;
import java.util.Objects;

public class PaymentAccountServiceImpl implements PaymentAccountService {

    @Override
    public PaymentAccount create(Integer id, User user, Bank bank) {

        return new PaymentAccount(id, user, bank);
    }

    @Override
    public void addMoney(PaymentAccount payAcc, Double sumMoney) {

        payAcc.setSum(payAcc.getSum() + sumMoney);
    }

    @Override
    public void subtractMoney(PaymentAccount payAcc, Double sumMoney) {

        payAcc.setSum(payAcc.getSum() - sumMoney);
    }

    @Override
    public void addPayment(Integer id, User user, Bank bank ){

        if (user.getPaymentAccounts() != null){
            for (PaymentAccount paymentAccount: user.getPaymentAccounts())
            {
                if (paymentAccount.getBank() == bank)
                    return;
            }
        }
        PaymentAccount paymentAccount = new PaymentAccount(id, user, bank);
        ArrayList<PaymentAccount> paymentAccounts;
        if(user.getPaymentAccounts() == null){
            paymentAccounts = new ArrayList<>();
        }
        else{
            paymentAccounts = user.getPaymentAccounts();
        }
        paymentAccounts.add(paymentAccount);
        BankService bankService = new BankServiceImpl();
        bankService.addUser(bank,user);
        user.setPaymentAccounts(paymentAccounts);
    }

    @Override
    public void DeletePayment(User user, Bank bank, PaymentAccount paymentAccount){
        if (paymentAccount.getSum() < 0){
            System.out.println("На счету не погашен долг");
        }
        else {
            ArrayList<PaymentAccount> paymentAccounts = user.getPaymentAccounts();
            paymentAccounts.remove(paymentAccount);
            user.setPaymentAccounts(paymentAccounts);
            boolean flag = false;

            for (PaymentAccount account : paymentAccounts) {
                if (Objects.equals(account.getBank(), bank)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                BankService bankService = new BankServiceImpl();
                bankService.deleteUser(bank, user);
            }
        }
    }
}
