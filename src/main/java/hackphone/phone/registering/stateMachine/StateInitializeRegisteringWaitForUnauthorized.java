package hackphone.phone.registering.stateMachine;

import hackphone.phone.detectors.ApiResponseUnauthorized;
import hackphone.phone.detectors.DetectedResponse;

class StateInitializeRegisteringWaitForUnauthorized extends StateAbstract {

    StateInitializeRegisteringWaitForUnauthorized(StateMachineViewForStates stateMachineView) {
        super(stateMachineView);
    }

    @Override
    public void processSignal(DetectedResponse signal) {
        if(signal.isTransactionDoesNotExist()) {
            stateMachineView.disconnectDueToError("Transaction for Resitering does not exists");
            stateMachineView.getEvents().onRegisteringFailed(stateMachineView.getContext(), "Transaction does not exist");
        }
        if(signal.isUnauthorized()) {
            stateMachineView.getContext().logger().info("Unauthorized");
            ApiResponseUnauthorized unauthorized = (ApiResponseUnauthorized)signal.api();
            stateMachineView.getContext().trafficable_nonce(unauthorized.getNonce());
            stateMachineView.getContext().trafficable_realm(unauthorized.getRealm());
            stateMachineView.changeStateByInternalState(StateList.REGISTER_INITIALIZE_SENDING_REGISTER_WITH_AUTHORIZATION);
        }
    }
}
