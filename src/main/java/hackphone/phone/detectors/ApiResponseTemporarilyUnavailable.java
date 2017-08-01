package hackphone.phone.detectors;

import javax.sip.message.Response;

public class ApiResponseTemporarilyUnavailable implements ApiResponse {

    final Response response;


    public ApiResponseTemporarilyUnavailable(Response response) {
        this.response = response;
    }
}
