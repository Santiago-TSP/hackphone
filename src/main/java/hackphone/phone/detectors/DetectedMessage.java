package hackphone.phone.detectors;

public interface DetectedMessage {

    boolean isResponse();

    boolean isRequest();

    DetectedResponse responseDetails();

    DetectedRequest requestDetails();
}
