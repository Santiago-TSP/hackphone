package hackphone.phone.registering.stateMachine;

import hackphone.phone.detectors.DetectedRequest;
import hackphone.phone.detectors.DetectedResponse;

class EmptyState implements State{


    @Override
    public void enter() {

    }

    @Override
    public void processSignal(DetectedResponse signal) {

    }

    @Override
    public void processSignal(DetectedRequest signal) {

    }
}
