package hackphone.phone.detectors;

import javax.sip.message.Request;

class DetectedMessageIsRequest implements DetectedMessage {

    final Request request;

    public DetectedMessageIsRequest(Request request) {
        this.request= request;
    }

    @Override
    public boolean isResponse() {
        return false;
    }

    @Override
    public boolean isRequest() {
        return true;
    }

    @Override
    public DetectedRequest requestDetails() {
        return new DetectedRequest() {

            @Override
            public boolean isOption() {
                return Request.OPTIONS.equalsIgnoreCase(request.getMethod());
            }

            @Override
            public boolean isAcknowledge() {
                return Request.ACK.equalsIgnoreCase(request.getMethod());
            }

            @Override
            public boolean isInvite() {
                return Request.INVITE.equalsIgnoreCase(request.getMethod());
            }

            @Override
            public boolean isBye() {
                return Request.BYE.equalsIgnoreCase(request.getMethod());
            }

            @Override
            public ApiRequest api() {
                if(isOption()) {
                    return new ApiRequestOptions(request);
                }
                if(isInvite()) {
                    return new ApiRequestInvite(request);
                }
                if(isAcknowledge()) {
                    return new ApiRequestAck(request);
                }
                if(isBye()) {
                    return new ApiRequestBye(request);
                }
                return null;
            }
        };
    }

    @Override
    public DetectedResponse responseDetails() {
        return null;
    }
}
