package hackphone.phone.registering.stateMachine;

import hackphone.phone.detectors.ApiRequestInvite;

public class InformationAboutCaller {
    final String phoneNumber;
    final String mediaTrafficIp;
    final int mediaTrafficPort;

    public InformationAboutCaller(ApiRequestInvite invite) {
        this(invite.getFromPhoneNumber(), invite.getMediaTrafficIp(), invite.getMediaTrafficPort());
    }

    public InformationAboutCaller(String phoneNumber, String mediaTrafficIp, int mediaTrafficPort) {
        this.phoneNumber = phoneNumber;
        this.mediaTrafficIp = mediaTrafficIp;
        this.mediaTrafficPort = mediaTrafficPort;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMediaTrafficIp() {
        return mediaTrafficIp;
    }

    public int getMediaTrafficPort() {
        return mediaTrafficPort;
    }

    @Override
    public String toString() {
        return "phoneNumber='" + phoneNumber + '\'' + ", media=" + mediaTrafficIp + ':' + mediaTrafficPort;
    }
}
