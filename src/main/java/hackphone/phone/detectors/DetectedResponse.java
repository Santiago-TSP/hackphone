package hackphone.phone.detectors;

public interface DetectedResponse {

    boolean isTrying();

    boolean isUnauthorized();

    boolean isOk();

    boolean isRinging();

    boolean isTemporarilyUnavailable();

    boolean isBusyHere();

    boolean isSessionProgress();

    boolean isTransactionDoesNotExist();

    boolean isServerInternalFailure();

    boolean isDeclined();

    ApiResponse api();
}
