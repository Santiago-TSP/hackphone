package hackphone.phone.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * Copy-Paste from Open Source
 * https://github.com/SnMill/network-tools/blob/master/src/main/java/network/tools/bestaddress/AvailableAddressesProvider.java
 *
 */
public class AvailableAddressesProvider {

    private final List<String> ignored = new ArrayList<>();

    public AvailableAddressesProvider() {
        ignored.add("127.0.0.1");
        ignored.add("192.168");
    }

    public Iterator<InetAddress> get() {
        try {
            List<InetAddress> result = new ArrayList<>();
            Enumeration<NetworkInterface> interfaces =  NetworkInterface.getNetworkInterfaces();
            while(interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if(address instanceof Inet4Address) {   // TODO: what about IP6 ?
                        boolean accepted = true;
                        for(String ignorable : ignored) {
                            if(address.getHostAddress().startsWith(ignorable)) {
                                accepted=false;
                            }
                        }
                        if(accepted) {
                            result.add(address);
                        }
                    }
                }
            }
            return result.iterator();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
