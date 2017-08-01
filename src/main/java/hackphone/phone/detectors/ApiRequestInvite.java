package hackphone.phone.detectors;

import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.header.From;
import hackphone.phone.configuration.SignallingContext;

import javax.sip.message.Request;
import javax.sip.message.Response;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiRequestInvite implements ApiRequest {

    final Request request;
    final ParserMediaTrafficAddress parserMediaTrafficAddress;

    ApiRequestInvite(Request request) {
        this.request = request;
        this.parserMediaTrafficAddress = new ParserMediaTrafficAddress(request.getRawContent());
    }

    @Override
    public Response createResponse(int statusCode, SignallingContext context) {
        try {
            ApiRequestInviteResponseBuilder builder = new ApiRequestInviteResponseBuilder();
            return builder.create(request, statusCode, context);
        } catch(ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getFromPhoneNumber() {
        From from = (From)request.getHeader(From.NAME);
        SipUri uri = (SipUri)from.getAddress().getURI();
        return uri.getUser();
    }

    public String getMediaTrafficIp() {
        return parserMediaTrafficAddress.getMediaTrafficIp();
    }

    public int getMediaTrafficPort() {
        return parserMediaTrafficAddress.getMediaTrafficPort();
    }
}
