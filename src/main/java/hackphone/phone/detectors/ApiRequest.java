package hackphone.phone.detectors;

import hackphone.phone.configuration.SignallingContext;

import javax.sip.message.Response;

public interface ApiRequest {

    Response createResponse(int statusCode, SignallingContext context);
}
