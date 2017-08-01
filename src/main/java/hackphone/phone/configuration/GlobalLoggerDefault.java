package hackphone.phone.configuration;

public class GlobalLoggerDefault implements GlobalLogger {

    @Override
    public void info(String message) {
        System.out.println("GLOBAL|"+message);
    }

    @Override
    public void error(Throwable exception) {
        System.out.println("GLOBAL ERROR|"+exception);
        exception.printStackTrace();
    }

    @Override
    public void error(String message, Throwable exception) {
        System.out.println("GLOBAL ERROR|"+message+"|"+exception);
        exception.printStackTrace();
    }
}
