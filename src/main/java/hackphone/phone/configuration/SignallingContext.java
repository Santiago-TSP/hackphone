package hackphone.phone.configuration;

import hackphone.phone.inviteing.InviteingContext;

import java.net.InetSocketAddress;

public class SignallingContext {

    final SignallingConfiguration configuration;
    final SignallingLazyConfiguration lazyConfiguration;

    public SignallingContext(SignallingConfiguration configuration, SignallingLazyConfiguration lazyConfiguration) {
        this.configuration = configuration;
        this.lazyConfiguration = lazyConfiguration;
    }

    public String myIp() {
        return configuration.me.myIp;
    }

    public int myPort() {
        return lazyConfiguration.myPort();
    }

    public String accountName() {
        return configuration.account.username;
    }

    public String accoungPassword() {
        return configuration.account.password;
    }

    public String accountDisplayName() {
        return configuration.account.displayName;
    }

    public String asteriskHost() {
        return configuration.account.asterisk.getHostString();
    }

    public InetSocketAddress asterisk() {
        return configuration.account.asterisk;
    }


    // Trafficable

    public String trafficable_realm() {
        return lazyConfiguration.getTrafficContext().getRealm();
    }

    public String trafficable_nonce() {
        return lazyConfiguration.getTrafficContext().getNonce();
    }

    public void trafficable_realm(String realm) {
        lazyConfiguration.getTrafficContext().setRealm(realm);
    }

    public void trafficable_nonce(String nonce) {
        lazyConfiguration.getTrafficContext().setNonce(nonce);
    }

    public int trafficable_myRtpPort() {
        return lazyConfiguration.getTrafficContext().getMyRtpPort();
    }

    // TODO: kto to ustawia?
    public void trafficable_myRtpPort(int myRtpPort) {
        lazyConfiguration.getTrafficContext().setMyRtpPort(myRtpPort);
    }

    public SessionLogger logger() {
        return configuration.logger;
    }

    public String callIdForRegistering() {
        return lazyConfiguration.getCallIdForRegistering();
    }

    public String callIdForInviteing() {
        return lazyConfiguration.getCallIdForInviteing();
    }

    // =========================

    public InviteingContext getInviteingContext() {
        return inviteingContext;
    }

    private final InviteingContext inviteingContext = new InviteingContext();

    // =========================

}
