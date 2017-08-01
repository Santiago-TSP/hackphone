package hackphone.phone.detectors;

import hackphone.phone.configuration.SignallingContext;

import javax.sip.message.Request;
import javax.sip.message.Response;

class ApiRequestAck implements ApiRequest {
    final Request request;

    ApiRequestAck(Request request) {
        this.request = request;
    }

    @Override
    public Response createResponse(int statusCode, SignallingContext context) {
        throw new UnsupportedOperationException("Creating Response for ACK is forbidden");
    }
}
