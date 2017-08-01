package hackphone.phone.configuration;


import java.net.InetSocketAddress;

public class SignallingConfiguration {

    public final SipAccount account;
    public final MyEndpoint me;
    public final SessionLogger logger;

    public SignallingConfiguration(SipAccount account, MyEndpoint me) {
        this.account = account;
        this.me = me;
        //
        this.logger = new SessionLoggerDefault(account.username);
    }

    public static class MyEndpoint {
        public final String myIp;

        public MyEndpoint(String myIp) {
            this.myIp = myIp;
        }
    }

    public static class SipAccount {
        public final InetSocketAddress asterisk;
        public final String username;
        public final String password;
        public final String displayName;

        public SipAccount(InetSocketAddress asterisk, String username, String password, String displayName) {
            this.asterisk = asterisk;
            this.username = username;
            this.password = password;
            this.displayName = displayName;
        }
    }

}
