package hackphone.phone.registering.stateMachine;

import javax.sip.message.Request;
import java.text.ParseException;

class StateInitializeRegistering extends StateAbstract {

    StateInitializeRegistering(StateMachineViewForStates stateMachineView) {
        super(stateMachineView);
    }

    @Override
    public void enter() {
        stateMachineView.changeStateByInternalState(StateList.REGISTER_INITIALIZE_WAIT_FOR_UNAUTHORIZED);
        StateInitializeRegisteringRegisterInitializeBuilder builder = new StateInitializeRegisteringRegisterInitializeBuilder();
        try {
            Request request = builder.build(stateMachineView.getContext());
            stateMachineView.getSender().accept(request.toString());
        } catch (ParseException e) {
            stateMachineView.getContext().logger().error(e);
        }
    }
}
