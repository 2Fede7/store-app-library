package it.gruppopam.app_common.tracking;

import java.util.Properties;

import javax.mail.Session;

public abstract class BaseMailClient {
    private static final String PROTOCOL = "smtp";
    private static final String SMTP_PORT = "25";
    protected final Session session;

    public BaseMailClient(String mailServer) {
        this.session = createSession(mailServer);
    }

    private Session createSession(String mailServer) {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", PROTOCOL);
        properties.setProperty("mail.host", mailServer);
        properties.setProperty("mail.smtp.auth", "false");
        properties.setProperty("mail.smtp.port", SMTP_PORT);
        return Session.getInstance(properties);
    }

}
