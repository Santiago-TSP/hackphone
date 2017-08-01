package hackphone.phone.inviteing;

import hackphone.phone.ConfiguredStrategies;
import hackphone.phone.configuration.SignallingContext;
import hackphone.phone.detectors.DetectedRequest;
import hackphone.phone.detectors.DetectedResponse;
import hackphone.phone.io.SignallingSender;
import hackphone.phone.registering.stateMachine.State;

public class InviteingStateMachine implements State, InviteingMachineViewForStates{

    final SignallingContext context;
    final SignallingSender sender;
    final InviteingEvents events;
    final ConfiguredStrategies strategies;
    State state;

    public InviteingStateMachine(
            SignallingContext context,
            SignallingSender sender,
            ConfiguredStrategies strategies,
            InviteingEvents events) {
        this.context = context;
        this.sender = sender;
        this.events = events;
        this.strategies = strategies;
        changeStateByInternalState(new StateInitializeInviteingWaitForUnauthorized(this));
    }


    @Override
    public ConfiguredStrategies getStrategies() {
        return strategies;
    }

    @Override
    public SignallingContext getContext() {
        return context;
    }

    @Override
    public SignallingSender getSender() {
        return sender;
    }

    @Override
    public InviteingEvents getEvents() {
        return events;
    }

    @Override
    public void disconnectDueToError(String reason) {
        // TODO: do it later
        context.logger().info("TODO: disconnect due to error in InviteingStateMAchine: "+reason);
    }

    @Override
    public void changeStateByInternalState(State candidate) {
        if (candidate != null) {
            this.state = candidate;
            this.state.enter();
        }
    }

    @Override
    public void enter() {
        state.enter();
    }

    @Override
    public void processSignal(DetectedRequest signal) {
        state.processSignal(signal);
    }

    @Override
    public void processSignal(DetectedResponse signal) {
        state.processSignal(signal);
    }
}
