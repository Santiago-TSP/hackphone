package hackphone.phone.detectors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserMediaTrafficAddress {

    final String sdp;

    public ParserMediaTrafficAddress(byte[] sdpFromSipMessage) {
        if(sdpFromSipMessage!=null) {
            this.sdp = new String(sdpFromSipMessage);
        } else {
            this.sdp = "";
        }
    }

    public ParserMediaTrafficAddress(String sdpFromSipMessage) {
        if(sdpFromSipMessage!=null) {
            this.sdp = sdpFromSipMessage;
        } else {
            this.sdp = "";
        }
    }

    public String getMediaTrafficIp() {
        // c=IN IP4 10.5.4.2
        Pattern pattern = Pattern.compile("c=IN IP4 (.{6,})");
        Matcher matcher = pattern.matcher(sdp);
        if(matcher.find()) {
            String dst = matcher.group(1);
            return dst;
        } else {
            return null;
        }
    }

    public int getMediaTrafficPort() {
        // m=audio 12786 RTP/AVP 8 0 101
        Pattern pattern = Pattern.compile("m=audio ([0-9]{4,10}) RTP");
        Matcher matcher = pattern.matcher(sdp);
        if(matcher.find()) {
            String dst = matcher.group(1);
            return Integer.parseInt(dst);
        } else {
            return 0;
        }
    }
}
