package hackphone.phone.inviteing;

import hackphone.phone.detectors.DetectedRequest;
import hackphone.phone.detectors.DetectedResponse;
import hackphone.phone.registering.stateMachine.State;

class StateAbstract implements State {

    protected final InviteingMachineViewForStates stateMachineView;

    public StateAbstract(InviteingMachineViewForStates stateMachineView) {
        this.stateMachineView = stateMachineView;
    }

    @Override
    public void enter() {
        // Supporting method
    }

    @Override
    public void processSignal(DetectedRequest signal) {
        // Supporting method
    }

    @Override
    public void processSignal(DetectedResponse signal) {
        // Supporting method
    }
}
