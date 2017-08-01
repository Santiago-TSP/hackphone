package hackphone.phone.detectors;

import gov.nist.javax.sip.header.WWWAuthenticate;

import javax.sip.message.Response;

public class ApiResponseUnauthorized implements ApiResponse {

    final Response response;
    //
    final String realm;
    final String nonce;


    public ApiResponseUnauthorized(Response response) {
        this.response = response;
        WWWAuthenticate header = (WWWAuthenticate)response.getHeader("WWW-Authenticate");
        realm = header.getRealm();
        nonce = header.getNonce();
    }

    public String getRealm() {
        return realm;
    }

    public String getNonce() {
        return nonce;
    }
}
