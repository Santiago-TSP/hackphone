package hackphone.media.io;

import hackphone.phone.configuration.GlobalLoggerDefault;
import hackphone.phone.utils.EvenUdpPortFindingStrategyDefault;
import org.junit.Ignore;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class InputOutputMediaStrategyTest {

    @Ignore // work only on my machine
    @Test
    public void sendUdpFiles() throws Exception {
        InputOutputMediaStrategy strategy = new InputOutputMediaStrategyProduction(new GlobalLoggerDefault());
        int myUdpPort= new EvenUdpPortFindingStrategyDefault().findEvenUdpPort();

        InetSocketAddress me = new InetSocketAddress("10.5.1.208", myUdpPort);
        InetSocketAddress peer = new InetSocketAddress("10.7.1.115", 52345);

        MediaTrafficContext trafficContext = new MediaTrafficContext(me, peer);
        MediaSender sender = strategy.connect(new MediaReceiver() {
            @Override
            public void disconnected() {
                System.out.println("MEDIATRAFFIC: DISCONNECTED");
            }

            @Override
            public void accept(ByteBuffer byteBuffer) {
                System.out.println("MEDIATRAFFIC: GOT: "+byteBuffer.position()+", "+byteBuffer.limit()+", "+byteBuffer.capacity());
            }
        }, trafficContext);


        for(int i=0; i<10; i++) {
            sender.accept(ByteBuffer.wrap(new byte[]{(byte) 0xAA, (byte) 0xBB, (byte) 0xCC}));
        }

    }

}