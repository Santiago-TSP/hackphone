package hackphone.phone.registering.stateMachine;

import hackphone.phone.configuration.SignallingContext;
import hackphone.phone.detectors.DetectedRequest;
import hackphone.phone.io.SignallingSender;

import javax.sip.message.Response;

class RegisteringStateMachineDefaultServiceOptions {

    final SignallingSender sender;
    final SignallingContext context;

    RegisteringStateMachineDefaultServiceOptions(SignallingSender sender, SignallingContext context) {
        this.sender = sender;
        this.context = context;
    }

    void processSignal(DetectedRequest signal) {
        try {
            Response response = signal.api().createResponse(Response.OK, context);
            sender.accept(response.toString());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
