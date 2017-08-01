package hackphone.phone.detectors;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DetectorMessageInvalidLengthTest {
    @Test
    public void it_is_not_response() {
        String message = "INVITE sip:660@10.5.1.208:5060;rinstance=a47dc4158b6ae5fe SIP/2.0\n" +
                "Via: SIP/2.0/UDP 10.5.4.2:5060;branch=z9hG4bK42ccd534;rport\n" +
                "Max-Forwards: 70\n" +
                "From: \"662423002\" <sip:662423002@10.5.4.2>;tag=as4472dc43\n" +
                "To: <sip:660@10.5.1.208:5060;rinstance=a47dc4158b6ae5fe>\n" +
                "Contact: <sip:662423002@10.5.4.2>\n" +
                "Call-ID: 48e02fa911c809823f5c0f683043e92d@10.5.4.2\n" +
                "CSeq: 102 INVITE\n" +
                "User-Agent: FPBX-2.9.0(1.6.2.17.3)\n" +
                "Date: Fri, 21 Jul 2017 12:04:05 GMT\n" +
                "Allow: INVITE, ACK, CANCEL, OPTIONS, BYE, REFER, SUBSCRIBE, NOTIFY, INFO\n" +
                "Supported: replaces, timer\n" +
                "Content-Type: application/sdp\n" +
                "Content-Length: 386\n" +
                "\n" +
                "v=0\n" +
                "o=root 1184403247 1184403247 IN IP4 10.5.4.2\n" +
                "s=Asterisk PBX 1.6.2.17.3\n" +
                "c=IN IP4 10.5.4.2\n" +
                "b=CT:384\n" +
                "t=0 0\n" +
                "m=audio 18822 RTP/AVP 8 0 101\n" +
                "a=rtpmap:8 PCMA/8000\n" +
                "a=rtpmap:0 PCMU/8000\n" +
                "a=rtpmap:101 telephone-event/8000\n" +
                "a=fmtp:101 0-16\n" +
                "a=ptime:20\n" +
                "a=sendrecv\n" +
                "m=video 14108 RTP/AVP 31 34 98\n" +
                "a=rtpmap:31 H261/90000\n" +
                "a=rtpmap:34 H263/90000\n" +
                "a=rtpmap:98 h263-1998/90000\n" +
                "a=sendrecv\n";

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        assertTrue(detected.isRequest());
        assertTrue(detected.requestDetails().isInvite());
    }
}
