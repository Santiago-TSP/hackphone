package hackphone.phone.detectors;

import gov.nist.javax.sip.header.To;

import javax.sip.message.Response;
import java.net.InetSocketAddress;

public class ApiResponseOK implements ApiResponse {

    final Response response;
    final ParserMediaTrafficAddress parserMediaTrafficAddress;
    final String toTag;

    ApiResponseOK(Response response) {
        this.response = response;
        this.parserMediaTrafficAddress = new ParserMediaTrafficAddress(response.getRawContent());
        To to = (To)response.getHeader(To.NAME);
        this.toTag = to.getTag();
    }

    public String getMediaTrafficIp() {
        return parserMediaTrafficAddress.getMediaTrafficIp();
    }

    public int getMediaTrafficPort() {
        return parserMediaTrafficAddress.getMediaTrafficPort();
    }

    public InetSocketAddress getRtpPeer() {
        String rtpPeerIp = getMediaTrafficIp();
        int rtpPeerPort = getMediaTrafficPort();
        InetSocketAddress rtpPeer = new InetSocketAddress(rtpPeerIp, rtpPeerPort);
        return rtpPeer;
    }

    public String getToTag() {
        return toTag;
    }
}
