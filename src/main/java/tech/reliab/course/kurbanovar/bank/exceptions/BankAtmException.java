package tech.reliab.course.kurbanovar.bank.exceptions;

public class BankAtmException extends RuntimeException {
    public BankAtmException(String type, String message) {super(String.format("Ошибка при работе с банкоматом. Суть ошибки: %s. Содержимое ошибки: %s",type, message));}
}