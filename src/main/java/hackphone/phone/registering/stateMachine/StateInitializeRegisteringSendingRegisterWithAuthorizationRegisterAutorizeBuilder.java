package hackphone.phone.registering.stateMachine;

import hackphone.phone.configuration.SignallingContext;
import hackphone.phone.modifiers.*;

import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

class StateInitializeRegisteringSendingRegisterWithAuthorizationRegisterAutorizeBuilder {


    private final String pattern = "REGISTER sip:asterisk.softwaremind.pl SIP/2.0\n" +
            "Via: SIP/2.0/UDP 10.99.99.109:49374;branch=z9hG4bK-524287-1---c5fb861daba1042e;rport\n" +
            "Max-Forwards: 70\n" +
            "Contact: <sip:660@10.99.99.109:49374;rinstance=a47dc4158b6ae5fe>\n" +
            "To: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>\n" +
            "From: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>;tag=875b8660\n" +
            "Call-ID: 84253MjE0Njk5YjQ0MDUzZjZmMWYzYjFlYWYyNmE1OTM1MDA\n" +
            "CSeq: 2 REGISTER\n" +
            "Expires: 3600\n" +
            "Allow: SUBSCRIBE, NOTIFY, INVITE, ACK, CANCEL, BYE, REFER, INFO, OPTIONS, MESSAGE\n" +
            "User-Agent: X-Lite release 4.9.8 stamp 84253\n" +
     //       "Authorization: Digest username=\"660\",realm=\"asterisk\",nonce=\"1cc78052\",uri=\"sip:asterisk.softwaremind.pl\",response=\"5109b63f749a3a87d0d6c06b83bc56c9\",algorithm=MD5\n" +
            "Content-Length: 0\r\n" +
            "\r\n";


    final MessageFactory messageFactory;
    final List<Modifier> modifiers;

    StateInitializeRegisteringSendingRegisterWithAuthorizationRegisterAutorizeBuilder() throws PeerUnavailableException {
        messageFactory = SipFactory.getInstance().createMessageFactory();
        modifiers = Arrays.asList(
                new ModifierFrom(),
                new ModifierTo(),
                new ModifierContact(),
                new ModifierVia(),
                new ModifierRequestUri(),
                new ModifierCallID(),
                new ModifierAuthorization() // to odroznia go od poprzedniego
        );
    }

    Request build(SignallingContext context) throws ParseException {
        Request request = messageFactory.createRequest(pattern);
        modifiers.forEach(m->m.update(request, context));
        return request;
    }
}
