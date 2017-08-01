package hackphone.phone.registering;

import hackphone.phone.registering.stateMachine.InformationAboutCaller;
import hackphone.phone.configuration.SignallingContext;
import hackphone.phone.io.SignallingSender;
import hackphone.phone.registering.stateMachine.State;

public interface RegisteringEvents {

    State createCustomizedStateForRegisteredState(SignallingContext context, SignallingSender sender);
    void onRegisteringSucceeded(SignallingContext context);
    void onRegisteringFailed(SignallingContext context, String reason);
    //
    void onIncomingCall(SignallingContext context, InformationAboutCaller caller);
}
