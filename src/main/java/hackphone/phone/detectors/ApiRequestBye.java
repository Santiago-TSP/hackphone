package hackphone.phone.detectors;

import hackphone.phone.configuration.SignallingContext;

import javax.sip.message.Request;
import javax.sip.message.Response;
import java.text.ParseException;

class ApiRequestBye implements ApiRequest {

    final Request request;

    ApiRequestBye(Request request) {
        this.request = request;
    }

    @Override
    public Response createResponse(int statusCode, SignallingContext context) {
        try {
            ApiRequestByeResponseBuilder builder = new ApiRequestByeResponseBuilder();
            return builder.create(request, statusCode, context);
        } catch(ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
