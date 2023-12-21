package tech.reliab.course.kurbanovar.bank.service;

import tech.reliab.course.kurbanovar.bank.entity.*;

public interface BankService {
    /*Создание банка*/
    Bank create(Integer id, String name);
    /*Добавление суммы денег в банк*/
    void addMoney(Bank bank, Double sumMoney);
    /*Вычитание суммы денег из банка*/
    void subtractMoney(Bank bank, Double sumMoney);

    //Добавить банкомат банку
    void addBankATM(Bank bank, BankATM bankATM);

    //Убрать банкомат у банка. Для этого надо убрать банкомат у соответсвующего
    //банковского офиса
    void deleteBankATM(Bank bank, BankATM bankATM);

    //Добавить работника
    void addEmployee(Bank bank, Employee employee);

    //Удалить работника. Для этого уберите все
    void deleteEmployee(Bank bank, Employee employee);

    //Добавить банковский офис.
    void addOffice(Bank bank, BankOffice bankOffice);

    //Убрать банковский офис. Все банкоматы этого офиса убираются банку
    void deleteOffice(Bank bank, BankOffice bankOffice);

    //Добавить юзера
    void addUser(Bank bank, User user);

    void deleteUser(Bank bank, User user);

    String getInfo(Bank bank);
}