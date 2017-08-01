package hackphone.phone.detectors;

import javax.sip.message.Response;

public class ApiResponseServerInternalFailure implements ApiResponse {

    final Response response;


    public ApiResponseServerInternalFailure(Response response) {
        this.response = response;
    }
}
