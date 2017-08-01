package hackphone.phone.detectors;

import javax.sip.message.Response;

public class ApiResponseDeclinded implements ApiResponse {

    final Response response;

    public ApiResponseDeclinded(Response response) {
        this.response = response;
    }
}
