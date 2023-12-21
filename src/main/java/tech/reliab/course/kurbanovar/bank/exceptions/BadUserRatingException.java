package tech.reliab.course.kurbanovar.bank.exceptions;

public class BadUserRatingException extends RuntimeException {
    public BadUserRatingException(Double bankRating, Integer userRating) {
        super("Кредитный рейтинг пользователя недостаточно высок для выдачи кредита (рейтинг банка - "
                + bankRating + "; кредитный рейтинг пользователя - " + userRating + ")");
    }
}
