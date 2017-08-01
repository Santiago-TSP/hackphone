package hackphone.phone.io;


import hackphone.phone.configuration.GlobalLogger;
import hackphone.phone.configuration.SignallingContext;

import java.nio.ByteBuffer;
import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class InputOutputStrategyTestable implements InputOutputStrategy {

    final GlobalLogger globalLogger;
    public final Semaphore sentMessageSemaphore;

    public InputOutputStrategyTestable(GlobalLogger globalLogger) {
        this.globalLogger = globalLogger;
        this.sentMessageSemaphore = new Semaphore(0);
    }

    SignallingReceiver receiver;
    String lastSentMessage;

    @Override
    public SignallingSender connect(SignallingReceiver receiver, SignallingContext context) {
        this.receiver = receiver;
        globalLogger.info("Called CONNECT");
        return (message) -> {
            globalLogger.info("SENT: "+message);
            lastSentMessage = message;
            sentMessageSemaphore.release();
        };
    }

    public void whenSentMessageThen(BiConsumer<String, Consumer<String>> fn) {
        try {
            sentMessageSemaphore.acquire(1);
            fn.accept(lastSentMessage, (received) -> {
                receiver.accept(ByteBuffer.wrap(received.getBytes()));
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}