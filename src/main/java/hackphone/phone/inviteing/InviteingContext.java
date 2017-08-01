package hackphone.phone.inviteing;

import java.net.InetSocketAddress;

public class InviteingContext {

    public CallingTo getCallingTo() {
        return callingTo;
    }

    public void setCallingTo(CallingTo callingTo) {
        this.callingTo = callingTo;
    }

    private CallingTo callingTo;
    private String toTag;

    public InetSocketAddress getPeerRtp() {
        return peerRtp;
    }

    public void setPeerRtp(InetSocketAddress peerRtp) {
        this.peerRtp = peerRtp;
    }

    private InetSocketAddress peerRtp;

    public String getToTag() {
        return toTag;
    }

    public void setToTag(String toTag) {
        this.toTag = toTag;
    }
}
