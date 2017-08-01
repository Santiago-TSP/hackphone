package hackphone.phone.io;

import hackphone.phone.configuration.GlobalLogger;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

class InputOutputStrategyProductionSignallingSelector {

    final Selector selector;
    final Thread thread;
    final AtomicBoolean working = new AtomicBoolean(true);
    final GlobalLogger globalLogger;

    InputOutputStrategyProductionSignallingSelector(final GlobalLogger globalLogger) throws IOException {
        this.globalLogger = globalLogger;
        this.selector = Selector.open();
        this.thread = new Thread(this::selecting);
        this.thread.start();
    }

    void register(DatagramChannel channel, SignallingReceiver attachment) throws ClosedChannelException {
        selector.wakeup();
        SelectionKey registered = channel.register(selector, SelectionKey.OP_READ);
        registered.attach(attachment);
    }

    void shutdown() throws IOException {
        System.out.print("SHUTDOWN in Signalling Selector");
        selector.wakeup();
        working.set(false);
        selector.close();
    }

    void selecting() {
        try {
            while (working.get() && !Thread.currentThread().isInterrupted()) {
                int selected = selector.select(500L);
                if (selected > 0) {
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        try {
                            SelectionKey key = keyIterator.next();
                            if (key.isReadable()) {
                                read(key);
                            }
                            if(key.isConnectable()) {
                                globalLogger.info("Connected key "+key);
                            }
                        } finally {
                            keyIterator.remove();
                        }
                    }
                } else {
                    Thread.yield();
                }
            }
        } catch(Exception ex) {
            try {
                globalLogger.error(ex);
                shutdown();
            } catch(Exception ex2) {
                globalLogger.error(ex2);
            }
        }
    }

    private void read(SelectionKey key) throws IOException {
        ByteBuffer dataBuffer = ByteBuffer.allocate(2048);
        try {
            DatagramChannel channel = (DatagramChannel) key.channel();
            int read = channel.read(dataBuffer);
            if (read >= 0) {
                //System.out.println("SIP READ DATA "+read);
                SignallingReceiver attachment = (SignallingReceiver)key.attachment();
                attachment.accept(dataBuffer);
            } else {
                //System.out.println("READ "+read);
                close(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof PortUnreachableException) {
                close(key);
            } else {
                close(key);
                throw e;
            }
        }
    }

    static void close(SelectionKey key) throws IOException {
        SignallingReceiver attachment = (SignallingReceiver)key.attachment();
        attachment.disconnected();
        key.channel().close();
        key.cancel();
    }
}
