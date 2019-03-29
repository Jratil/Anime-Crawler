package top.ratil.animecrawler.exception;

public class RequestLimitException extends RuntimeException {

    private String message;

    public RequestLimitException() {
        super();
    }

    public RequestLimitException(String message) {
        this.message = message;
    }

    public RequestLimitException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public String geMessage() {
        return message;
    }
}
