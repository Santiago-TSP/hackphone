package hackphone.phone.modifiers;

import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.address.AddressFactory;
import javax.sip.header.HeaderFactory;
import javax.sip.message.MessageFactory;

public class ModifierAbstract {

    protected final MessageFactory messageFactory;
    protected final AddressFactory addressFactory;
    protected final HeaderFactory headerFactory;

    ModifierAbstract() {
        try {
            messageFactory = SipFactory.getInstance().createMessageFactory();
            addressFactory = SipFactory.getInstance().createAddressFactory();
            headerFactory = SipFactory.getInstance().createHeaderFactory();
        } catch (PeerUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
