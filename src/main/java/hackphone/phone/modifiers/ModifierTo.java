package hackphone.phone.modifiers;

import gov.nist.javax.sip.header.To;
import hackphone.phone.configuration.SignallingContext;

import javax.sip.address.Address;
import javax.sip.address.SipURI;
import javax.sip.message.Message;
import java.text.ParseException;

public class ModifierTo extends ModifierAbstract implements Modifier {

    final boolean inviteing;

    public ModifierTo() {
        this.inviteing = false;
    }

    public ModifierTo(boolean inviteing) {
        this.inviteing = inviteing;
    }

    @Override
    public void update(Message request, SignallingContext context) {
        try {
            if(inviteing) {
                To to = (To) request.getHeader(To.NAME);
                SipURI newSipUri = addressFactory.createSipURI(context.accountName(), context.asteriskHost());
                newSipUri.setUser(context.getInviteingContext().getCallingTo().getPhoneNumber());
                Address newAddress = addressFactory.createAddress(newSipUri);
                newAddress.setDisplayName(context.accountDisplayName());
                to.setAddress(newAddress);
            } else {
                To to = (To) request.getHeader(To.NAME);
                SipURI newSipUri = addressFactory.createSipURI(context.accountName(), context.asteriskHost());
                Address newAddress = addressFactory.createAddress(newSipUri);
                newAddress.setDisplayName(context.accountDisplayName());
                to.setAddress(newAddress);
            }
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
