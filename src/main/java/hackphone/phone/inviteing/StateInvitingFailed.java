package hackphone.phone.inviteing;


import hackphone.phone.detectors.DetectedRequest;
import hackphone.phone.detectors.DetectedResponse;

import javax.sip.PeerUnavailableException;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.text.ParseException;

/**
 * TODO: NEW STATE FOR BYE, HOLD, UNHOLD etc...
 */
public class StateInvitingFailed extends StateAbstract {

    StateInvitingFailed(InviteingMachineViewForStates stateMachineView) {
        super(stateMachineView);
    }

    @Override
    public void enter() {
        try {
            StateInvitingSucceededAckBuilder builder = new StateInvitingSucceededAckBuilder();
            Request request = builder.build(stateMachineView.getContext(), stateMachineView.getContext().getInviteingContext().getCallingTo());
            stateMachineView.getSender().accept(request.toString());
        } catch(PeerUnavailableException | ParseException ex) {
            stateMachineView.getContext().logger().error(ex);
        }
    }

    @Override
    public void processSignal(DetectedResponse signal) {
        stateMachineView.getContext().logger().info("StateInvitingFailed # RESPONSE # "+signal);
    }

    @Override
    public void processSignal(DetectedRequest signal) {
        stateMachineView.getContext().logger().info("StateInvitingFailed # REQUEST # "+signal);
    }
}
