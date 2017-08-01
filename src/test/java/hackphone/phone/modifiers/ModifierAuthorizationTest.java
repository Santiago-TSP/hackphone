package hackphone.phone.modifiers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * https://en.wikipedia.org/wiki/Digest_access_authentication
 *
 * HA1=MD5(username:realm:password)
 *
 * HA2=MD5(method:digestURI)
 *
 * response=MD5(HA1:nonce:HA2)
 */
public class ModifierAuthorizationTest {


    @Test
    public void generateAuthorizationResponse() {
        String username = "660";
        String realm = "asterisk";
        String password = "test";
        String nonce = "1cc78052";
        String method = "REGISTER";
        String digestURI = "sip:asterisk.softwaremind.pl";
        String response = ModifierAuthorization.generateAuthorizationResponse(
                username, realm, password, nonce, method, digestURI
        );
        assertEquals("7a222d1317bdc6a67bda3823c356d1ef", response);
    }
}