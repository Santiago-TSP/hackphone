package hackphone.media.io;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public interface MediaReceiver extends Consumer<ByteBuffer> {

    void disconnected();
}
