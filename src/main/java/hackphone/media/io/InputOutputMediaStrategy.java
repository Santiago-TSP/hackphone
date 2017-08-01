package hackphone.media.io;


public interface InputOutputMediaStrategy {

   MediaSender connect(MediaReceiver receiver, MediaTrafficContext context);
}
