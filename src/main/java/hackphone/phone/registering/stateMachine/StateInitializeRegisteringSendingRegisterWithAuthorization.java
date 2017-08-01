package hackphone.phone.registering.stateMachine;

import hackphone.phone.detectors.DetectedResponse;

import javax.sip.PeerUnavailableException;
import javax.sip.message.Request;
import java.text.ParseException;

public class StateInitializeRegisteringSendingRegisterWithAuthorization extends StateAbstract {

    StateInitializeRegisteringSendingRegisterWithAuthorization(StateMachineViewForStates stateMachineView) {
        super(stateMachineView);
    }

    @Override
    public void enter() {
        try {
            StateInitializeRegisteringSendingRegisterWithAuthorizationRegisterAutorizeBuilder builder = new StateInitializeRegisteringSendingRegisterWithAuthorizationRegisterAutorizeBuilder();
            Request request = builder.build(stateMachineView.getContext());
            stateMachineView.getSender().accept(request.toString());
        } catch(PeerUnavailableException | ParseException ex) {
            stateMachineView.getContext().logger().error(ex);
        }
    }

    @Override
    public void processSignal(DetectedResponse signal) {
        if(signal.isTransactionDoesNotExist()) {
            stateMachineView.disconnectDueToError("Transaction for Resitering does not exists");
            stateMachineView.getEvents().onRegisteringFailed(stateMachineView.getContext(), "Transaction does not exist");
        }
        if(signal.isUnauthorized()) {
            stateMachineView.getEvents().onRegisteringFailed(stateMachineView.getContext(), "Unauthorized");
        }
        if(signal.isOk()) {
            stateMachineView.getContext().logger().info("Phone is registered and authorized");
            State customizedStateInRegisteredState = stateMachineView
                    .getEvents()
                    .createCustomizedStateForRegisteredState(
                            stateMachineView.getContext(), stateMachineView.getSender()
                    );
            if(customizedStateInRegisteredState==null) {
                customizedStateInRegisteredState = new EmptyState();
            }
            stateMachineView.changeStateByInternalState(new StateRegisterSucceeded(stateMachineView, customizedStateInRegisteredState));
            stateMachineView.getEvents().onRegisteringSucceeded(stateMachineView.getContext());
        }
    }
}
