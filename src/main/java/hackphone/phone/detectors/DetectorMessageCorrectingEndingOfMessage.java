package hackphone.phone.detectors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DetectorMessageCorrectingEndingOfMessage {

    static String correct(String message) {
        String last4Chars = new String(message.getBytes(), message.length()- 4, 4);
        if(!"\r\n\r\n".equals(last4Chars)) {
            message = message.trim();
            message += "\r\n\r\n";
            return message;
        } else {
            return message;
        }
    }

    static String changeContentLength(String message, String exceptionMessage) {
        // Invalid content length 371 / 386
        Pattern pattern = Pattern.compile("Invalid content length ([0-9]{1,10}) / ([0-9]{1,10})");
        Matcher matcher = pattern.matcher(exceptionMessage);
        if(matcher.find()) {
            String dst = matcher.group(1);
            String src = matcher.group(2);
            return message.replace(src, dst);
        } else {
            return null;
        }
    }
}
