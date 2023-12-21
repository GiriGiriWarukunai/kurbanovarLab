package tech.reliab.course.kurbanovar.bank.exceptions;

public class PaymentException extends RuntimeException {
    public PaymentException(String type, String message) {super(String.format("Ошибка при работе с банковским счётом. Суть ошибки: %s. Содержимое ошибки: %s",type, message));}
}
