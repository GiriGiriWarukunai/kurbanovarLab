package tech.reliab.course.kurbanovar.bank.service.impl;

import tech.reliab.course.kurbanovar.bank.entity.*;
import tech.reliab.course.kurbanovar.bank.service.AtmService;
import tech.reliab.course.kurbanovar.bank.service.CreditAccountService;
import tech.reliab.course.kurbanovar.bank.service.PaymentAccountService;
import tech.reliab.course.kurbanovar.bank.utils.StatusATM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class CreditAccountServiceImpl implements CreditAccountService {
    private Integer checkPaymentAcc(User user, Bank bank){
        for (int index = 0; index < user.getPaymentAccounts().size(); index++) {
            if (Objects.equals(user.getPaymentAccounts().get(index).getBank(), bank))
                return index;
        }
        return -1;
    }

    private Integer checkCreditAcc(User user, Bank bank){
        for (int index = 0; index < user.getCreditAccounts().size(); index++) {
            if (Objects.equals(user.getCreditAccounts().get(index).getBank(), bank))
                return index;
        }
        return -1;
    }

    @Override
    public CreditAccount create(Integer id, User user, Bank bank, Employee employee, PaymentAccount paymentAccount, LocalDate startDate, Integer countMonth, Double amount) {
        return new CreditAccount(id, user, bank, employee, paymentAccount, startDate, countMonth, amount);
    }

    @Override
    public void openCredit(Integer id, User user, BankOffice bankOffice, Employee employee,
                       LocalDate startDate, Integer countMonth, Double amount)
    {
        if (!bankOffice.getStatus())
            System.out.println("Выбранный офис не работает");
        else if (!bankOffice.getMaySetATM())
            System.out.println("В офисе нет банкоматов");
        else if (bankOffice.getMoney() < amount)
            System.out.println("В офисе не хватает денег");
        else if (!employee.getCanLend())
            System.out.println("Выбранный сотрудник не выдаёт кредит");
        else if (user.getCreditRating() < 500 && bankOffice.getBank().getInterestRate() > 50)
            System.out.println("Клиенту отказано в выдаче кредита");
        else if (user.getCreditAccounts()!=null && checkCreditAcc(user, bankOffice.getBank()) != -1)
            System.out.println("У клиента уже есть кредит в этом банке");
        else {
            for (BankATM bankATM : bankOffice.getBankATMS()) {
                if (bankATM.getStatus() == StatusATM.Work && bankATM.getMoney() >= amount) {
                    ArrayList<CreditAccount> creditAccounts;
                    CreditAccount creditAccount;
                    if (user.getCreditAccounts() != null && checkPaymentAcc(user, bankOffice.getBank()) >= 0) {
                        creditAccount = new CreditAccount(id, user, bankOffice.getBank(), employee,
                                user.getPaymentAccounts().get(checkPaymentAcc(user, bankOffice.getBank())), startDate, countMonth, amount);
                    } else {
                        PaymentAccountService paymentAccountService = new PaymentAccountServiceImpl();
                        paymentAccountService.addPayment(100, user, bankOffice.getBank());
                        creditAccount = new CreditAccount(id, user, bankOffice.getBank(), employee,
                                user.getPaymentAccounts().get(user.getPaymentAccounts().size() - 1), startDate, countMonth, amount);
                    }

                    if (user.getCreditAccounts() != null) {
                        creditAccounts = user.getCreditAccounts();
                    } else
                        creditAccounts = new ArrayList<>();
                    creditAccounts.add(creditAccount);
                    AtmService atmService = new AtmServiceImpl();
                    atmService.subtractMoney(bankATM, amount);
                    user.setCreditAccounts(creditAccounts);
                    return;
                }
            }
        }
    }

    @Override
    public void closeCredit(User user, CreditAccount creditAccount, LocalDate ourDate){
        if (ourDate.isBefore(creditAccount.getEndDate()))
            System.out.println("Время для закрытия кредита ещё не наступила");
        else {
            ArrayList<CreditAccount> creditAccounts = user.getCreditAccounts();
            creditAccounts.remove(creditAccount);
            if (creditAccounts.size() == 0)
                user.setCreditAccounts(null);
            else
                user.setCreditAccounts(creditAccounts);
        }
    }
}
