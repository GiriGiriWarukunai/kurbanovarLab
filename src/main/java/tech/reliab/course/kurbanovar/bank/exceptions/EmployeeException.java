package tech.reliab.course.kurbanovar.bank.exceptions;

public class EmployeeException extends  RuntimeException {
    public EmployeeException(String type, String message) {super(String.format("Ошибка при работе с сотрудником банка. Суть ошибки: %s. Содержимое ошибки: %s",type, message));}
}
