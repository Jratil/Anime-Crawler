package top.ratil.animecrawler.exception;

public class PageException extends RuntimeException {

    private String message;

    public PageException() {
        super();
    }

    public PageException(String message) {
        super(message);
        this.message = message;
    }

    public PageException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public PageException(Throwable cause) {
        super(cause);
        this.message = message;
    }

    protected PageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
