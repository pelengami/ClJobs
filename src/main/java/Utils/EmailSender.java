package Utils;

import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;
import org.simplejavamail.mailer.config.TransportStrategy;

import javax.mail.Message;

public class EmailSender {

    public void sendHtmlEmail(EmailCredentials emailCredentials, String textHtml) {
        Email email = new Email();
        email.setFromAddress("ClJobs", "ClJobs@do-not-reply.com");
        email.setReplyToAddress("ClJobs", "ClJobs@do-not-reply.com");
        email.addRecipient(emailCredentials.Recipient, emailCredentials.Recipient, Message.RecipientType.TO);
        email.setSubject("Cl Jobs");
        email.setTextHTML(textHtml);

        Mailer mailer = new Mailer(new ServerConfig(emailCredentials.Host, 587, emailCredentials.Login, emailCredentials.Password),
                TransportStrategy.SMTP_TLS
        );

        mailer.sendMail(email);
    }

}

