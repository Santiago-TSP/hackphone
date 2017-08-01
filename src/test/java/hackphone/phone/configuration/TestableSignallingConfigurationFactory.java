package hackphone.phone.configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

public class TestableSignallingConfigurationFactory {

    static final String MY_TEMPORARY_IP = "10.5.1.208";

    public static SignallingConfiguration testing() throws IOException {
        SignallingConfiguration.SipAccount account = new SignallingConfiguration.SipAccount(
                new InetSocketAddress("testing.ailleron.com", 5060),
                "660",
                "aaaaabbbbb",
                "MaMi"
        );
        SignallingConfiguration.MyEndpoint me = new SignallingConfiguration.MyEndpoint(MY_TEMPORARY_IP);
        SignallingConfiguration cfg = new SignallingConfiguration(account, me);
        return cfg;
    }

    public static SignallingConfiguration testing_server_115() throws IOException {
        SignallingConfiguration.SipAccount account = new SignallingConfiguration.SipAccount(
                new InetSocketAddress("10.7.1.115", 5060),
                "660",
                "abc",
                "MaMi"
        );
        SignallingConfiguration.MyEndpoint me = new SignallingConfiguration.MyEndpoint(MY_TEMPORARY_IP);
        SignallingConfiguration cfg = new SignallingConfiguration(account, me);
        return cfg;
    }
}
