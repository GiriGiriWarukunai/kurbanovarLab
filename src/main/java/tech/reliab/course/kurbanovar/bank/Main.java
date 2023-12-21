package tech.reliab.course.kurbanovar.bank;

import tech.reliab.course.kurbanovar.bank.entity.*;
import tech.reliab.course.kurbanovar.bank.exceptions.*;
import tech.reliab.course.kurbanovar.bank.service.*;
import tech.reliab.course.kurbanovar.bank.service.impl.*;
import tech.reliab.course.kurbanovar.bank.utils.StatusATM;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class Main {

    static ArrayList<Double> getCritetiaForBanks(ArrayList<Bank> banks) {
        ArrayList<Double> criteria = new ArrayList<>();
        for (Bank bank : banks) {
            criteria.add(bank.getBankOffices().size() + bank.getBankATMS().size() +
                    bank.getEmployees().size() + (20 - bank.getInterestRate()));

        }
        return criteria;
    }


    static void mainLab_3() {
        Scanner input = new Scanner(System.in);
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
            Bank bank = bankService.create(i, String.format("Банк#%d", i));
            banks.add(bank);
            for (int j = 0; j < 4; j++) {
                BankATM bankATM = atmService.create(i * 3 + j, String.format("Банкомат#%d", i * 3 + j),
                        StatusATM.Work, 500.0);


                if (j % 2 == 0) {
                    BankOffice bankOffice = bankOfficeService.create(i * 3 + j, String.format("Офис#%d", i * 3 + j), String.format("Адрес#%d", i * 3 + j), true, 500.0);
                    bankService.addOffice(banks.get(i), bankOffice);
                    bankOfficeService.addMoney(bankOffice, 200.0);
                    bankService.addBankATM(banks.get(i), bankATM);

                    bankOfficeService.addATM(banks.get(i).getBankOffices().get(j), banks.get(i).getBankATMS().get(j));
                    atmService.addMoney(bankATM, 12313.0);
                    for (int k = 0; k < 4; k++) {
                        int temp = 5 * (j + 3 * i) + k;
                        Employee employee = employeeService.create(temp, String.format("Григорий#%d", temp), String.format("Потёмкин#%d", temp), new Date(19081917), String.format("Работа%d", k), (double) 500 * k);
                        if (k % 2 == 0) {
                            employee.setCanLend(false);
                        }
                        bankService.addEmployee(banks.get(i), employee);
                        bankOfficeService.addEmployee(banks.get(i).getBankOffices().get(j), employee);
                    }
                    employeeService.setWorkerToBankomat(banks.get(i).getBankOffices().get(j).getBankATMS().get(0),
                            banks.get(i).getBankOffices().get(j).getEmployees().get(0));
                } else {
                    BankOffice bankOffice = bankOfficeService.create(i * 3 + j, String.format("Офис#%d", i * 3 + j), String.format("Адрес#%d", i * 3 + j), true, 10.0);
                    bankService.addOffice(banks.get(i), bankOffice);
                    bankOfficeService.addMoney(bankOffice, 200000.0);

                    bankService.addBankATM(banks.get(i), bankATM);
                    bankOfficeService.addATM(banks.get(i).getBankOffices().get(j), banks.get(i).getBankATMS().get(j));
                    atmService.addMoney(bankATM, 123130000.0);
                    for (int k = 0; k < 4; k++) {
                        int temp = 5 * (j + 3 * i) + k;
                        Employee employee = employeeService.create(temp, String.format("Григорий#%d", temp), String.format("Орлов#%d", temp), new Date(19081917), String.format("Работа#%d", k), (double) 500 * k);
                        if (k % 2 == 0) {
                            employee.setCanLend(false);
                        }
                        bankService.addEmployee(banks.get(i), employee);
                        bankOfficeService.addEmployee(banks.get(i).getBankOffices().get(j), employee);
                    }
                    employeeService.setWorkerToBankomat(banks.get(i).getBankOffices().get(j).getBankATMS().get(0),
                            banks.get(i).getBankOffices().get(j).getEmployees().get(0));
                }
            }
            banks.get(i).getBankOffices().get(0).setMayApplyCredit(false);
            banks.get(i).getBankOffices().get(3).setMayApplyCredit(false);
        }

        for (int i = 0; i < 5; i++) {
            User user = userService.create(i, String.format("Эрнест#%d", i), String.format("Светлов#%d", i), new Date(10112000), String.format("Работа#%d", i));
            users.add(user);

            for (int j = 0; j < 2; j++) {

                int bankIndex;
                if (i + j == 5)
                    bankIndex = 0;
                else
                    bankIndex = i + j;

                paymentAccountService.addPayment(i * 2 + j, users.get(i), banks.get(bankIndex));
                ArrayList<PaymentAccount> paymentAccounts = users.get(i).getPaymentAccounts();
                paymentAccountService.addMoney(paymentAccounts.get(j), 100.0 * (j + i));
            }
        }
        boolean flag = true;

        System.out.println("Выберите id пользователя для которого вы будете брать кредит:");
        for (User user : users) {
            System.out.println("Информация о пользователе " + user.getId() + ":\n" + user + "\n");
        }
        System.out.print("Выбранный id: ");
        int choseUserID = input.nextInt();
        User choseUser = null;
        for (User user : users) {
            if (user.getId() == choseUserID) {
                choseUser = user;
                break;
            }
        }

        System.out.print("Введите размер кредита: ");
        int amount = input.nextInt();

        System.out.print("Введите количество месяцев, в течении которых вы будете покрывать кредит: ");
        int month = input.nextInt();

        banks.sort(new BankComparatorImpl());
        Collections.reverse(banks);
        ArrayList<Double> criteria = getCritetiaForBanks(banks);

        System.out.println("Введите id банка из представленных на экране. Банки расположены в порядке предпочтения от лучшего к худшему");
        for (int index = 0; index < banks.size(); index++) {
            System.out.println("Информация о банке " + banks.get(index).getId().toString() + ":\n" + banks.get(index).toString());
            System.out.println("Критерий банка: " + criteria.get(index).toString() + "\n");
        }
        System.out.print("Выбранный id: ");
        int choseBankID = input.nextInt();
        Bank choseBank = null;
        for (int index = 0; index < banks.size(); index++) {
            if (banks.get(index).getId() == choseBankID) {
                choseBank = banks.get(index);
                break;
            }
            if (index == banks.size() - 1)
                choseBank = banks.get(index);
        }
        BankOffice choseOffice = null;
        int choseOfficeID;


        try {
            System.out.println("Выберите id офиса из выбранного вами банка:");
            assert choseBank != null;
            for (BankOffice bankOffice : choseBank.getBankOffices()) {
                System.out.println("Информация об офисе " + bankOffice.getId().toString() + ":\n" + bankOffice + "\n");
            }
            System.out.print("Выбранный id: ");
            choseOfficeID = input.nextInt();

            for (int index = 0; index < choseBank.getBankOffices().size(); index++) {
                if (choseBank.getBankOffices().get(index).getId() == choseOfficeID) {
                    choseOffice = choseBank.getBankOffices().get(index);
                    break;
                }
                if (index == banks.size() - 1)
                    choseOffice = choseBank.getBankOffices().get(index);
            }
            assert choseOffice != null;

            if (amount > choseOffice.getMoney()) {
                int c = 0;
                System.out.println("В данном офисе недостаточно средств");
                System.out.println("Предлагаем выбрать другой офис");
                for (int i = 0; i < choseBank.getBankOffices().size(); i++) {
                    if (amount <= choseBank.getBankOffices().get(i).getMoney()) {
                        System.out.println("Информация об офисе " + choseBank.getBankOffices().get(i).getId().toString() + ":\n" + choseBank.getBankOffices().get(i).toString() + "\n");
                        c++;
                    }
                }
                if (c == 0) {
                    throw new NoEligibleOfficeException();
                }
                System.out.print("Выбранный id: ");
                choseOfficeID = input.nextInt();
                for (int index = 0; index < choseBank.getBankOffices().size(); index++) {
                    if (choseBank.getBankOffices().get(index).getId() == choseOfficeID) {
                        choseOffice = choseBank.getBankOffices().get(index);
                        break;
                    }
                    if (index == banks.size() - 1)
                        choseOffice = choseBank.getBankOffices().get(index);
                }
            }

            if (!choseOffice.getStatus())
                throw new BankOfficeException("Попытка получить кредит", "Выбранный офис не работает");
            if (!choseOffice.getMayApplyCredit())
                throw new BankOfficeException("Попытка получить кредит", "Выбранный офис не выдаёт кредиты");

        } catch (BankOfficeException | NoEligibleOfficeException e) {
            System.err.println(e.getMessage());
            return;
        }

        Employee choseEmployee = null;
        try {
            System.out.println("Выберите id сотрудника для выбранного вами офиса:");
            for (Employee employee : choseOffice.getEmployees()) {
                System.out.println("Информация о сотруднике " + employee.getId() + ":\n" + employee + "\n");
            }
            System.out.print("Выбранный id: ");
            int choseEmployeeID = input.nextInt();

            for (int index = 0; index < choseOffice.getEmployees().size(); index++) {
                if (choseOffice.getEmployees().get(index).getId() == choseEmployeeID) {
                    choseEmployee = choseOffice.getEmployees().get(index);
                    break;
                }
                if (index == banks.size() - 1)
                    choseEmployee = choseOffice.getEmployees().get(index);
            }

            assert choseEmployee != null;
            if (!choseEmployee.getCanLend())
                throw new EmployeeException("Попытка получить кредит", "Выбранный сотрудник не может выдавать кредиты");

        } catch (EmployeeException e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Пытаемся открыть кредит");
        creditAccountService.openCredit(1, choseUser, choseOffice, choseEmployee, LocalDate.now(), month, (double) amount);

    }

    static void mainLab_4() {
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
            Bank bank = bankService.create(i, String.format("Банк#%d", i));
            banks.add(bank);
            for (int j = 0; j < 3; j++) {
                BankOffice bankOffice = bankOfficeService.create(i * 3 + j, String.format("Офис#%d", i * 3 + j), String.format("Адрес#%d", i * 3 + j), true, 500.0);
                bankService.addOffice(banks.get(i), bankOffice);
                bankOfficeService.addMoney(bankOffice, 2000000.0);

                BankATM bankATM = atmService.create(i * 3 + j, String.format("Банкомат#%d", i * 3 + j),
                        StatusATM.Work, 500.0);
                bankService.addBankATM(banks.get(i), bankATM);

                bankOfficeService.addATM(banks.get(i).getBankOffices().get(j), banks.get(i).getBankATMS().get(j));
                atmService.addMoney(bankATM, 1231323103.0);
                for (int k = 0; k < 5; k++) {
                    int temp = 5 * (j + 3 * i) + k;
                    Employee employee = employeeService.create(temp, String.format("Иван%d", temp), String.format("Иванов%d", temp), new Date(19081917), String.format("Работа%d", k), (double) 500 * k);

                    bankService.addEmployee(banks.get(i), employee);
                    bankOfficeService.addEmployee(banks.get(i).getBankOffices().get(j), employee);
                }
                employeeService.setWorkerToBankomat(banks.get(i).getBankOffices().get(j).getBankATMS().get(0),
                        banks.get(i).getBankOffices().get(j).getEmployees().get(0));
            }
        }

        for (int i = 0; i < 5; i++) {
            User user = userService.create(i, String.format("Евгений#%d", i), String.format("Серов#%d", i), new Date(10112000), String.format("Работа#%d", i));
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
        Scanner input = new Scanner(System.in);


        boolean flag = true;
        while (flag) {
            try {

                System.out.println("\nВыберите действие: ");
                System.out.println("1. Вывод всех счетов пользователя по конкретному банку в .txt файл");
                System.out.println(
                        "2. Перенести счет пользователя из одного банка в другой через .txt файл");
                System.out.println("3. Выход");

                int action = input.nextInt();
                switch (action) {
                    case 1:
                        System.out.println("Выберите id пользователя для которого вы хотите вывести все счета в текстовый файл (.txt)");
                        for (User user : users) {
                            System.out.println("Информация о пользователе " + user.getId() + ":\n" + user + "\n");
                        }
                        System.out.print("Выбранный id: ");
                        int choseUserID = input.nextInt();
                        int uid = 0;
                        for (int index = 0; index < banks.size(); index++) {
                            if (users.get(index).getId() == choseUserID) {
                                uid = index;
                                break;
                            }
                        }

                        System.out.println("Платёжные счета пользователя:");
                        System.out.println(users.get(uid).getPaymentAccounts());
                        System.out.println("\nКредитные счета пользователя:");
                        System.out.println(users.get(uid).getCreditAccounts());

                        System.out.println("Введите id банка из представленных на экране");
                        for (Bank value : banks) {
                            System.out.println("Информация о банке " + value.getId().toString() + ":\n" + value);
                        }
                        System.out.print("Выбранный id: ");
                        int choseBankID = input.nextInt();
                        Bank choseBank = null;
                        for (int index = 0; index < banks.size(); index++) {
                            if (banks.get(index).getId() == choseBankID) {
                                choseBank = banks.get(index);
                                break;
                            }
                            if (index == banks.size() - 1)
                                choseBank = banks.get(index);
                        }
                        userService.saveToFile("file.txt", choseBank, users.get(uid));
                        break;
                    case 2:
                        System.out.print("Введите название файла:");
                        input.nextLine();
                        String fileName = input.nextLine();
                        System.out.println("Введите id банка из представленных на экране");
                        for (Bank bank : banks) {
                            System.out.println("Информация о банке " + bank.getId().toString() + ":\n" + bank);
                        }
                        System.out.print("Выбранный id: ");
                        choseBankID = input.nextInt();
                        int id = 0;
                        for (int index = 0; index < banks.size(); index++) {
                            if (banks.get(index).getId() == choseBankID) {
                                id = index;
                                break;
                            }
                            if (index == banks.size() - 1)
                                id = index;
                        }

                        userService.updateFromFile(fileName, banks.get(id), banks, users);
                        System.out.println("Информация о выбранном банке после добавления пользователя:");
                        System.out.println(banks.get(id).toString());
                        ArrayList<User> user1 = banks.get(id).getClients();
                        for (User user : user1) {
                            System.out.println("Информация о пользователе " + user.getId() + ":\n" + userService.getInfo(user) + "\n");
                        }

                        break;
                    case 3:
                        flag = false;
                        break;
                }
            } catch (IOException e) {
                System.out.println("Ошибка файла: " + e);
            }
        }
    }


    public static void main(String[] args) {
        mainLab_4();
    }
}