package hackphone.phone.detectors;

class DetectedMessageIsNorResponseNorRequest implements DetectedMessage {

    final Throwable reason;

    public DetectedMessageIsNorResponseNorRequest(Throwable reason) {
        this.reason = reason;
    }

    public Throwable getReason() {
        return reason;
    }

    @Override
    public boolean isResponse() {
        return false;
    }

    @Override
    public boolean isRequest() {
        return false;
    }

    @Override
    public DetectedResponse responseDetails() {
        return null;
    }

    @Override
    public DetectedRequest requestDetails() {
        return null;
    }
}
