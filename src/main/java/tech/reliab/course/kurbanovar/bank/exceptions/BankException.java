package tech.reliab.course.kurbanovar.bank.exceptions;

public class BankException extends RuntimeException {
    public BankException(String type, String message) {super(String.format("Ошибка при работе с офисом. Суть ошибки: %s. Содержимое ошибки: %s",type, message));}
}
