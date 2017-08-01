package hackphone.phone.registering;

import hackphone.phone.configuration.SignallingContext;
import hackphone.phone.io.SignallingSender;
import hackphone.phone.registering.stateMachine.RegisteringStateMachineDefault;

public class RegisteringStateMachineFactory {

    final SignallingSender sender;
    final SignallingContext context;

    public RegisteringStateMachineFactory(SignallingSender sender, SignallingContext context) {
        this.sender = sender;
        this.context = context;
    }

    public RegisteringStateMachine create(RegisteringEvents listener) {
        return new RegisteringStateMachineDefault(sender, context, listener);
    }
}
