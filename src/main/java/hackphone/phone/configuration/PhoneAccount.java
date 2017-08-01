package hackphone.phone.configuration;

public class PhoneAccount {

    final String username;
    final String password;

    public PhoneAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
