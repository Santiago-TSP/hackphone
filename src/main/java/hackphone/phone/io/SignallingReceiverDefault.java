package hackphone.phone.io;

import hackphone.phone.configuration.SessionLogger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SignallingReceiverDefault implements SignallingReceiver {

    final SessionLogger logger;
    final List<Consumer<String>> consumers = new ArrayList<>();

    public SignallingReceiverDefault(SessionLogger logger) {
        this.logger = logger;
    }

    @Override
    public void disconnected() {
        logger.info("TODO: DISCONNECTED");
        // TODO it later
    }

    @Override
    public void accept(ByteBuffer byteBuffer) {
        String packet = new String(byteBuffer.array());
        consumers.forEach(c->c.accept(packet));
    }

    public void listenToIncominigPackets(Consumer<String> consumer) {
        consumers.add(consumer);
    }
}
