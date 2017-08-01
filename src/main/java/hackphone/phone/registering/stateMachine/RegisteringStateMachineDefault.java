package hackphone.phone.registering.stateMachine;

import hackphone.phone.configuration.SignallingContext;
import hackphone.phone.registering.RegisteringEvents;
import hackphone.phone.detectors.DetectedRequest;
import hackphone.phone.detectors.DetectedResponse;
import hackphone.phone.io.SignallingSender;
import hackphone.phone.registering.RegisteringStateMachine;

public class RegisteringStateMachineDefault implements StateMachineViewForStates, RegisteringStateMachine {

    State state;

    final SignallingSender sender;
    final SignallingContext context;
    final RegisteringEvents events;
    //
    final RegisteringStateMachineDefaultServiceOptions optionsProcessor;

    public RegisteringStateMachineDefault(
            SignallingSender sender,
            SignallingContext context,
            RegisteringEvents events) {
        this.sender = sender;
        this.context = context;
        this.events = events;
        changeStateByInternalState(StateList.REGISTER_INITIALIZE);
        this.optionsProcessor = new RegisteringStateMachineDefaultServiceOptions(sender, context);
    }

    @Override
    public void changeStateByInternalState(StateList newState) {
        State candidate = null;
        switch (newState) {
            case REGISTER_INITIALIZE:
                candidate = new StateInitializeRegistering(this);
                break;
            case REGISTER_INITIALIZE_WAIT_FOR_UNAUTHORIZED:
                candidate = new StateInitializeRegisteringWaitForUnauthorized(this);
                break;
            case REGISTER_INITIALIZE_SENDING_REGISTER_WITH_AUTHORIZATION:
                candidate = new StateInitializeRegisteringSendingRegisterWithAuthorization(this);
                break;

        }
        changeStateByInternalState(candidate);
    }

    @Override
    public void changeStateByInternalState(State candidate) {
        if (candidate != null) {
            this.state = candidate;
            this.state.enter();
        }
    }

    @Override
    public void disconnectDueToError(String reason) {
        // TODO
        // Wyrejestrowac kanal UDP
        context.logger().info("Disconnect due to Error " + reason);
    }

    @Override
    public void enter() {
        state.enter();
    }

    @Override
    public void incomingSignal(DetectedResponse signal) {
        state.processSignal(signal);
    }

    @Override
    public void incomingSignal(DetectedRequest signal) {
        if (signal.isOption()) {
            // Optiony to takie pingi, beda przychodzic co jakis czas
            // nie wpuszczam ich do maszyny stanowej
            optionsProcessor.processSignal(signal);
        } else {
            state.processSignal(signal);
        }
    }

    @Override
    public SignallingSender getSender() {
        return sender;
    }

    @Override
    public SignallingContext getContext() {
        return context;
    }

    @Override
    public RegisteringEvents getEvents() {
        return events;
    }
}
