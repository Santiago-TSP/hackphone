package hackphone.phone.configuration;

public interface SessionLogger {

    void info(String message);

    void error(Throwable ex);
}
