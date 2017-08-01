package hackphone.phone.configuration;

import hackphone.phone.utils.AvailableAddressesProvider;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Iterator;

public class SignallingConfigurationFactory {

    final String MY_ADDRESS_IP;

    public SignallingConfigurationFactory() {
        Iterator<InetAddress> addressIterator = new AvailableAddressesProvider().get();
        if(addressIterator.hasNext()) {
            MY_ADDRESS_IP = addressIterator.next().getHostAddress();
            System.out.println("Detected IP address "+MY_ADDRESS_IP);
        } else {
            throw new RuntimeException("Cannot find my IP address");
        }
    }

    public SignallingConfiguration asterisk(
            String username,
            String password,
            String asteriskHost,
            int asteriskPort) throws IOException {
        SignallingConfiguration.SipAccount account = new SignallingConfiguration.SipAccount(
                new InetSocketAddress(asteriskHost, asteriskPort),
                username,
                password,
                "HackPhone"
        );
        SignallingConfiguration.MyEndpoint me = new SignallingConfiguration.MyEndpoint(MY_ADDRESS_IP);
        SignallingConfiguration cfg = new SignallingConfiguration(account, me);
        return cfg;
    }
}
