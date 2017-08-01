package hackphone.phone.registering.stateMachine;

import hackphone.phone.detectors.ApiRequestInvite;
import hackphone.phone.detectors.DetectedRequest;
import hackphone.phone.detectors.DetectedResponse;

import javax.sip.message.Response;


/**
 * TODO: externalize it to separated domain
 */
class StateRegisterSucceeded extends StateAbstract {

    final State customizedStateInRegisteredState;

    StateRegisterSucceeded(StateMachineViewForStates stateMachineView, State customizedStateInRegisteredState) {
        super(stateMachineView);
        this.customizedStateInRegisteredState = customizedStateInRegisteredState;
    }

    @Override
    public void processSignal(DetectedResponse signal) {
        customizedStateInRegisteredState.processSignal(signal);
    }

    InformationAboutCaller cached_caller;

    @Override
    public void processSignal(DetectedRequest signal) {
        if (signal.isAcknowledge()) {
            // caching restoring
            stateMachineView.getEvents().onIncomingCall(stateMachineView.getContext(), cached_caller);
        } else if (signal.isInvite()) {
            // caching remember
            ApiRequestInvite invite = (ApiRequestInvite) signal.api();
            cached_caller = new InformationAboutCaller(invite);
            //
            Response trying = signal.api().createResponse(Response.TRYING, stateMachineView.getContext());
            stateMachineView.getSender().accept(trying.toString());
            Response ringing = signal.api().createResponse(Response.RINGING, stateMachineView.getContext());
            stateMachineView.getSender().accept(ringing.toString());
            Response busyHere = signal.api().createResponse(Response.BUSY_HERE, stateMachineView.getContext());
            stateMachineView.getSender().accept(busyHere.toString());
        } else {
            customizedStateInRegisteredState.processSignal(signal);
        }
    }
}
