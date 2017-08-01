package hackphone.phone.configuration;

public class SessionLoggerDefault implements SessionLogger {

    final String prefix;

    public SessionLoggerDefault(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void info(String message) {
        System.out.println(prefix+"|"+message);
    }

    @Override
    public void error(Throwable ex) {
        System.out.println(prefix+"|ERROR|"+ex.getMessage());
        ex.printStackTrace();
    }
}
