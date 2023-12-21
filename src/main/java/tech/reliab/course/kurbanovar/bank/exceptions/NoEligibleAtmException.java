package tech.reliab.course.kurbanovar.bank.exceptions;

public class NoEligibleAtmException extends RuntimeException {
    public NoEligibleAtmException() {
        super("В выбранном офисе нет банкоматов, удовлетворяющих условиям клиента");
    }
}
