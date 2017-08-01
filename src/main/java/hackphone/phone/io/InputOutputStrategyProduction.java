package hackphone.phone.io;

import hackphone.phone.configuration.GlobalLogger;
import hackphone.phone.configuration.SignallingContext;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class InputOutputStrategyProduction implements InputOutputStrategy {

    final InputOutputStrategyProductionSignallingSelector selector;

    public InputOutputStrategyProduction(GlobalLogger globalLogger) throws IOException {
        this.selector = new InputOutputStrategyProductionSignallingSelector(globalLogger);
    }

    @Override
    public SignallingSender connect(SignallingReceiver receiver, SignallingContext context) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            DatagramSocket socket = channel.socket();
            socket.bind(new InetSocketAddress(context.myIp(), context.myPort()));
            selector.register(channel, receiver);
            channel.connect(context.asterisk());
            return (message) -> {
                try {
                    channel.write(ByteBuffer.wrap(message.getBytes()));
                } catch (IOException e) {
                    throw new RuntimeException(e);

                }
            };
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
