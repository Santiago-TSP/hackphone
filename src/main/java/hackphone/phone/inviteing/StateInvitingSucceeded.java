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
public class StateInvitingSucceeded extends StateAbstract {

    StateInvitingSucceeded(InviteingMachineViewForStates stateMachineView) {
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
        stateMachineView.getContext().logger().info("StateInvitingSucceeded # RESPONSE # "+signal);
    }

    @Override
    public void processSignal(DetectedRequest signal) {
        if(signal.isBye()) {
            Response ok = signal.api().createResponse(Response.OK, stateMachineView.getContext());
            stateMachineView.getSender().accept(ok.toString());
            stateMachineView.getEvents().onBye(stateMachineView.getContext());
        } else {
            stateMachineView.getContext().logger().info("StateInvitingSucceeded # REQUEST # "+signal);
        }
    }
}
