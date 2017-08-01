package hackphone.phone.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketUtilsTest {
    @Test
    public void portsAreEven() {
        for(int i=0; i<10; i++) {
            int port = SocketUtils.findAvailablePort(5060, 30000);
            System.out.println("Free UDP Port: "+port);
            assertTrue(port % 2 == 0);
        }
    }
}