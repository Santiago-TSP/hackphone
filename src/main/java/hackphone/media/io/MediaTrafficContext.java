package hackphone.media.io;

import java.net.InetSocketAddress;

public class MediaTrafficContext {

    final InetSocketAddress myAddress;
    final InetSocketAddress peerAddress;

    public MediaTrafficContext(InetSocketAddress myAddress, InetSocketAddress peerAddress) {
        this.myAddress = myAddress;
        this.peerAddress = peerAddress;
    }

    InetSocketAddress getMyAddress() {
        return myAddress;
    }

    InetSocketAddress getPeerAddress() {
        return peerAddress;
    }
}
