package hackphone.media.io;

import hackphone.phone.configuration.SessionLogger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MediaReceiverDefault implements MediaReceiver {

    final SessionLogger logger;
    final List<Consumer<ByteBuffer>> consumers = new ArrayList<>();

    public MediaReceiverDefault(SessionLogger logger) {
        this.logger = logger;
    }

    @Override
    public void disconnected() {
        logger.info("TODO: DISCONNECTED");
        // TODO it later
    }

    @Override
    public void accept(ByteBuffer packet) {
        consumers.forEach(c->c.accept(packet));
    }

    public void listenToIncominigPackets(Consumer<ByteBuffer> consumer) {
        consumers.add(consumer);
    }
}
