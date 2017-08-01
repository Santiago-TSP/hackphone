package hackphone.media;

import java.nio.ByteBuffer;

/**
 * Copy-Paste from Open Source
 */
public class RtpPacket {

    public static final int HEADER_LEN = 12;
    private final ByteBuffer buffer;

    public RtpPacket(int capacity, boolean allocateDirect) {
        buffer = allocateDirect
                ? ByteBuffer.allocateDirect(capacity)
                : ByteBuffer.allocate(capacity);
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public int getVersion() {
        return (buffer.get(0) & 0xC0) >> 6;
    }

    public int getContributingSource() {
        return buffer.get(0) & 0x0F;
    }

    public boolean hasPadding() {
        return (buffer.get(0) & 0x20) == 0x020;
    }

    public boolean getMarker() {
        return (buffer.get(1) & 0xff & 0x80) == 0x80;
    }

    public int getPayloadType() {
        return (buffer.get(1) & 0xff & 0x7f);
    }

    public int getSeqNumber() {
        short before = buffer.getShort(2);
        int after;
        if(before < 0) {
            if (before < -8192){
                after = 0xDFFF & before;
                return after;
            }
            after = 0xEFFF & before;
            return after;
        }
        return before;
    }

    public long getTimestamp() {
        return buffer.getInt(4);
    }

    public void setTimestamp(long timestamp) {
        buffer.putInt(4, (int) timestamp);
    }

    public long getSyncSource() {
        return buffer.getInt(8);
    }

    public int getPayloadLength() {
        return buffer.limit() - HEADER_LEN;
    }

    public int getPayloadPosition() {
        return HEADER_LEN;
    }

    public void wrap(boolean mark, byte payloadType, int seqNumber, long timestamp, long ssrc, ByteBuffer data) {
        buffer.clear();
        buffer.rewind();
        buffer.put((byte) 0x80);
        byte b = (byte) (payloadType);
        if (mark) {
            b = (byte) (b | 0x80);
        }
        buffer.put(b);
        buffer.put((byte) ((seqNumber & 0xFF00) >> 8));
        buffer.put((byte) (seqNumber & 0x00FF));
        buffer.put((byte) ((timestamp & 0xFF000000) >> 24));
        buffer.put((byte) ((timestamp & 0x00FF0000) >> 16));
        buffer.put((byte) ((timestamp & 0x0000FF00) >> 8));
        buffer.put((byte) ((timestamp & 0x000000FF)));
        buffer.put((byte) ((ssrc & 0xFF000000) >> 24));
        buffer.put((byte) ((ssrc & 0x00FF0000) >> 16));
        buffer.put((byte) ((ssrc & 0x0000FF00) >> 8));
        buffer.put((byte) ((ssrc & 0x000000FF)));
        buffer.put(data);
        buffer.flip();
        buffer.rewind();
    }
}
