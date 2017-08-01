package hackphone.phone.modifiers;

import gov.nist.javax.sip.header.From;
import hackphone.phone.configuration.SignallingContext;

import javax.sip.address.Address;
import javax.sip.address.SipURI;
import javax.sip.message.Message;
import java.text.ParseException;

public class ModifierFrom extends ModifierAbstract implements Modifier {

    @Override
    public void update(Message request, SignallingContext context) {
        try {
            From from = (From) request.getHeader(From.NAME);
            SipURI newSipUri = addressFactory.createSipURI(context.accountName(), context.asteriskHost());
            Address newAddress = addressFactory.createAddress(newSipUri);
            newAddress.setDisplayName(context.accountDisplayName());
            from.setAddress(newAddress);
        } catch(ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
