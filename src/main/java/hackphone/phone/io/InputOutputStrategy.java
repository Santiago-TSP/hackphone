package hackphone.phone.io;

import hackphone.phone.configuration.SignallingContext;

public interface InputOutputStrategy {

   SignallingSender connect(SignallingReceiver receiver, SignallingContext context);
}
