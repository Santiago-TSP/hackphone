package hackphone.phone.inviteing;

import hackphone.phone.ConfiguredStrategies;
import hackphone.phone.configuration.SignallingContext;
import hackphone.phone.io.SignallingSender;
import hackphone.phone.registering.stateMachine.State;

interface InviteingMachineViewForStates {

    void changeStateByInternalState(State newState);

    void disconnectDueToError(String reason);

    SignallingSender getSender();
    SignallingContext getContext();
    InviteingEvents getEvents();
    ConfiguredStrategies getStrategies();
}
