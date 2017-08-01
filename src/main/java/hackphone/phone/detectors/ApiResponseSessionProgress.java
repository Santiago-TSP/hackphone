package hackphone.phone.detectors;

import gov.nist.javax.sip.header.To;

import javax.sip.message.Response;

public class ApiResponseSessionProgress implements ApiResponse {

    final Response response;
    //
    final String toTag;


    public ApiResponseSessionProgress(Response response) {
        this.response = response;
        To to = (To)response.getHeader(To.NAME);
        this.toTag = to.getTag();
    }

    public String getToTag() {
        return toTag;
    }
}
