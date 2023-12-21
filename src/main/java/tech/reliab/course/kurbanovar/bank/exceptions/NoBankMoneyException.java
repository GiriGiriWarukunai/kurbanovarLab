package tech.reliab.course.kurbanovar.bank.exceptions;

public class NoBankMoneyException extends RuntimeException {
    public NoBankMoneyException() {
        super("В банке недостаточно средств для выдачи кредита");
    }
}
