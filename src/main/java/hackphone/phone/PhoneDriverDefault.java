package hackphone.phone;

import hackphone.phone.configuration.SignallingConfiguration;
import hackphone.phone.configuration.SignallingContext;
import hackphone.phone.configuration.SignallingLazyConfiguration;
import hackphone.phone.detectors.DetectedMessage;
import hackphone.phone.detectors.DetectorMessage;
import hackphone.phone.io.SignallingReceiverDefault;
import hackphone.phone.io.SignallingSender;
import hackphone.phone.registering.RegisteringEvents;
import hackphone.phone.registering.RegisteringStateMachine;
import hackphone.phone.registering.RegisteringStateMachineFactory;

import java.io.IOException;

class PhoneDriverDefault implements PhoneDriver {

    final DetectorMessage detector = new DetectorMessage();
    final SignallingSender sender;
    final SignallingReceiverDefault receiver;
    final SignallingContext context;
    final ConfiguredStrategies strategies;


    final RegisteringStateMachineFactory registeringStateMachineFactory;

    RegisteringStateMachine stateMachine;


    PhoneDriverDefault(SignallingConfiguration configuration, ConfiguredStrategies strategies) throws IOException {
        this.strategies = strategies;

        final int sipPort = strategies.evenUdpFinderStrategy().findEvenUdpPort();
        SignallingLazyConfiguration lazyConfiguration = new SignallingLazyConfiguration(sipPort);
        this.context = new SignallingContext(configuration, lazyConfiguration);
        this.receiver = new SignallingReceiverDefault(configuration.logger);
        this.sender = strategies.inputOutputStrategy().connect(receiver, context);
        this.registeringStateMachineFactory = new RegisteringStateMachineFactory(sender, context);
    }

    public void register(RegisteringEvents listener) {

        receiver.listenToIncominigPackets((stringPacket) -> {
            DetectedMessage detected = detector.detect(stringPacket);
            if(detected.isResponse()) {
                getStateMachine().incomingSignal(detected.responseDetails());
            }
            if(detected.isRequest()) {
                getStateMachine().incomingSignal(detected.requestDetails());
            }
        });

        setStateMachine(registeringStateMachineFactory.create(listener));
        getStateMachine().enter();
    }

    @Override
    public void signallingSend(String signallingPacket) {
        this.sender.accept(signallingPacket);
    }

    public synchronized RegisteringStateMachine getStateMachine() {
        return stateMachine;
    }

    public synchronized void setStateMachine(RegisteringStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }
}
