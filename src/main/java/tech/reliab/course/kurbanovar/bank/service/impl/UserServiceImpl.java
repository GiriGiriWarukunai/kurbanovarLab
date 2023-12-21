package tech.reliab.course.kurbanovar.bank.service.impl;

import tech.reliab.course.kurbanovar.bank.entity.*;
import tech.reliab.course.kurbanovar.bank.entity.jsonClasses.JsonCreditAcc;
import tech.reliab.course.kurbanovar.bank.entity.jsonClasses.JsonPayAcc;
import tech.reliab.course.kurbanovar.bank.service.BankService;
import tech.reliab.course.kurbanovar.bank.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class UserServiceImpl implements UserService {

    Gson gson = new Gson();
    Type payAccArrType = new TypeToken<ArrayList<JsonPayAcc>>() {}.getType();
    Type credAccArrType = new TypeToken<ArrayList<JsonCreditAcc>>() {}.getType();

    @Override
    public User create(Integer id, String name, String surname, Date birthday, String work) {
        return new User(id, name, surname, birthday, work);
    }

    @Override
    public void changeWork(User user, String newWork, Double newMonthSalary) {
        user.setWork(newWork);
        user.setMonthSalary(newMonthSalary);
        int creditRating = 0;
        int startRat = 0;
        int endRat = 1000;
        while ((startRat != 10000) && (creditRating == 0)) {
            if ((newMonthSalary <= endRat) && (newMonthSalary >= startRat))
                creditRating = endRat / 10;
            else {
                startRat += 1000;
                endRat += 1000;
            }
        }
        user.setCreditRating(creditRating);
    }

    @Override
    public String getInfo(User user){
        String res = "";

        res += user.toString();

        res += "\n\nИнформация о платёжных счётах:\n";
        for (PaymentAccount paymentAccount: user.getPaymentAccounts()){
            res += paymentAccount.toString();
            res += "\n\n";
        }

        res += "\n\nИнформация о кредитных счётах:\n";
        for (CreditAccount creditAccount: user.getCreditAccounts()){
            res += creditAccount.toString();
            res += "\n\n";
        }
        return res;
    }

    @Override
    public void saveToFile(String fileName, Bank bank, User user) throws IOException {
        String payAccStr = gson.toJson(this.makeJsonPayAcc(bank.getId(), user));
        String creditAccStr = gson.toJson(this.makeJsonCreditAcc(bank.getId(),user));
        File file = new File(fileName);
        FileWriter writer = new FileWriter(file);
        writer.write("Платёжные счета:\n" + payAccStr + "\n\nКредитные счета:\n" + creditAccStr);
        writer.close();
    }

    @Override
    public void updateFromFile(String fileName, Bank bank, ArrayList<Bank> banks,  ArrayList<User> users) throws IOException {
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        boolean first = true;
        while (line != null) {
            if (!line.isEmpty()) {
                if (line.charAt(0) == '[') {
                    if (first) {
                        first = false;
                        this.makePayAccFromJson(gson.fromJson(line, payAccArrType), users, bank);
                    } else {
                        this.makeCreditAccFromJson(gson.fromJson(line, credAccArrType), users, bank, banks);
                    }
                }
            }
            line = reader.readLine();
        }
    }


    private ArrayList<JsonPayAcc> makeJsonPayAcc(Integer bankID, User user) {
        ArrayList<JsonPayAcc> jsonPay = new ArrayList<>();
        for (PaymentAccount paymentAccount : user.getPaymentAccounts()) {
            if (Objects.equals(paymentAccount.getBank().getId(), bankID)) {
                jsonPay.add(new JsonPayAcc(paymentAccount));
            }
        }
        if (jsonPay.isEmpty())
            System.out.println("\nУ пользователя нет платежных счетов в данном банке");
        return jsonPay;
    }

    private void makePayAccFromJson(ArrayList<JsonPayAcc> jsonPayAcc, ArrayList<User> users, Bank bank) {

        if (!jsonPayAcc.isEmpty()) {
            for (int j = 0; j < jsonPayAcc.size(); j++) {
                int choseUserID = jsonPayAcc.get(j).getUserID();
                User choseUser = null;
                for (User user : users) {
                    if (user.getId() == choseUserID) {
                        choseUser = user;
                        break;
                    }
                }
                assert choseUser != null;
                ArrayList<PaymentAccount> payAcc = choseUser.getPaymentAccounts();
                for (int i = 0; i < payAcc.size(); i++) {
                    if (Objects.equals(payAcc.get(i).getId(), jsonPayAcc.get(j).getId())) {
                        payAcc.get(i).updateFromJsonClass(jsonPayAcc.get(j), bank);
                    }
                }
                choseUser.setPaymentAccounts(payAcc);
            }

        }
    }

    private ArrayList<JsonCreditAcc> makeJsonCreditAcc(Integer bankID, User user) {
        ArrayList<JsonCreditAcc> jsonCredit = new ArrayList<>();
        for (CreditAccount creditAccount : user.getCreditAccounts()) {
            if (Objects.equals(creditAccount.getBank().getId(), bankID)) {
                jsonCredit.add(new JsonCreditAcc(creditAccount));
            }
        }
        if (jsonCredit.isEmpty())
            System.out.println("\nУ пользователя нет кредитов в данном банке");
        return jsonCredit;
    }

    private void makeCreditAccFromJson(ArrayList<JsonCreditAcc> jsonCreditAcc, ArrayList<User> users, Bank bank, ArrayList<Bank> banks) {
        if (!jsonCreditAcc.isEmpty()) {
            for (int j = 0; j < jsonCreditAcc.size(); j++) {
                int choseUserID = jsonCreditAcc.get(j).getUserID();
                User choseUser = null;
                for (User user : users) {
                    if (user.getId() == choseUserID) {
                        choseUser = user;
                        break;
                    }
                }
                int choseBankID = jsonCreditAcc.get(j).getBankID();
                int id = 0;
                for (int index = 0; index < banks.size(); index++) {
                    if (banks.get(index).getId() == choseBankID) {
                        id = index;
                        break;
                    }
                    if (index == banks.size() - 1)
                        id = index;
                }
                assert choseUser != null;
                ArrayList<CreditAccount> creditAccounts = choseUser.getCreditAccounts();
                for (int i = 0; i < creditAccounts.size(); i++) {
                    if (Objects.equals(creditAccounts.get(i).getId(), jsonCreditAcc.get(j).getId())) {
                        creditAccounts.get(i).updateFromJsonClass(jsonCreditAcc.get(j), bank);
                        BankService bankService = new BankServiceImpl();
                        bankService.deleteUser(banks.get(id), choseUser);
                        bankService.addUser(bank, choseUser);
                        ArrayList<Bank> bankus =  choseUser.getBanks();
                        bankus.remove(banks.get(id));
                        choseUser.setBanks(bankus);
                    }
                }
                choseUser.setCreditAccounts(creditAccounts);

            }

        }
    }


}