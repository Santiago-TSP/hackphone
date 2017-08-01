package hackphone.phone.detectors;

import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.address.AddressFactory;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.text.ParseException;

public class DetectorMessage {

    final MessageFactory messageFactory;
    final AddressFactory addressFactory;

    public DetectorMessage() {
        try {
            messageFactory = SipFactory.getInstance().createMessageFactory();
            addressFactory = SipFactory.getInstance().createAddressFactory();
        } catch (PeerUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public DetectedMessage detect(String message) {
        message = DetectorMessageCorrectingEndingOfMessage.correct(message);
        try {
            Response response = messageFactory.createResponse(message);
            return new DetectedMessageIsResponse(response);
        } catch (ParseException exResponse) {
            if(exResponse.getMessage().contains("Invalid content length")) {
//                exResponse.printStackTrace();
//                System.out.println("PARSE ERROR DURING PARSING "+exResponse);
                return detect(DetectorMessageCorrectingEndingOfMessage.changeContentLength(message, exResponse.getMessage()));
            }
            try {
                Request request = messageFactory.createRequest(message);
                return new DetectedMessageIsRequest(request);
            } catch(ParseException exRequest) {
                if(exRequest.getMessage().contains("Invalid content length")) {
//                    exRequest.printStackTrace();
//                    System.out.println("PARSE ERROR DURING PARSING "+exRequest);
                    return detect(DetectorMessageCorrectingEndingOfMessage.changeContentLength(message, exRequest.getMessage()));
                }
                return new DetectedMessageIsNorResponseNorRequest(exRequest);
            }
        }
    }
}
