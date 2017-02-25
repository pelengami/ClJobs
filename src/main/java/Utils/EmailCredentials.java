package Utils;

public class EmailCredentials {
    public String Login;
    public String Password;
    public String Host;
    public String Recipient;

    public EmailCredentials(String login, String password, String host, String recipient) {
        this.Login = login;
        this.Password = password;
        this.Host = host;
        this.Recipient = recipient;
    }
}

