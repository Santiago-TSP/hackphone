package hackphone.phone.io;

import java.util.function.Consumer;

@FunctionalInterface
public interface SignallingSender extends Consumer<String> {
}
