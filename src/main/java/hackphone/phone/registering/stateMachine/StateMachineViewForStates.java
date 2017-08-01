package hackphone.phone.registering.stateMachine;

import hackphone.phone.configuration.SignallingContext;
import hackphone.phone.registering.RegisteringEvents;
import hackphone.phone.io.SignallingSender;

public interface StateMachineViewForStates {

    void changeStateByInternalState(StateList newState);

    void changeStateByInternalState(State newState);

    void disconnectDueToError(String reason);

    SignallingSender getSender();
    SignallingContext getContext();
    RegisteringEvents getEvents();
}
