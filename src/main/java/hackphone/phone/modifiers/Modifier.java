package hackphone.phone.modifiers;

import hackphone.phone.configuration.SignallingContext;

import javax.sip.message.Message;

@FunctionalInterface
public interface Modifier {

    void update(Message request, SignallingContext context);
}
