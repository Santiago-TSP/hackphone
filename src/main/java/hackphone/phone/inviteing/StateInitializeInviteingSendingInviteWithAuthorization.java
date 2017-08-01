package hackphone.phone.inviteing;


import hackphone.phone.detectors.ApiResponseOK;
import hackphone.phone.detectors.ApiResponseRinging;
import hackphone.phone.detectors.ApiResponseSessionProgress;
import hackphone.phone.detectors.DetectedResponse;

import javax.sip.PeerUnavailableException;
import javax.sip.message.Request;
import java.text.ParseException;

public class StateInitializeInviteingSendingInviteWithAuthorization extends StateAbstract {

    StateInitializeInviteingSendingInviteWithAuthorization(InviteingMachineViewForStates stateMachineView) {
        super(stateMachineView);
    }

    @Override
    public void enter() {
        try {
            StateInitializeInviteingSendingInviteWithAuthorizationInviteBuilder builder = new StateInitializeInviteingSendingInviteWithAuthorizationInviteBuilder();
            Request request = builder.build(stateMachineView.getContext(), stateMachineView.getContext().getInviteingContext().getCallingTo());
            stateMachineView.getSender().accept(request.toString());
        } catch(PeerUnavailableException | ParseException ex) {
            stateMachineView.getContext().logger().error(ex);
        }
    }

    @Override
    public void processSignal(DetectedResponse signal) {
        if(signal.isTransactionDoesNotExist()) {
            stateMachineView.disconnectDueToError("Transaction for Resitering does not exists");
            stateMachineView.getEvents().onFailure(stateMachineView.getContext(), "Transaction does not exist");
        }
        if(signal.isUnauthorized()) {
            stateMachineView.getEvents().onFailure(stateMachineView.getContext(), "Unauthorized");
        }
        if(signal.isTemporarilyUnavailable()) {
            stateMachineView.getEvents().onFailure(stateMachineView.getContext(), "Temporarily Unavailable");
            stateMachineView.changeStateByInternalState(new StateInvitingFailed(stateMachineView));
        }
        if(signal.isServerInternalFailure()) {
            stateMachineView.getEvents().onFailure(stateMachineView.getContext(), "Server internal failure");
            stateMachineView.changeStateByInternalState(new StateInvitingFailed(stateMachineView));
        }
        if(signal.isBusyHere()) {
            stateMachineView.getEvents().onFailure(stateMachineView.getContext(), "Busy Here");
            stateMachineView.changeStateByInternalState(new StateInvitingFailed(stateMachineView));
        }
        if(signal.isDeclined()) {
            stateMachineView.getEvents().onFailure(stateMachineView.getContext(), "Declined");
            stateMachineView.changeStateByInternalState(new StateInvitingFailed(stateMachineView));
        }
        if(signal.isSessionProgress()) {
            ApiResponseSessionProgress sessionProgress = (ApiResponseSessionProgress)signal.api();
            stateMachineView.getContext().getInviteingContext().setToTag(sessionProgress.getToTag());
        }
        if(signal.isRinging()) {
            ApiResponseRinging ringing = (ApiResponseRinging)signal.api();
            stateMachineView.getContext().getInviteingContext().setToTag(ringing.getToTag());
            stateMachineView.getEvents().onRinging(stateMachineView.getContext());
        }
        if(signal.isOk()) {
            ApiResponseOK ok = (ApiResponseOK)signal.api();
            stateMachineView.getContext().getInviteingContext().setToTag(ok.getToTag());
            stateMachineView.getContext().logger().info("Phone is registered and authorized");
            stateMachineView.changeStateByInternalState(new StateInvitingSucceeded(stateMachineView));
            stateMachineView.getEvents().onSuccess(stateMachineView.getContext(), ok.getRtpPeer());
        }
    }
}
