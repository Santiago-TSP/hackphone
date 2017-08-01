package hackphone.phone.modifiers;

import gov.nist.javax.sip.header.Contact;
import hackphone.phone.configuration.SignallingContext;

import javax.sip.address.Address;
import javax.sip.address.SipURI;
import javax.sip.message.Message;
import java.text.ParseException;

public class ModifierContact extends ModifierAbstract implements Modifier {

    @Override
    public void update(Message message, SignallingContext context) {
        try {
            Contact contact = (Contact) message.getHeader(Contact.NAME);
            if(contact==null) {
                contact = (Contact)headerFactory.createContactHeader();
                contact.setExpires(3600);
                message.addHeader(contact);
            }
            SipURI newSipUri = addressFactory.createSipURI(context.accountName(), context.myIp());
            newSipUri.setPort(context.myPort());
            newSipUri.setParameter("rinstance", "a47dc4158b6ae5fe");
            Address newAddress = addressFactory.createAddress(newSipUri);
            contact.setAddress(newAddress);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
