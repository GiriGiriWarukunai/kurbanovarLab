package tech.reliab.course.kurbanovar.bank;

import tech.reliab.course.kurbanovar.bank.entity.*;
import tech.reliab.course.kurbanovar.bank.service.*;
import tech.reliab.course.kurbanovar.bank.service.impl.*;
import tech.reliab.course.kurbanovar.bank.utils.StatusATM;

import java.io.Console;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    static void get_banks_console(ArrayList <Bank> bank) {
        int size = bank.size();
        for (int i = 0; i < size; i++) {
            System.out.printf("Id:%d Название:%s%n", bank.get(i).getId(), bank.get(i).getName());
        }
    }

    static void lab_2()  {
        ArrayList<Bank> banks = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        BankService bankService = new BankServiceImpl();
        AtmService atmService = new AtmServiceImpl();
        BankOfficeService bankOfficeService = new BankOfficeServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();
        CreditAccountService creditAccountService = new CreditAccountServiceImpl();
        PaymentAccountService paymentAccountService = new PaymentAccountServiceImpl();
        UserService userService = new UserServiceImpl();

        for (int i = 0; i < 5; i++) {
            Bank bank = bankService.create(i, String.format("Bank#%d", i));
            banks.add(bank);
            for (int j = 0; j < 3; j++) {
                BankOffice bankOffice = bankOfficeService.create(i * 3 + j, String.format("OfficeName#%d", i * 3 + j), String.format("Adress#%d", i * 3 + j), true, 500.0);
                bankService.addOffice(banks.get(i), bankOffice);
                bankOfficeService.addMoney(bankOffice, 2000000.0);

                BankATM bankATM = atmService.create(i * 3 + j, String.format("BankATM#%d", i * 3 + j),
                        StatusATM.Work, 500.0);
                bankService.addBankATM(banks.get(i), bankATM);

                bankOfficeService.addATM(banks.get(i).getBankOffices().get(j), banks.get(i).getBankATMS().get(j));
                atmService.addMoney(bankATM, 1231323103.0);
                for (int k = 0; k < 5; k++) {
                    int temp = 5 * (j + 3 * i) + k;
                    Employee employee = employeeService.create(temp, String.format("Grigori%d", temp), String.format("Tatarov%d", temp), new Date(19081917), String.format("work%d", k), (double) 500 * k);

                    bankService.addEmployee(banks.get(i), employee);
                    bankOfficeService.addEmployee(banks.get(i).getBankOffices().get(j), employee);
                }
                employeeService.setWorkerToBankomat(banks.get(i).getBankOffices().get(j).getBankATMS().get(0),
                        banks.get(i).getBankOffices().get(j).getEmployees().get(0));
            }
        }

        for (int i = 0; i < 5; i++) {
            User user = userService.create(i, String.format("Appolon#%d", i), String.format("Bringest#%d", i), new Date(10112000), String.format("Work#%d", i));
            users.add(user);

            for (int j = 0; j < 2; j++) {

                int bankIndex;
                if (i + j == 5)
                    bankIndex = 0;
                else
                    bankIndex = i + j;

                paymentAccountService.addPayment(i * 2 + j, users.get(i), banks.get(bankIndex));
                ArrayList <PaymentAccount> paymentAccounts = users.get(i).getPaymentAccounts();
                paymentAccountService.addMoney(paymentAccounts.get(j), 100.0 * (j + i));
                creditAccountService.openCredit(i * 2 + j, users.get(i),
                        banks.get(bankIndex).getBankOffices().get(0), banks.get(bankIndex).getBankOffices().get(0).getEmployees().get(0),
                        LocalDate.now(), 24, 100.0);
            }
        }

        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("Что вы хотите сделать?\n1.Посмотреть информацию о банке\n2.Вывести все свои счета в консоль\n3.Выйти");
            int number = sc.nextInt();
            switch (number) {
                case 1:
                    get_banks_console(banks);
                    System.out.println("Введите Id банка");
                    int num = sc.nextInt();
                    if (num < 5)
                        System.out.println(bankService.getInfo(banks.get(num)));
                    else
                        System.out.println("Банка с таким Id не существует");
                    break;
                case 2:
                    System.out.println("Введите Ваш id");
                    int us = sc.nextInt();
                    if (us < 5)
                        System.out.println(userService.getInfo(users.get(us)));
                    else
                        System.out.println("Пользователя с таким Id не существует");
                    break;
                case 3:
                    flag = false;
                    break;
            }
        }
    }


    public static void main(String[] args) {
        lab_2();
    }
}