package hackphone.phone.modifiers;

import hackphone.phone.configuration.SignallingContext;

import javax.sip.address.SipURI;
import javax.sip.message.Message;
import javax.sip.message.Request;
import java.text.ParseException;

public class ModifierRequestUri extends ModifierAbstract implements Modifier {

    final boolean inviteing;

    public ModifierRequestUri() {
        this.inviteing = false;
    }

    public ModifierRequestUri(boolean inviteing) {
        this.inviteing = inviteing;
    }

    @Override
    public void update(Message message, SignallingContext context) {
        try {
            if(inviteing) {
                Request request = (Request)message;
                SipURI uri = (SipURI)request.getRequestURI();
                uri.setHost(context.asteriskHost());
                uri.setUser(context.getInviteingContext().getCallingTo().getPhoneNumber());
            } else {
                Request request = (Request)message;
                SipURI uri = (SipURI)request.getRequestURI();
                uri.setHost(context.asteriskHost());
            }
        } catch(ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
