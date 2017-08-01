package hackphone.media.io;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

@FunctionalInterface
public interface MediaSender extends Consumer<ByteBuffer> {
}
