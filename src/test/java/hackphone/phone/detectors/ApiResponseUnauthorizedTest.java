package hackphone.phone.detectors;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApiResponseUnauthorizedTest {


    @Test
    public void unauthorized() {
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
        ApiResponseUnauthorized unauthorized = (ApiResponseUnauthorized)detected.responseDetails().api();

        assertEquals("1cc78052", unauthorized.getNonce());
        assertEquals("asterisk", unauthorized.getRealm());
    }

    @Test
    public void unauthorized_2() {
        String message = "SIP/2.0 401 Unauthorized\n" +
                "Via: SIP/2.0/UDP 10.99.99.109:49374;branch=z9hG4bK-524287-1---f7cc9a5d98cf5f51;received=10.99.99.109;rport=49374\n" +
                "From: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>;tag=875b8660\n" +
                "To: \"Marcin Miotk\"<sip:660@asterisk.softwaremind.pl>;tag=as6be09d82\n" +
                "Call-ID: 84253MjE0Njk5YjQ0MDUzZjZmMWYzYjFlYWYyNmE1OTM1MDA\n" +
                "CSeq: 3 REGISTER\n" +
                "Server: FPBX-2.9.0(1.6.2.17.3)\n" +
                "Allow: INVITE, ACK, CANCEL, OPTIONS, BYE, REFER, SUBSCRIBE, NOTIFY, INFO\n" +
                "Supported: replaces, timer\n" +
                "WWW-Authenticate: Digest algorithm=MD5, realm=\"asterisk\", nonce=\"08a3f9ed\", stale=true\n" +
                "Content-Length: 0\r\n" +
                "\r\n";

        DetectorMessage detector = new DetectorMessage();
        DetectedMessage detected = detector.detect(message);
        ApiResponseUnauthorized unauthorized = (ApiResponseUnauthorized)detected.responseDetails().api();

        assertEquals("08a3f9ed", unauthorized.getNonce());
        assertEquals("asterisk", unauthorized.getRealm());
    }


}