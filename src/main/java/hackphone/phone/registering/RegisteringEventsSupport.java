package hackphone.phone.registering;

import hackphone.phone.registering.stateMachine.InformationAboutCaller;
import hackphone.phone.configuration.SignallingContext;
import hackphone.phone.io.SignallingSender;
import hackphone.phone.registering.stateMachine.State;

public class RegisteringEventsSupport implements RegisteringEvents {

    @Override
    public State createCustomizedStateForRegisteredState(SignallingContext context, SignallingSender sender) {
        return null;
    }

    @Override
    public void onRegisteringFailed(SignallingContext context, String reason) {
        context.logger().info("Registering failed: "+reason);
    }

    @Override
    public void onRegisteringSucceeded(SignallingContext context) {
        context.logger().info("Registering Succeeded");
    }

    @Override
    public void onIncomingCall(SignallingContext context, InformationAboutCaller caller) {
        context.logger().info("Incoming call");
    }
}