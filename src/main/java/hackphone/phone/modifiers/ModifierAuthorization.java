package hackphone.phone.modifiers;

import hackphone.phone.configuration.SignallingContext;

import javax.sip.address.SipURI;
import javax.sip.header.AuthorizationHeader;
import javax.sip.message.Message;
import javax.sip.message.Request;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

public class ModifierAuthorization extends ModifierAbstract implements Modifier {

    final boolean inviteing;

    public ModifierAuthorization() {
        this.inviteing = false;
    }

    public ModifierAuthorization(boolean inviteing) {
        this.inviteing = inviteing;
    }



    @Override
    public void update(Message message, SignallingContext context) {
        try {
            Request request = (Request)message;
            AuthorizationHeader authorization = headerFactory.createAuthorizationHeader("Digest");
            authorization.setAlgorithm("MD5");
            authorization.setUsername(context.accountName());
            authorization.setRealm(context.trafficable_realm());
            authorization.setNonce(context.trafficable_nonce());
            SipURI uri = (SipURI)request.getRequestURI();
            uri.setHost(context.asteriskHost());
            authorization.setURI(uri);
            String username = context.accountName();
            String realm = context.trafficable_realm();
            String password = context.accoungPassword();
            String nonce = context.trafficable_nonce();
            String method;
            if(inviteing) {
                method = "INVITE";
            } else {
                method = "REGISTER";
            }

            String digestURI = uri.toString();
            String response = generateAuthorizationResponse(username, realm, password, nonce, method, digestURI);
            authorization.setResponse(response);
            request.addHeader(authorization);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * https://en.wikipedia.org/wiki/Digest_access_authentication
     *
     * HA1=MD5(username:realm:password)
     * HA2=MD5(method:digestURI)
     * response=MD5(HA1:nonce:HA2)
     */
    static String generateAuthorizationResponse(String username, String realm, String password, String nonce, String method, String digestURI) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte[] HA1 = algorithm.digest(new String(username+":"+realm+":"+password).getBytes());
            byte[] HA2 = algorithm.digest(new String(method+":"+digestURI).getBytes());
            String HA1_hex = new HexBinaryAdapter().marshal(HA1).toLowerCase();
            String HA2_hex = new HexBinaryAdapter().marshal(HA2).toLowerCase();
            byte[] response = algorithm.digest(new String(HA1_hex+":"+nonce+":"+HA2_hex).getBytes());
            return new HexBinaryAdapter().marshal(response).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
