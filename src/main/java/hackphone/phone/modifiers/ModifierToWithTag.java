package hackphone.phone.modifiers;

import gov.nist.javax.sip.header.To;
import hackphone.phone.configuration.SignallingContext;

import javax.sip.address.Address;
import javax.sip.address.SipURI;
import javax.sip.message.Message;
import java.text.ParseException;

public class ModifierToWithTag extends ModifierAbstract implements Modifier {

    @Override
    public void update(Message request, SignallingContext context) {
        try {
            To to = (To) request.getHeader(To.NAME);
            SipURI newSipUri = addressFactory.createSipURI(context.accountName(), context.asteriskHost());
            newSipUri.setUser(context.getInviteingContext().getCallingTo().getPhoneNumber());
            Address newAddress = addressFactory.createAddress(newSipUri);
            newAddress.setDisplayName(context.accountDisplayName());
            to.setAddress(newAddress);
            to.setTag(context.getInviteingContext().getToTag());
        } catch (ParseException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
