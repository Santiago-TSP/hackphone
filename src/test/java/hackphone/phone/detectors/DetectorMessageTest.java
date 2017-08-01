package hackphone.phone.detectors;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DetectorMessageTest {

    @Test
    public void it_is_not_response() {
        String message = "REGISTER sip:asterisk.softwaremind.pl SIP/2.0\n" +
                "Via: SIP/2.0/UDP 10.99.99.109:49374;branch=z9hG4bK-524287-1---48386459eb7cd63e;rport\n" +
                "Max-Forwards: 70\n" +
                "Contact: <sip:660@10.99.99.109:49374;rinstance=a47dc4158b6ae5fe>\n" +
                "To: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>\n" +
                "From: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>;tag=875b8660\n" +
                "Call-ID: 84253MjE0Njk5YjQ0MDUzZjZmMWYzYjFlYWYyNmE1OTM1MDA\n" +
                "CSeq: 1 REGISTER\n" +
                "Expires: 3600\n" +
                "Allow: SUBSCRIBE, NOTIFY, INVITE, ACK, CANCEL, BYE, REFER, INFO, OPTIONS, MESSAGE\n" +
                "User-Agent: X-Lite release 4.9.8 stamp 84253\n" +
                "Content-Length: 0\r\n" +
                "\r\n";

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        assertFalse(detected.isResponse());
    }

    @Test
    public void it_is_response() {
        String message = "SIP/2.0 100 Trying\n" +
                "Via: SIP/2.0/UDP 10.99.99.109:49374;branch=z9hG4bK-524287-1---48386459eb7cd63e;received=10.99.99.109;rport=49374\n" +
                "From: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>;tag=875b8660\n" +
                "To: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>\n" +
                "Call-ID: 84253MjE0Njk5YjQ0MDUzZjZmMWYzYjFlYWYyNmE1OTM1MDA\n" +
                "CSeq: 1 REGISTER\n" +
                "Server: FPBX-2.9.0(1.6.2.17.3)\n" +
                "Allow: INVITE, ACK, CANCEL, OPTIONS, BYE, REFER, SUBSCRIBE, NOTIFY, INFO\n" +
                "Supported: replaces, timer\n" +
                "Content-Length: 0\r\n" +
                "\r\n";

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        assertTrue(detected.isResponse());
    }

    @Test
    public void it_is_response_2() {
        String message = "SIP/2.0 401 Unauthorized\n" +
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

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        assertTrue(detected.isResponse());
    }

    @Test
    public void it_is_response_Trying() {
        String message = "SIP/2.0 100 Trying\n" +
                "Via: SIP/2.0/UDP 10.99.99.109:49374;branch=z9hG4bK-524287-1---48386459eb7cd63e;received=10.99.99.109;rport=49374\n" +
                "From: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>;tag=875b8660\n" +
                "To: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>\n" +
                "Call-ID: 84253MjE0Njk5YjQ0MDUzZjZmMWYzYjFlYWYyNmE1OTM1MDA\n" +
                "CSeq: 1 REGISTER\n" +
                "Server: FPBX-2.9.0(1.6.2.17.3)\n" +
                "Allow: INVITE, ACK, CANCEL, OPTIONS, BYE, REFER, SUBSCRIBE, NOTIFY, INFO\n" +
                "Supported: replaces, timer\n" +
                "Content-Length: 0\r\n" +
                "\r\n";

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        assertTrue(detected.isResponse());
        assertTrue(detected.responseDetails().isTrying());
        assertFalse(detected.responseDetails().isUnauthorized());
    }

    @Test
    public void it_is_response_Unauthorized() {
        String message = "SIP/2.0 401 Unauthorized\n" +
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

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        assertTrue(detected.isResponse());
        assertFalse(detected.responseDetails().isTrying());
        assertTrue(detected.responseDetails().isUnauthorized());
    }

    @Test
    public void it_is_response_OK() {
        String message = "SIP/2.0 200 OK\n" +
                "Via: SIP/2.0/UDP 10.99.99.109:49374;branch=z9hG4bK-524287-1---c5fb861daba1042e;received=10.99.99.109;rport=49374\n" +
                "From: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>;tag=875b8660\n" +
                "To: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>;tag=as6be09d82\n" +
                "Call-ID: 84253MjE0Njk5YjQ0MDUzZjZmMWYzYjFlYWYyNmE1OTM1MDA\n" +
                "CSeq: 2 REGISTER\n" +
                "Server: FPBX-2.9.0(1.6.2.17.3)\n" +
                "Allow: INVITE, ACK, CANCEL, OPTIONS, BYE, REFER, SUBSCRIBE, NOTIFY, INFO\n" +
                "Supported: replaces, timer\n" +
                "Expires: 3600\n" +
                "Contact: <sip:660@10.99.99.109:49374;rinstance=a47dc4158b6ae5fe>;expires=3600\n" +
                "Date: Wed, 19 Jul 2017 05:26:56 GMT\n" +
                "Content-Length: 0\r\n" +
                "\r\n";

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        assertTrue(detected.isResponse());
        assertTrue(detected.responseDetails().isOk());
    }

    @Test
    public void it_is_response_call_leg_transaction_does_not_exist() {
        String message = "SIP/2.0 481 Call leg/Transaction does not exist\n" +
                "CSeq: 1 REGISTER\n" +
                "Call-ID: 84253MjE0Njk5YjQ0MDUzZjZmMWYzYjFlYWYyNmE1OTM1MDA\n" +
                "From: \"MaMi\" <sip:660@10.7.1.115>;tag=875b8660\n" +
                "To: \"MaMi\" <sip:660@10.7.1.115>\n" +
                "Via: SIP/2.0/UDP 10.99.99.109:5060;branch=z9hG4bK-524287-1---48386459eb7cd63e;rport\n" +
                "Content-Length: 0\r\n" +
                "\r\n";

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        assertTrue(detected.isResponse());
        assertTrue(detected.responseDetails().isTransactionDoesNotExist());
    }


    @Test
    public void it_is_request_option() {
        String message = "OPTIONS sip:660@10.99.99.109:49374;rinstance=a47dc4158b6ae5fe SIP/2.0\n" +
                "Via: SIP/2.0/UDP 10.5.4.2:5060;branch=z9hG4bK4e184b40;rport\n" +
                "Max-Forwards: 70\n" +
                "From: \"Unknown\" <sip:Unknown@10.5.4.2>;tag=as1e2b32aa\n" +
                "To: <sip:660@10.99.99.109:49374;rinstance=a47dc4158b6ae5fe>\n" +
                "Contact: <sip:Unknown@10.5.4.2>\n" +
                "Call-ID: 0aab7dca20538e5548ee8bb3330d42fb@10.5.4.2\n" +
                "CSeq: 102 OPTIONS\n" +
                "User-Agent: FPBX-2.9.0(1.6.2.17.3)\n" +
                "Date: Wed, 19 Jul 2017 05:26:56 GMT\n" +
                "Allow: INVITE, ACK, CANCEL, OPTIONS, BYE, REFER, SUBSCRIBE, NOTIFY, INFO\n" +
                "Supported: replaces, timer\n" +
                "Content-Length: 0\r\n" +
                "\r\n";

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        assertTrue(detected.isRequest());
        assertTrue(detected.requestDetails().isOption());
    }
}