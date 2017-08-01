package hackphone.phone.modifiers;

import gov.nist.javax.sip.header.Via;
import hackphone.phone.configuration.SignallingContext;

import javax.sip.InvalidArgumentException;
import javax.sip.message.Message;
import java.text.ParseException;

public class ModifierVia extends ModifierAbstract implements Modifier {

    @Override
    public void update(Message request, SignallingContext context) {
        try {
            Via via = (Via)request.getHeader(Via.NAME);
            via.setHost(context.myIp());
            via.setPort(context.myPort());
        } catch (ParseException | InvalidArgumentException ex) {
            throw new RuntimeException(ex);
        }
    }
}
