package hackphone.phone.utils;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

/**
 * Copy-Paste from Spring
 */
class SocketUtils {

    private static final Random random = new Random(System.currentTimeMillis());

    public static int findAvailablePort(int minPort, int maxPort) {
        int portRange = maxPort - minPort;
        int candidatePort;
        int searchCounter = 0;
        do {
            if (++searchCounter > portRange) {
                throw new IllegalStateException("Could not find an available");
            }
            candidatePort = findRandomPort(minPort, maxPort);
        } while (!isPortAvailable(candidatePort));

        return candidatePort;
    }

    private static int findRandomPort(int minPort, int maxPort) {
        int portRange = maxPort - minPort;
        int candidate =  minPort + random.nextInt(portRange + 1);
        if(candidate%2==0) {
            return candidate;
        } else {
            return candidate+1;
        }
    }

    private static boolean isPortAvailable(int port) {
        try {
            DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("localhost"));
            socket.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
