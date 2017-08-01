package hackphone.media;

import java.nio.ByteBuffer;

public class PackagingAlaw {

    private static final int RTP_LENGTH = 172;
    private int sequenceNumber = 1;
    private long timestamp = 0;
    private long ssrc = 1617633477L;

    public synchronized ByteBuffer packageAlaw(ByteBuffer audio) {
        RtpPacket packet = new RtpPacket(RTP_LENGTH, false);
        packet.wrap(false, (byte)8, sequenceNumber, timestamp, ssrc, audio);
        ByteBuffer output = packet.getBuffer();
        sequenceNumber++;
        timestamp+=160;
        return output;
    }
}
