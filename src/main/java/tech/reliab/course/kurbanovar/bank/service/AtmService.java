package tech.reliab.course.kurbanovar.bank.service;

import tech.reliab.course.kurbanovar.bank.entity.Bank;
import tech.reliab.course.kurbanovar.bank.entity.BankATM;
import tech.reliab.course.kurbanovar.bank.entity.BankOffice;
import tech.reliab.course.kurbanovar.bank.entity.Employee;
import tech.reliab.course.kurbanovar.bank.utils.StatusATM;


public interface AtmService {
    /*Создание банкомата*/
    BankATM create (Integer id, String name, StatusATM status, Double maintenanceCost);

    /*Добавление суммы денег в банкомат, а, соответственно, добавление суммы денег в офис банка
     * и в банк, которому принадлежит данный банкомат, с учётом того, работает ли банкомат. Если он
     * работает, то деньги вносятся и возвращается true, иначе false*/
    void addMoney(BankATM bankATM, Double sumMoney);

    /*Вычитание суммы денег из банкомата, и, соответственно, вычитание суммы денег из офиса банка и банка,
     * которому принадлежит данный банкомат, с проверкой того, достаточно ли денег в банкомате, чтобы их вычесть.
     * Если не достаточно, то возвращается false, иначе true. И с учётом того, работает ли банкомат и есть ли в
     * нём деньги. Если он работает и в нём есть деньги, то деньги вычитаются и возвращается true, иначе false*/
    void subtractMoney(BankATM bankATM, Double sumMoney);

    /*Включить Банкомат*/
    void turnOnATM(BankATM bankATM);

    //Выключить Бакомат
    void turnOffATM(BankATM bankATM);
}