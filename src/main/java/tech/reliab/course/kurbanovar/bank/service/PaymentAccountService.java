package tech.reliab.course.kurbanovar.bank.service;

import tech.reliab.course.kurbanovar.bank.entity.Bank;
import tech.reliab.course.kurbanovar.bank.entity.PaymentAccount;
import tech.reliab.course.kurbanovar.bank.entity.User;

public interface PaymentAccountService {
    /*Создание платежного счета*/
    PaymentAccount create(Integer id, User user, Bank bank);
    /*Добавление суммы денег на платёжный счёт*/
    void addMoney(PaymentAccount payAcc, Double sumMoney);

    /*Вычитание суммы денег с платёжного счёта*/
    void subtractMoney(PaymentAccount payAcc, Double sumMoney);

    //ввести новый платёжный счёт
    void addPayment(Integer id, User user, Bank bank );

    void DeletePayment(User user, Bank bank, PaymentAccount paymentAccount);
}
