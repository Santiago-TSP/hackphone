package hackphone.phone.inviteing;

import hackphone.phone.detectors.ApiResponseUnauthorized;
import hackphone.phone.detectors.DetectedResponse;

import javax.sip.message.Request;

class StateInitializeInviteingWaitForUnauthorized extends StateAbstract {

    StateInitializeInviteingWaitForUnauthorized(InviteingMachineViewForStates stateMachineView) {
        super(stateMachineView);
    }

    @Override
    public void enter() {
        try {
            stateMachineView.getContext().getInviteingContext().setCallingTo(new CallingTo(stateMachineView.getContext().getInviteingContext().getCallingTo().getPhoneNumber()));
            stateMachineView.getContext().trafficable_myRtpPort(stateMachineView.getStrategies().evenUdpFinderStrategy().findEvenUdpPort());
            StateInitializeInviteingWaitForUnauthorizedInviteBuilder builder = new StateInitializeInviteingWaitForUnauthorizedInviteBuilder();
            Request invite = builder.build(stateMachineView.getContext(), stateMachineView.getContext().getInviteingContext().getCallingTo());
            stateMachineView.getSender().accept(invite.toString());
        } catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void processSignal(DetectedResponse signal) {
        if(signal.isTransactionDoesNotExist()) {
            stateMachineView.getEvents().onFailure(stateMachineView.getContext(), "Transaction for Resitering does not exists");
        }
        if(signal.isUnauthorized()) {
            ApiResponseUnauthorized unauthorized = (ApiResponseUnauthorized)signal.api();
            stateMachineView.getContext().trafficable_nonce(unauthorized.getNonce());
            stateMachineView.getContext().trafficable_realm(unauthorized.getRealm());
            stateMachineView.changeStateByInternalState(new StateInitializeInviteingSendingInviteWithAuthorization(stateMachineView));
        }
    }
}
