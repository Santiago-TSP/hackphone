package hackphone.phone.detectors;

import javax.sip.message.Response;

class DetectedMessageIsResponse implements DetectedMessage {

    final Response response;

    public DetectedMessageIsResponse(Response response) {
        this.response= response;
    }

    @Override
    public boolean isResponse() {
        return true;
    }

    @Override
    public boolean isRequest() {
        return false;
    }

    @Override
    public DetectedRequest requestDetails() {
        return null;
    }

    @Override
    public DetectedResponse responseDetails() {
        return new DetectedResponse() {

            @Override
            public boolean isTemporarilyUnavailable() {
                return response.getStatusCode()==Response.TEMPORARILY_UNAVAILABLE;
            }

            @Override
            public boolean isServerInternalFailure() {
                return response.getStatusCode()==Response.SERVER_INTERNAL_ERROR;
            }

            @Override
            public boolean isBusyHere() {
                return response.getStatusCode()==Response.BUSY_HERE;
            }

            @Override
            public boolean isTrying() {
                return response.getStatusCode()==Response.TRYING;
            }

            @Override
            public boolean isDeclined() {
                return response.getStatusCode()==Response.DECLINE;
            }

            @Override
            public boolean isSessionProgress() {
                return response.getStatusCode()==Response.SESSION_PROGRESS;
            }

            @Override
            public boolean isRinging() {
                return response.getStatusCode()==Response.RINGING;
            }

            @Override
            public boolean isUnauthorized() {
                return response.getStatusCode()==Response.UNAUTHORIZED;
            }

            @Override
            public boolean isOk() {
                return response.getStatusCode()==Response.OK;
            }

            @Override
            public boolean isTransactionDoesNotExist() {
                return response.getStatusCode()==Response.CALL_OR_TRANSACTION_DOES_NOT_EXIST;
            }

            @Override
            public ApiResponse api() {
                if(isUnauthorized()) {
                    return new ApiResponseUnauthorized(response);
                }
                if(isOk()) {
                    return new ApiResponseOK(response);
                }
                if(isRinging()) {
                    return new ApiResponseRinging(response);
                }
                if(isSessionProgress()) {
                    return new ApiResponseSessionProgress(response);
                }
                if(isBusyHere()) {
                    return new ApiResponseBusyHere(response);
                }
                if(isTemporarilyUnavailable()) {
                    return new ApiResponseTemporarilyUnavailable(response);
                }
                if(isServerInternalFailure()) {
                    return new ApiResponseServerInternalFailure(response);
                }
                if(isDeclined()) {
                    return new ApiResponseDeclinded(response);
                }
                return null;
            }
        };
    }
}
