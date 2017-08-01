package hackphone.phone.modifiers;

import gov.nist.javax.sip.header.CallID;
import hackphone.phone.configuration.SignallingContext;

import javax.sip.message.Message;
import java.text.ParseException;

public class ModifierCallID extends ModifierAbstract implements Modifier {

    final boolean inviteing;

    public ModifierCallID() {
        this.inviteing = false;
    }

    public ModifierCallID(boolean inviteing) {
        this.inviteing = inviteing;
    }


    @Override
    public void update(Message message, SignallingContext context) {
        try {
            if(inviteing) {
                CallID id = (CallID) message.getHeader(CallID.NAME);
                id.setCallId(context.callIdForInviteing());
            } else {
                CallID id = (CallID) message.getHeader(CallID.NAME);
                id.setCallId(context.callIdForRegistering());
            }
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
