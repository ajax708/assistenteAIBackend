package com.soporte.clientecorreo;


import jakarta.annotation.PostConstruct;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.search.FlagTerm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class EmailService {

    @Value("${spring.mail.imap.host}")
    private String hostImap;

    @Value("${spring.mail.imap.port}")
    private String portImap;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String hostSmtp;

    @Value("${spring.mail.port}")
    private String portSmtp;


    @Value("${spring.mail.smtp.auth}")
    private String smptAuth;
    @Value("${spring.mail.smtp.starttls.enable}")
    private String starttls;
    @Value("${spring.mail.imap.ssl.enable}")
    private String ssl;

    private Store store;

    @PostConstruct
    public void init() {
        try {
            connectToStore();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void connectToStore() throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.imap.host", hostImap);
        properties.put("mail.imap.port", portImap);
        properties.put("mail.imap.ssl.enable", ssl);
        properties.put("mail.store.protocol", "imap");

        Session emailSession = Session.getDefaultInstance(properties);
        store = emailSession.getStore("imap");
        store.connect(username, password);
    }

    private void ensureConnected() throws MessagingException {
        if (store == null || !store.isConnected()) {
            connectToStore();
        }
    }

    public void processUnreadMessages(MessageProcessor processor) throws MessagingException {
        ensureConnected();
        Folder inbox = null;
        try {
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE); // Cambiado a READ_WRITE para marcar como leídos si es necesario
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            for (Message message : messages) {
                processor.process(message);
                message.setFlag(Flags.Flag.SEEN, true); // Marcar como leído después de procesar
            }
        } finally {
            if (inbox != null && inbox.isOpen()) {
                inbox.close(false);
            }
        }
    }

    public interface MessageProcessor {
        void process(Message message) throws MessagingException;
    }

    public void sendEmail(String to, String subject, String content) {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", hostSmtp);
        prop.put("mail.smtp.port", portSmtp);
        prop.put("mail.smtp.auth", smptAuth);
        prop.put("mail.smtp.starttls.enable", starttls);

        Session session = Session.getInstance(prop, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
