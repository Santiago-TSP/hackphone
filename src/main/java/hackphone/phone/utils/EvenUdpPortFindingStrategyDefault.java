package hackphone.phone.utils;

public class EvenUdpPortFindingStrategyDefault implements EvenUdpPortFindingStrategy {

    @Override
    public int findEvenUdpPort() {
        return SocketUtils.findAvailablePort(5060, 40000);
    }
}
