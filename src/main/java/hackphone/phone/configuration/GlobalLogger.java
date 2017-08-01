package hackphone.phone.configuration;

public interface GlobalLogger {

    void info(String message);

    void error(Throwable exception);

    void error(String message, Throwable exception);
}
