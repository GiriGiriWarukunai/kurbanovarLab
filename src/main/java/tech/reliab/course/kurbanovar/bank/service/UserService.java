package tech.reliab.course.kurbanovar.bank.service;

import tech.reliab.course.kurbanovar.bank.entity.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public interface UserService {
    /*Создание пользователя*/
    User create(Integer id, String name, String surname, Date birthday, String work);
    /*Смена пользователем работы, а соответственно, и заработной платы, и пересчёт его кредитного рейтинга*/
    void changeWork(User user, String newWork, Double newMonthSalary);
    //Вывести информацию о клиенте
    String getInfo(User user);

    void saveToFile(String fileName, Bank bank, User user) throws IOException;
    void updateFromFile(String fileName, Bank bank, ArrayList<Bank> banks, ArrayList<User> users) throws IOException;


}
