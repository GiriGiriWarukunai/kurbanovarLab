package tech.reliab.course.kurbanovar.bank.exceptions;

public class NoEligibleEmployeeException extends RuntimeException {
    public NoEligibleEmployeeException() {
        super("В выбранном офисе нет сотрудников, умеющих выдавать кредиты");
    }
}