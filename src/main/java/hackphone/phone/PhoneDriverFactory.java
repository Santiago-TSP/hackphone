package hackphone.phone;

import hackphone.phone.configuration.PhoneAccount;
import hackphone.media.io.InputOutputMediaStrategyProduction;
import hackphone.phone.configuration.GlobalLogger;
import hackphone.phone.configuration.GlobalLoggerDefault;
import hackphone.phone.configuration.SignallingConfiguration;
import hackphone.phone.configuration.SignallingConfigurationFactory;
import hackphone.phone.io.InputOutputStrategyProduction;

import java.io.IOException;

public class PhoneDriverFactory {

    public static PhoneDriver build(SignallingConfiguration configuration, ConfiguredStrategies strategies) throws IOException {
        return new PhoneDriverDefault(configuration, strategies);
    }

    final ConfiguredStrategies strategies;

    public ConfiguredStrategies getStrategies() {
        return strategies;
    }

    final String asteriskHost;
    final int asteriskPort;

    public PhoneDriverFactory(String asteriskHost, int asteriskPort) throws Exception {
        this.asteriskHost = asteriskHost;
        this.asteriskPort = asteriskPort;
        GlobalLogger logger = new GlobalLoggerDefault();
        this.strategies = new ConfiguredStrategies(
                new InputOutputStrategyProduction(logger),
                new InputOutputMediaStrategyProduction(logger)
        );
    }

    /**
     * TODO: Configuration
     */
    public PhoneDriver phone(PhoneAccount account) throws Exception {
        SignallingConfiguration configuration = new SignallingConfigurationFactory()
                .asterisk(account.getUsername(), account.getPassword(), asteriskHost, asteriskPort);
        PhoneDriver driver = PhoneDriverFactory.build(configuration, strategies);
        return driver;
    }
}
