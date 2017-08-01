package hackphone;

import hackphone.media.io.InputOutputMediaStrategy;
import hackphone.media.io.InputOutputMediaStrategyProduction;
import hackphone.phone.configuration.GlobalLoggerDefault;
import hackphone.phone.configuration.SignallingConfiguration;
import hackphone.phone.configuration.SignallingConfigurationFactory;
import hackphone.phone.configuration.TestableSignallingConfigurationFactory;
import hackphone.phone.registering.RegisteringEventsSupport;
import org.junit.Test;
import hackphone.phone.*;
import hackphone.phone.io.InputOutputStrategyTestable;

public class MainTest {

    @Test
    public void send_register_receive_call_does_not_exist() throws Exception {
        final String RESPONSE_AS_REACTION = "SIP/2.0 481 Call leg/Transaction does not exist\n" +
                "CSeq: 1 REGISTER\n" +
                "Call-ID: 84253MjE0Njk5YjQ0MDUzZjZmMWYzYjFlYWYyNmE1OTM1MDA\n" +
                "From: \"MaMi\" <sip:660@10.7.1.115>;tag=875b8660\n" +
                "To: \"MaMi\" <sip:660@10.7.1.115>\n" +
                "Via: SIP/2.0/UDP 10.99.99.109:5060;branch=z9hG4bK-524287-1---48386459eb7cd63e;rport\n" +
                "Content-Length: 0\r\n" +
                "\r\n";

        SignallingConfiguration configuration = TestableSignallingConfigurationFactory.testing_server_115();
        InputOutputStrategyTestable inputOutputStrategy = new InputOutputStrategyTestable(new GlobalLoggerDefault());
        InputOutputMediaStrategy mediaStrategy = new InputOutputMediaStrategyProduction(new GlobalLoggerDefault());
        ConfiguredStrategies context = new ConfiguredStrategies(inputOutputStrategy, mediaStrategy);
        PhoneDriver driver = PhoneDriverFactory.build(configuration, context);
        driver.register(new RegisteringEventsSupport());
        inputOutputStrategy.whenSentMessageThen((incoming, receiver)-> {
            receiver.accept(RESPONSE_AS_REACTION);
        });
    }


    @Test
    public void send_register_receive_unauthorized() throws Exception {
        final String RESPONSE_AS_REACTION = "SIP/2.0 401 Unauthorized\n" +
                "Via: SIP/2.0/UDP 10.99.99.109:49374;branch=z9hG4bK-524287-1---48386459eb7cd63e;received=10.99.99.109;rport=49374\n" +
                "From: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>;tag=875b8660\n" +
                "To: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>;tag=as6be09d82\n" +
                "Call-ID: 84253MjE0Njk5YjQ0MDUzZjZmMWYzYjFlYWYyNmE1OTM1MDA\n" +
                "CSeq: 1 REGISTER\n" +
                "Server: FPBX-2.9.0(1.6.2.17.3)\n" +
                "Allow: INVITE, ACK, CANCEL, OPTIONS, BYE, REFER, SUBSCRIBE, NOTIFY, INFO\n" +
                "Supported: replaces, timer\n" +
                "WWW-Authenticate: Digest algorithm=MD5, realm=\"asterisk\", nonce=\"1cc78052\"\n" +
                "Content-Length: 0\r\n" +
                "\r\n";

        SignallingConfiguration configuration = TestableSignallingConfigurationFactory.testing_server_115();
        InputOutputStrategyTestable inputOutputStrategy = new InputOutputStrategyTestable(new GlobalLoggerDefault());
        InputOutputMediaStrategy mediaStrategy = new InputOutputMediaStrategyProduction(new GlobalLoggerDefault());
        ConfiguredStrategies context = new ConfiguredStrategies(inputOutputStrategy, mediaStrategy);
        PhoneDriver driver = PhoneDriverFactory.build(configuration, context);
        driver.register(new RegisteringEventsSupport());
        inputOutputStrategy.whenSentMessageThen((incoming, receiver)-> {
            receiver.accept(RESPONSE_AS_REACTION);
        });
    }
}