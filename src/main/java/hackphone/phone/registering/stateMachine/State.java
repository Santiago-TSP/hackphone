package hackphone.phone.registering.stateMachine;

import hackphone.phone.detectors.DetectedRequest;
import hackphone.phone.detectors.DetectedResponse;

public interface State {

    void enter();

    void processSignal(DetectedResponse signal);

    void processSignal(DetectedRequest signal);
}
