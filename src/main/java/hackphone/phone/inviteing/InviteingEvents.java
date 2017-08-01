package hackphone.phone.inviteing;

import hackphone.phone.configuration.SignallingContext;

import java.net.InetSocketAddress;

public interface InviteingEvents {

    // Responses
    void onRinging(SignallingContext context);
    void onSuccess(SignallingContext context, InetSocketAddress rtpPeer);
    void onFailure(SignallingContext context, String reason);

    // Requests
    void onBye(SignallingContext context);
}
