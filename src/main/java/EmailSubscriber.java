import Utils.EmailCredentials;
import Utils.EmailSender;

public class EmailSubscriber {
    EmailSender emailSender = new EmailSender();
    EmailCredentials emailCredentials;

    public EmailSubscriber(EmailCredentials emailCredentials) {
        this.emailCredentials = emailCredentials;
    }

    public void notify(String htmlReport) {
        emailSender.sendHtmlEmail(emailCredentials, htmlReport);
    }
}
