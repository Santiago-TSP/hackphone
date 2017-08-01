package hackphone.phone.configuration;

import java.util.Random;

public class SignallingLazyConfiguration {

    final int port;
    final String callIdForRegistering;
    final String callIdForInviteing;

    public SignallingLazyConfiguration(int port) {
        this.port = port;
        long random = new Random().nextLong();
        this.callIdForRegistering = "random-register-"+random;
        this.callIdForInviteing = "random-invite-"+random;
    }

    public int myPort() {
        return port;
    }

    public String getCallIdForRegistering() {
        return callIdForRegistering;
    }

    public String getCallIdForInviteing() {
        return callIdForInviteing;
    }


    final TrafficContext trafficContext = new TrafficContext();


    public TrafficContext getTrafficContext() {
        return trafficContext;
    }

    public static class TrafficContext {
        private String nonce;

        public String getNonce() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }

        public String getRealm() {
            return realm;
        }

        public void setRealm(String realm) {
            this.realm = realm;
        }

        private String realm;


        // TODO: uzupelniac to gdy wylosujemy port dla RTP
        public int getMyRtpPort() {
            return myRtpPort;
        }

        public void setMyRtpPort(int myRtpPort) {
            this.myRtpPort = myRtpPort;
        }

        private int myRtpPort;




    }


}
