package hackphone.media;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class AudioDetector implements Consumer<ByteBuffer> {

    private static final int LENGTH_OF_AUDIO_WITH_HEADER = 172;
    private static final int LENGTH_OF_AUDIO = 160;
    private final Consumer<ByteBuffer> audioConsumer;

    public AudioDetector(Consumer<ByteBuffer> audioConsumer) {
        this.audioConsumer = audioConsumer;
    }

    @Override
    public void accept(ByteBuffer byteBuffer) {
        if (byteBuffer.position() == LENGTH_OF_AUDIO_WITH_HEADER) {
            byteBuffer.limit(LENGTH_OF_AUDIO_WITH_HEADER);
            byteBuffer.position(12);
            ByteBuffer audio = ByteBuffer.allocate(LENGTH_OF_AUDIO);
            audio.put(byteBuffer);
            audio.rewind();
            audioConsumer.accept(audio);
        }
    }
}
