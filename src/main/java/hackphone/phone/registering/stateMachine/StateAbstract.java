package hackphone.phone.registering.stateMachine;

import hackphone.phone.detectors.DetectedRequest;
import hackphone.phone.detectors.DetectedResponse;

public abstract class StateAbstract implements State{

    protected final StateMachineViewForStates stateMachineView;

    public StateAbstract(StateMachineViewForStates stateMachineView) {
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
