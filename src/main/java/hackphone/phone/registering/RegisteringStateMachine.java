package hackphone.phone.registering;

import hackphone.phone.detectors.DetectedRequest;
import hackphone.phone.detectors.DetectedResponse;

public interface RegisteringStateMachine {

    void enter();

    void incomingSignal(DetectedResponse signal);

    void incomingSignal(DetectedRequest signal);
}
