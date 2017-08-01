package hackphone.phone.io;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public interface SignallingReceiver extends Consumer<ByteBuffer> {

    void disconnected();
}
