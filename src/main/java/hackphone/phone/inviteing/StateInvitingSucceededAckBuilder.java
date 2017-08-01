package hackphone.phone.inviteing;

import hackphone.phone.configuration.SignallingContext;
import hackphone.phone.modifiers.*;

import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class StateInvitingSucceededAckBuilder {

    private final String pattern = "ACK sip:662423002@10.5.4.2 SIP/2.0\n" +
            "Via: SIP/2.0/UDP 10.5.1.208:58487;branch=z9hG4bK-524287-1---70d099372f4f2366;rport\n" +
            "Max-Forwards: 70\n" +
            "Contact: <sip:672@10.5.1.208:58487;rinstance=29c6fa276c8f166c>\n" +
            "To: <sip:662423002@asterisk.softwaremind.pl>;tag=as2c4e1818\n" +
            "From: \"Marcin Miotk\"<sip:672@asterisk.softwaremind.pl>;tag=69af3f62\n" +
            "Call-ID: 84253MzBiNmJlOWUxOTgzMjhkMzVjNDljZmViZTg5MDlmZGI\n" +
            "CSeq: 2 ACK\n" +
            "User-Agent: X-Lite release 4.9.8 stamp 84253\n" +
            "Content-Length: 0\r\n\r\n";

    final MessageFactory messageFactory;
    final HeaderFactory headerFactory;
    final List<Modifier> modifiers;

    public StateInvitingSucceededAckBuilder() throws PeerUnavailableException {
        messageFactory = SipFactory.getInstance().createMessageFactory();
        headerFactory = SipFactory.getInstance().createHeaderFactory();
        modifiers = Arrays.asList(
                new ModifierFrom(),
                new ModifierToWithTag(),
                new ModifierContact(),
                new ModifierVia(),
                new ModifierRequestUri(true),
                new ModifierCallID(true)
                //,
                //new ModifierAuthorization(true) // to odroznia go od poprzedniego
        );
    }

    public Request build(SignallingContext context, CallingTo callingTo) throws ParseException {
        // given for modifiers
        context.getInviteingContext().setCallingTo(callingTo);
        Request request = messageFactory.createRequest(pattern);
        modifiers.forEach(m->m.update(request, context));
        return request;
    }
}
