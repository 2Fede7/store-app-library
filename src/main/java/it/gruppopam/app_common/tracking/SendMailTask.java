package it.gruppopam.app_common.tracking;

import android.os.AsyncTask;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import it.gruppopam.app_common.handler.MailResponseHandler;
import it.gruppopam.app_common.utils.ExceptionLogger;

public class SendMailTask extends AsyncTask<Void, Void, Boolean> {

    private Session session;
    private String subject;
    private String fromAddress;
    private String toAddress;
    private File attachment;
    private MailResponseHandler responseHandler;
    private String body;
    private int mail_sent_successfully;
    private int mail_sending_failed;

    public SendMailTask(Session session, String subject, String fromAddress, String toAddress, String body,
                        File attachment, MailResponseHandler responseHandler, int mail_sent_successfully, int mail_sending_failed) {
        this.session = session;
        this.subject = subject;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.attachment = attachment;
        this.body = body;
        this.responseHandler = responseHandler;
        this.mail_sent_successfully = mail_sent_successfully;
        this.mail_sending_failed = mail_sending_failed;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean isSuccessful = true;
        try {
            MimeMessage message = new MimeMessage(session);
            InternetAddress[] to = new InternetAddress[1];
            to[0] = new InternetAddress(toAddress);
            MimeMultipart multipart = new MimeMultipart();

            multipart.addBodyPart(getTextAsBodyPart(body));
            if (this.attachment != null) {
                multipart.addBodyPart(getAttachmentAsBodyPart());
            }

            createMailMessage(message, to, multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            isSuccessful = false;
            ExceptionLogger.logError(BaseMailClient.class.getCanonicalName(), e.getMessage(), e);
        }
        return isSuccessful;
    }

    @Override
    protected void onPostExecute(Boolean isSuccessful) {
        super.onPostExecute(isSuccessful);
        responseHandler.handleMailResponse(isSuccessful, mail_sent_successfully, mail_sending_failed);
    }

    private void createMailMessage(MimeMessage message, InternetAddress[] to, MimeMultipart multipart) throws MessagingException {
        message.setSubject(subject);
        message.setFrom(new InternetAddress(fromAddress));
        message.setRecipients(Message.RecipientType.TO, to);
        message.setContent(multipart);
    }

    private MimeBodyPart getAttachmentAsBodyPart() throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        FileDataSource source = new FileDataSource(this.attachment);
        bodyPart.setDataHandler(new DataHandler(source));
        bodyPart.setFileName(this.attachment.getName());
        return bodyPart;
    }

    private MimeBodyPart getTextAsBodyPart(String text) throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(text);
        return bodyPart;
    }
}
