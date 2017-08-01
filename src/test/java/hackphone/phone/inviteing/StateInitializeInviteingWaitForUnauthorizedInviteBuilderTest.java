package hackphone.phone.inviteing;

import hackphone.phone.configuration.*;
import hackphone.phone.detectors.DetectorMessage;
import org.junit.Test;

import javax.sip.message.Request;

import static org.junit.Assert.*;

public class StateInitializeInviteingWaitForUnauthorizedInviteBuilderTest {


    @Test
    public void build_outgoing_invite() throws Exception{
        StateInitializeInviteingWaitForUnauthorizedInviteBuilder builder = new StateInitializeInviteingWaitForUnauthorizedInviteBuilder();
//        SignallingContext context = mock(SignallingContext.class);
        SignallingConfiguration configuration = TestableSignallingConfigurationFactory.testing();
        SignallingLazyConfiguration lazyConfiguration = new SignallingLazyConfiguration(43224);
        final SignallingContext context = new SignallingContext(configuration, lazyConfiguration);
        // given
        context.trafficable_myRtpPort(20000);
        context.trafficable_nonce("nonce_from_register");
        context.trafficable_realm("nonce_from_register");
        CallingTo callingTo = new CallingTo("555333222");

        // when
        Request request = builder.build(context, callingTo);
        System.out.println(request.toString());
        String stringRequest = request.toString();

        DetectorMessage detector = new DetectorMessage();
        detector.detect(request.toString());

        assertTrue(stringRequest.contains("c=IN IP4 10.5.1.208"));
        assertTrue(stringRequest.contains("m=audio 20000 RTP"));
        assertTrue(stringRequest.contains("INVITE sip:555333222@testing.ailleron.com SIP/2.0"));
        assertTrue(stringRequest.contains("Contact: <sip:660@10.5.1.208"));
        assertTrue(stringRequest.contains("To: \"MaMi\" <sip:555333222@testing.ailleron.com"));
        assertTrue(stringRequest.contains("Call-ID: random-invite-"));
    }

}