package hackphone.phone;

import hackphone.phone.registering.RegisteringEvents;

public interface PhoneDriver {

    void register(RegisteringEvents listener);

    void signallingSend(String signallingPacket);
}
