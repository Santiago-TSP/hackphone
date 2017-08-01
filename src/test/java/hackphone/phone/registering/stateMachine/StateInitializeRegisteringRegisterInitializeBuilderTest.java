package hackphone.phone.registering.stateMachine;

import hackphone.phone.configuration.*;
import org.junit.Test;

import javax.sip.InvalidArgumentException;
import javax.sip.PeerUnavailableException;
import javax.sip.message.Request;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.assertTrue;

public class StateInitializeRegisteringRegisterInitializeBuilderTest {

    @Test
    public void build() throws PeerUnavailableException, ParseException, InvalidArgumentException, IOException {
        SignallingConfiguration configuration = TestableSignallingConfigurationFactory.testing();
        SignallingLazyConfiguration lazyConfiguration = new SignallingLazyConfiguration(43224);
        final SignallingContext context = new SignallingContext(configuration, lazyConfiguration);
        StateInitializeRegisteringRegisterInitializeBuilder builder = new StateInitializeRegisteringRegisterInitializeBuilder();
        Request request = builder.build(context);
        String message = request.toString();
        assertTrue(message.contains("Contact: <sip:660@10.5.1.208:43224"));
        assertTrue(message.contains("To: \"MaMi\" <sip:660@testing.ailleron.com>"));
        assertTrue(message.contains("From: \"MaMi\" <sip:660@testing.ailleron.com>;tag="));
        assertTrue(message.contains("Via: SIP/2.0/UDP 10.5.1.208:43224;branch="));
        assertTrue(message.contains("REGISTER sip:testing.ailleron.com"));
    }
}