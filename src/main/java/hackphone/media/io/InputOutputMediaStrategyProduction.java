package hackphone.media.io;

import hackphone.phone.configuration.GlobalLogger;

import java.io.IOException;
import java.net.DatagramSocket;
import java.nio.channels.DatagramChannel;

public class InputOutputMediaStrategyProduction implements InputOutputMediaStrategy {

    final InputOutputMediaStrategyProductionSignallingSelector selector;

    public InputOutputMediaStrategyProduction(GlobalLogger globalLogger) throws IOException {
        this.selector = new InputOutputMediaStrategyProductionSignallingSelector(globalLogger);
    }

    @Override
    public MediaSender connect(MediaReceiver receiver, MediaTrafficContext context) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            DatagramSocket socket = channel.socket();
            socket.bind(context.getMyAddress());
            selector.register(channel, receiver);
            channel.connect(context.getPeerAddress());
            return (message) -> {
                try {
                    if(channel.isConnected()) {
                        channel.write(message);
                    }
                } catch (IOException e) {
                    // TODO: close?
                    e.printStackTrace();
                }
            };
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
