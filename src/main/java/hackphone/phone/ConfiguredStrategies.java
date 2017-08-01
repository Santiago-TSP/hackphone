package hackphone.phone;

import hackphone.media.io.InputOutputMediaStrategy;
import hackphone.phone.io.InputOutputStrategy;
import hackphone.phone.utils.EvenUdpPortFindingStrategy;
import hackphone.phone.utils.EvenUdpPortFindingStrategyDefault;

import java.io.IOException;

public class ConfiguredStrategies {
    final InputOutputStrategy inputOutputStrategy;
    final InputOutputMediaStrategy mediaStrategy;
    final EvenUdpPortFindingStrategy evenUdpPortFindingStrategy;

    public ConfiguredStrategies(InputOutputStrategy inputOutputStrategy, InputOutputMediaStrategy mediaStrategy) throws IOException {
        this.inputOutputStrategy = inputOutputStrategy;
        this.mediaStrategy = mediaStrategy;
        this.evenUdpPortFindingStrategy = new EvenUdpPortFindingStrategyDefault();
    }

    InputOutputStrategy inputOutputStrategy() {
        return inputOutputStrategy;
    }

    public InputOutputMediaStrategy mediaStrategy(){ return mediaStrategy; }

    public EvenUdpPortFindingStrategy evenUdpFinderStrategy() {
        return evenUdpPortFindingStrategy;
    }
}
