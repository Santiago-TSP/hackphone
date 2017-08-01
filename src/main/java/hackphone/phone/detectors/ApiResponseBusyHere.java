package hackphone.phone.detectors;

import javax.sip.message.Response;

public class ApiResponseBusyHere implements ApiResponse {

    final Response response;

    public ApiResponseBusyHere(Response response) {
        this.response = response;
    }
}
