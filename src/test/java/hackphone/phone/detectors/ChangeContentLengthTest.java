package hackphone.phone.detectors;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChangeContentLengthTest {

    @Test
    public void changeSize() {
        String message = DetectorMessageCorrectingEndingOfMessage.changeContentLength("Content-Length: 386\n", "Invalid content length 371 / 386");
        assertEquals("Content-Length: 371\n", message);
    }

}
