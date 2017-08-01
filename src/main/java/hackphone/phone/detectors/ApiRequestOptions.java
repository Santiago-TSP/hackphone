package hackphone.phone.detectors;

import hackphone.phone.configuration.SignallingContext;

import javax.sip.message.Request;
import javax.sip.message.Response;
import java.text.ParseException;

class ApiRequestOptions implements ApiRequest {

    final Request request;

    ApiRequestOptions(Request request) {
        this.request = request;
    }

    @Override
    public Response createResponse(int statusCode, SignallingContext context) {
        try {
            ApiRequestOptionsResponseBuilder builder = new ApiRequestOptionsResponseBuilder();
            return builder.create(request, statusCode, context);
        } catch(ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
