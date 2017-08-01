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

public class StateInitializeInviteingWaitForUnauthorizedInviteBuilder {

    private final String pattern = "INVITE sip:662423002@asterisk.softwaremind.pl SIP/2.0\n" +
            "Via: SIP/2.0/UDP 10.5.1.208:58487;branch=z9hG4bK-524287-1---da8df17c0fec0803;rport\n" +
            "Max-Forwards: 70\n" +
            "Contact: <sip:672@10.5.1.208:58487;rinstance=29c6fa276c8f166c>\n" +
            "To: <sip:662423002@asterisk.softwaremind.pl>\n" +
            "From: \"Marcin Miotk\"<sip:672@asterisk.softwaremind.pl>;tag=69af3f62\n" +
            "Call-ID: 84253MzBiNmJlOWUxOTgzMjhkMzVjNDljZmViZTg5MDlmZGI\n" +
            "CSeq: 1 INVITE\n" +
            "Allow: SUBSCRIBE, NOTIFY, INVITE, ACK, CANCEL, BYE, REFER, INFO, OPTIONS, MESSAGE\n" +
            "Content-Type: application/sdp\n" +
            "Supported: replaces\n" +
            "User-Agent: X-Lite release 4.9.8 stamp 84253\n" +
            "Content-Length: 0\r\n\r\n";


    // TODO:
    // 1. Sprawdzin FROM, TO, CONTACT, powinno byc odseparowane Call-ID
    // 2. Podmienic tez numer telefonu docelowego

    private final String sdpPattern = "v=0\n" +
            "o=- 13145118363215542 1 IN IP4 #PATTERN_MY_IP#\n" +
            "s=X-Lite release 4.9.8 stamp 84253\n" +
            "c=IN IP4 #PATTERN_MY_IP#\n" +
            "t=0 0\n" +
            "m=audio #PATTERN_MY_RTP_PORT# RTP/AVP 8 101\n" +
            "a=rtpmap:101 telephone-event/8000\n" +
            "a=fmtp:101 0-15\n" +
            "a=sendrecv";


    // 8 to alaw (Europa)
    // 0 to ulaw (Ameryka)

    final MessageFactory messageFactory;
    final HeaderFactory headerFactory;
    final List<Modifier> modifiers;

    public StateInitializeInviteingWaitForUnauthorizedInviteBuilder() throws PeerUnavailableException {
        messageFactory = SipFactory.getInstance().createMessageFactory();
        headerFactory = SipFactory.getInstance().createHeaderFactory();
        modifiers = Arrays.asList(
                new ModifierFrom(),
                new ModifierTo(true),
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
        {
            String sdp = sdpPattern;
            sdp = sdp.replaceAll("#PATTERN_MY_IP#", context.myIp());
            sdp = sdp.replaceAll("#PATTERN_MY_RTP_PORT#", ""+context.trafficable_myRtpPort());

            ContentTypeHeader contentType = headerFactory.createContentTypeHeader("application", "sdp");
            request.setContent(sdp, contentType);
        }
        return request;
    }
}
