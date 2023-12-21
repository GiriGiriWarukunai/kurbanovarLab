package tech.reliab.course.kurbanovar.bank.service.impl;

import tech.reliab.course.kurbanovar.bank.entity.*;
import tech.reliab.course.kurbanovar.bank.exceptions.*;
import tech.reliab.course.kurbanovar.bank.service.AtmService;
import tech.reliab.course.kurbanovar.bank.service.CreditAccountService;
import tech.reliab.course.kurbanovar.bank.service.PaymentAccountService;
import tech.reliab.course.kurbanovar.bank.utils.StatusATM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class CreditAccountServiceImpl implements CreditAccountService {

    @Override
    public CreditAccount create(Integer id, User user, Bank bank, Employee employee, PaymentAccount paymentAccount, LocalDate startDate, Integer countMonth, Double amount) {
        return new CreditAccount(id, user, bank, employee, paymentAccount, startDate, countMonth, amount);
    }

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
    public void openCredit(Integer id, User user, BankOffice bankOffice, Employee employee,
                           LocalDate startDate, Integer countMonth, Double amount)
    {
        try {
            if (!bankOffice.getStatus())
                throw new BankOfficeException("Попытка получить кредит", "Выбранный офис не работает");
            if (!bankOffice.getMaySetATM())
                throw new BankAtmException("Попытка получить кредит", "В офисе нет банкоматов");
            if (bankOffice.getMoney() < amount)
                throw new NoBankMoneyException();
            if (!bankOffice.getMayApplyCredit())
                throw new BankOfficeException("Попытка получить кредит", "Выбранный офис не выдаёт кредиты");
            if (!employee.getCanLend())
                throw new EmployeeException("Попытка получить кредит", "Выбранный сотрудник не выдаёт кредит");
            if (user.getCreditRating() < 500 && bankOffice.getBank().getInterestRate() > 50)
                throw new BadUserRatingException(bankOffice.getBank().getInterestRate(), user.getCreditRating());
            if (user.getCreditAccounts()!=null && checkCreditAcc(user, bankOffice.getBank()) != -1)
                throw new CreditException("Попытка получить кредит", "У клиента уже есть кредит в этом банке");

            for (BankATM bankATM : bankOffice.getBankATMS()) {
                if (bankATM.getStatus() == StatusATM.Work && bankATM.getMoney() >= amount) {
                    ArrayList<CreditAccount> creditAccounts;
                    CreditAccount creditAccount;
                    if (user.getCreditAccounts()!=null && checkPaymentAcc(user, bankOffice.getBank()) >= 0) {
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
            throw new CreditException("Попытка получить кредит", "Ни в одном из банкоматов нет денег");
        }catch (CreditException | BankOfficeException | BadUserRatingException | EmployeeException | NoBankMoneyException | BankAtmException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void closeCredit(User user, CreditAccount creditAccount, LocalDate ourDate){
        try {
            if (ourDate.isBefore(creditAccount.getEndDate()))
                throw new CreditException("Попытка закрыть кредит", "Время для закрытия кредита ещё не наступило");

            ArrayList<CreditAccount> creditAccounts = user.getCreditAccounts();
            creditAccounts.remove(creditAccount);
            if (creditAccounts.isEmpty())
                user.setCreditAccounts(null);
            else
                user.setCreditAccounts(creditAccounts);

        } catch (CreditException e) {
            System.err.println(e.getMessage());
        }
    }
}
