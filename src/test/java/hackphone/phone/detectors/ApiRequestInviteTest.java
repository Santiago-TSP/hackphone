package hackphone.phone.detectors;

import hackphone.phone.configuration.SignallingContext;
import org.junit.Test;
import org.mockito.Mockito;

import javax.sip.message.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ApiRequestInviteTest {

    @Test
    public void got_invite_and_produced_trying() {
        String message = "INVITE sip:660@10.5.1.208:65056;rinstance=1a905a88dd5ac3d8 SIP/2.0\n" +
                "Via: SIP/2.0/UDP 10.5.4.2:5060;branch=z9hG4bK2c858ff4;rport\n" +
                "Max-Forwards: 70\n" +
                "From: \"662423002\" <sip:662423002@10.5.4.2>;tag=as68b9bbdb\n" +
                "To: <sip:660@10.5.1.208:65056;rinstance=1a905a88dd5ac3d8>\n" +
                "Contact: <sip:662423002@10.5.4.2>\n" +
                "Call-ID: 5b0ff95e723c7dd46e32ef546734b5bf@10.5.4.2\n" +
                "CSeq: 102 INVITE\n" +
                "User-Agent: FPBX-2.9.0(1.6.2.17.3)\n" +
                "Date: Fri, 21 Jul 2017 07:17:56 GMT\n" +
                "Allow: INVITE, ACK, CANCEL, OPTIONS, BYE, REFER, SUBSCRIBE, NOTIFY, INFO\n" +
                "Supported: replaces, timer\n" +
                "Content-Type: application/sdp\n" +
                "Content-Length: 384\r\n" +
                "\r\n" +
                "v=0\r\n" +
                "o=root 270727982 270727982 IN IP4 10.5.4.2\r\n" +
                "s=Asterisk PBX 1.6.2.17.3\r\n" +
                "c=IN IP4 10.5.4.2\r\n" +
                "b=CT:384\r\n" +
                "t=0 0\r\n" +
                "m=audio 14102 RTP/AVP 8 0 101\r\n" +
                "a=rtpmap:8 PCMA/8000\r\n" +
                "a=rtpmap:0 PCMU/8000\r\n" +
                "a=rtpmap:101 telephone-event/8000\r\n" +
                "a=fmtp:101 0-16\r\n" +
                "a=ptime:20\r\n" +
                "a=sendrecv\r\n" +
                "m=video 12018 RTP/AVP 31 34 98\r\n" +
                "a=rtpmap:31 H261/90000\r\n" +
                "a=rtpmap:34 H263/90000\n" +
                "a=rtpmap:98 h263-1998/90000\n" +
                "a=sendrecv";
        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        assertTrue(detected.isRequest());
        assertTrue(detected.requestDetails().isInvite());
        SignallingContext context = Mockito.mock(SignallingContext.class);
        Response trying = detected.requestDetails().api().createResponse(Response.TRYING, context);
        System.out.println(trying);
        String stringTrying = trying.toString();
        assertTrue(stringTrying.contains("SIP/2.0 100 Trying"));
        assertTrue(stringTrying.contains("Call-ID: 5b0ff95e723c7dd46e32ef546734b5bf@10.5.4.2"));
        assertTrue(stringTrying.contains("CSeq: 102 INVITE"));
        assertTrue(stringTrying.contains("From: \"662423002\" <sip:662423002@10.5.4.2>;tag=as68b9bbdb"));
    }

    @Test
    public void got_invite_and_produced_ringing() {
        String message = "INVITE sip:660@10.5.1.208:65056;rinstance=1a905a88dd5ac3d8 SIP/2.0\n" +
                "Via: SIP/2.0/UDP 10.5.4.2:5060;branch=z9hG4bK2c858ff4;rport\n" +
                "Max-Forwards: 70\n" +
                "From: \"662423002\" <sip:662423002@10.5.4.2>;tag=as68b9bbdb\n" +
                "To: <sip:660@10.5.1.208:65056;rinstance=1a905a88dd5ac3d8>\n" +
                "Contact: <sip:662423002@10.5.4.2>\n" +
                "Call-ID: 5b0ff95e723c7dd46e32ef546734b5bf@10.5.4.2\n" +
                "CSeq: 102 INVITE\n" +
                "User-Agent: FPBX-2.9.0(1.6.2.17.3)\n" +
                "Date: Fri, 21 Jul 2017 07:17:56 GMT\n" +
                "Allow: INVITE, ACK, CANCEL, OPTIONS, BYE, REFER, SUBSCRIBE, NOTIFY, INFO\n" +
                "Supported: replaces, timer\n" +
                "Content-Type: application/sdp\n" +
                "Content-Length: 384\r\n" +
                "\r\n" +
                "v=0\r\n" +
                "o=root 270727982 270727982 IN IP4 10.5.4.2\r\n" +
                "s=Asterisk PBX 1.6.2.17.3\r\n" +
                "c=IN IP4 10.5.4.2\r\n" +
                "b=CT:384\r\n" +
                "t=0 0\r\n" +
                "m=audio 14102 RTP/AVP 8 0 101\r\n" +
                "a=rtpmap:8 PCMA/8000\r\n" +
                "a=rtpmap:0 PCMU/8000\r\n" +
                "a=rtpmap:101 telephone-event/8000\r\n" +
                "a=fmtp:101 0-16\r\n" +
                "a=ptime:20\r\n" +
                "a=sendrecv\r\n" +
                "m=video 12018 RTP/AVP 31 34 98\r\n" +
                "a=rtpmap:31 H261/90000\r\n" +
                "a=rtpmap:34 H263/90000\n" +
                "a=rtpmap:98 h263-1998/90000\n" +
                "a=sendrecv";

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        assertTrue(detected.isRequest());
        assertTrue(detected.requestDetails().isInvite());
        SignallingContext context = Mockito.mock(SignallingContext.class);
        when(context.myIp()).thenReturn("10.5.1.208");
        when(context.accountName()).thenReturn("660");
        when(context.myPort()).thenReturn(65056);
        Response ringing = detected.requestDetails().api().createResponse(Response.RINGING, context);
        System.out.println(ringing);
        String stringRinging = ringing.toString();
        assertTrue(stringRinging.contains("SIP/2.0 180 Ringing"));
        assertTrue(stringRinging.contains("From: \"662423002\" <sip:662423002@10.5.4.2>;tag=as68b9bbdb"));
        assertTrue(stringRinging.contains("To: <sip:660@10.5.1.208:65056;rinstance=1a905a88dd5ac3d8>;tag="));
        assertTrue(stringRinging.contains("Contact: <sip:660@10.5.1.208:"));
    }

    @Test
    public void got_invite_and_produced_busyHere() {
        String message = "INVITE sip:660@10.5.1.208:65056;rinstance=1a905a88dd5ac3d8 SIP/2.0\n" +
                "Via: SIP/2.0/UDP 10.5.4.2:5060;branch=z9hG4bK2c858ff4;rport\n" +
                "Max-Forwards: 70\n" +
                "From: \"662423002\" <sip:662423002@10.5.4.2>;tag=as68b9bbdb\n" +
                "To: <sip:660@10.5.1.208:65056;rinstance=1a905a88dd5ac3d8>\n" +
                "Contact: <sip:662423002@10.5.4.2>\n" +
                "Call-ID: 5b0ff95e723c7dd46e32ef546734b5bf@10.5.4.2\n" +
                "CSeq: 102 INVITE\n" +
                "User-Agent: FPBX-2.9.0(1.6.2.17.3)\n" +
                "Date: Fri, 21 Jul 2017 07:17:56 GMT\n" +
                "Allow: INVITE, ACK, CANCEL, OPTIONS, BYE, REFER, SUBSCRIBE, NOTIFY, INFO\n" +
                "Supported: replaces, timer\n" +
                "Content-Type: application/sdp\n" +
                "Content-Length: 384\r\n" +
                "\r\n" +
                "v=0\r\n" +
                "o=root 270727982 270727982 IN IP4 10.5.4.2\r\n" +
                "s=Asterisk PBX 1.6.2.17.3\r\n" +
                "c=IN IP4 10.5.4.2\r\n" +
                "b=CT:384\r\n" +
                "t=0 0\r\n" +
                "m=audio 14102 RTP/AVP 8 0 101\r\n" +
                "a=rtpmap:8 PCMA/8000\r\n" +
                "a=rtpmap:0 PCMU/8000\r\n" +
                "a=rtpmap:101 telephone-event/8000\r\n" +
                "a=fmtp:101 0-16\r\n" +
                "a=ptime:20\r\n" +
                "a=sendrecv\r\n" +
                "m=video 12018 RTP/AVP 31 34 98\r\n" +
                "a=rtpmap:31 H261/90000\r\n" +
                "a=rtpmap:34 H263/90000\n" +
                "a=rtpmap:98 h263-1998/90000\n" +
                "a=sendrecv";

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        assertTrue(detected.isRequest());
        assertTrue(detected.requestDetails().isInvite());
        SignallingContext context = Mockito.mock(SignallingContext.class);
        when(context.myIp()).thenReturn("10.5.1.208");
        when(context.accountName()).thenReturn("660");
        when(context.myPort()).thenReturn(65056);
        Response busyHere = detected.requestDetails().api().createResponse(Response.BUSY_HERE, context);
        System.out.println(busyHere);
        String stringRinging = busyHere.toString();
        assertTrue(stringRinging.contains("486 Busy here"));
        assertTrue(stringRinging.contains("From: \"662423002\" <sip:662423002@10.5.4.2>;tag=as68b9bbdb"));
        assertTrue(stringRinging.contains("To: <sip:660@10.5.1.208:65056;rinstance=1a905a88dd5ac3d8>;tag="));
    }

    @Test
    public void got_invite_parsed_important_information() {
        String message = "INVITE sip:660@10.5.1.208:5060;rinstance=a47dc4158b6ae5fe SIP/2.0\n" +
                "Via: SIP/2.0/UDP 10.5.4.2:5060;branch=z9hG4bK324749ad;rport\n" +
                "Max-Forwards: 70\n" +
                "From: \"662423002\" <sip:662423002@10.5.4.2>;tag=as6ad225d8\n" +
                "To: <sip:660@10.5.1.208:5060;rinstance=a47dc4158b6ae5fe>\n" +
                "Contact: <sip:662423002@10.5.4.2>\n" +
                "Call-ID: 4317157c04cc304666fdfe97119188ee@10.5.4.2\n" +
                "CSeq: 102 INVITE\n" +
                "User-Agent: FPBX-2.9.0(1.6.2.17.3)\n" +
                "Date: Fri, 21 Jul 2017 12:51:13 GMT\n" +
                "Allow: INVITE, ACK, CANCEL, OPTIONS, BYE, REFER, SUBSCRIBE, NOTIFY, INFO\n" +
                "Supported: replaces, timer\n" +
                "Content-Type: application/sdp\n" +
                "Content-Length: 386\n" +
                "\n" +
                "v=0\n" +
                "o=root 1626133600 1626133600 IN IP4 10.5.4.2\n" +
                "s=Asterisk PBX 1.6.2.17.3\n" +
                "c=IN IP4 10.5.4.2\n" +
                "b=CT:384\n" +
                "t=0 0\n" +
                "m=audio 12786 RTP/AVP 8 0 101\n" +
                "a=rtpmap:8 PCMA/8000\n" +
                "a=rtpmap:0 PCMU/8000\n" +
                "a=rtpmap:101 telephone-event/8000\n" +
                "a=fmtp:101 0-16\n" +
                "a=ptime:20\n" +
                "a=sendrecv\n" +
                "m=video 14746 RTP/AVP 31 34 98\n" +
                "a=rtpmap:31 H261/90000\n" +
                "a=rtpmap:34 H263/90000\n" +
                "a=rtpmap:98 h263-1998/90000\n" +
                "a=sendrecv\n";

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        ApiRequestInvite invite =(ApiRequestInvite)detected.requestDetails().api();

        assertEquals("662423002", invite.getFromPhoneNumber());
        assertEquals("10.5.4.2", invite.getMediaTrafficIp());
        assertEquals(12786, invite.getMediaTrafficPort());
    }
}