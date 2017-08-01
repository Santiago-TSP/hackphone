package hackphone.phone.detectors;

public interface DetectedRequest {

    boolean isOption();

    boolean isInvite();

    boolean isAcknowledge();

    boolean isBye();

    ApiRequest api();
}
