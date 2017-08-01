package hackphone.media;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class DtmfDetector implements Consumer<ByteBuffer> {

    private static final int LENGTH_OF_DTMF = 16;
    private final Consumer<String> numbersConsumer;
    private final Consumer<String> keysConsumer;
    private StringBuffer buffer = new StringBuffer();
    private boolean first = true;

    public DtmfDetector(Consumer<String> numbersConsumer, Consumer<String> keysConsumer) {
        this.numbersConsumer = numbersConsumer;
        this.keysConsumer = keysConsumer;
    }

    @Override
    public void accept(ByteBuffer byteBuffer) {
        //System.out.println("\ttraffic received: "+byteBuffer.position()+", "+byteBuffer.limit());
        if(byteBuffer.position()==LENGTH_OF_DTMF) {
            int number = (int)byteBuffer.get(12);
            Dtmf pressedKey = Dtmf.fromInt(number);
             byte end = byteBuffer.get(13);
            if(end==(byte)0x0a) {
                // NOT END
                dtmf(false, pressedKey.getDtmfAsString());
            }
            if(end==(byte)0x8a) {
                dtmf(true, pressedKey.getDtmfAsString());
            }
        }
    }

    private void notifyAboutTypedNumber() {
        String numberToNotify = buffer.toString();
        buffer = new StringBuffer("");
        if(numberToNotify!=null && numberToNotify.length()>0) {
            numbersConsumer.accept(numberToNotify);
        }
    }

    private synchronized void dtmf(boolean end, String key) {
        if(!end && first) {
            keysConsumer.accept(key);
            if(key.equalsIgnoreCase("#") || key.equalsIgnoreCase("*")) {
                notifyAboutTypedNumber();
                first = true;
            } else {
                buffer.append(key);
                first = false;
                // czekam az bedzie END
            }
        }
        if(end) {
            first = true;
        }
    }

    public enum Dtmf {

        KEY_0('0'),
        KEY_1('1'),
        KEY_2('2'),
        KEY_3('3'),
        KEY_4('4'),
        KEY_5('5'),
        KEY_6('6'),
        KEY_7('7'),
        KEY_8('8'),
        KEY_9('9'),
        KEY_START('*'),
        KEY_PLUS('+'),
        KEY_HASH('#');

        private char dtmf;

        Dtmf(char dtmf) {
            this.dtmf = dtmf;
        }

        public static Dtmf fromChar(char key) {
            for (Dtmf dtmf: Dtmf.values()) {
                if (dtmf.dtmf == key) {
                    return dtmf;
                }
            }
            return null;
        }

        public static Dtmf fromInt(int intKey) {
            char character;
            switch (intKey) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    character = Character.forDigit(intKey, 10);
                    return Dtmf.fromChar(character);
                case 10:
                    return Dtmf.fromChar('*');
                case 11:
                    return Dtmf.fromChar('#');
                default:
                    return null;
            }
        }

        public String getDtmfAsString() {
            return String.valueOf(dtmf);
        }
    }
}
