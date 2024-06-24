package com.soporte.clientecorreo;

import jakarta.mail.*;
import jakarta.mail.search.FlagTerm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailReceiverService {
    @Value("${spring.mail.imap.host}")
    private String imapHost;

    @Value("${spring.mail.imap.port}")
    private int imapPort;

    @Value("${spring.mail.imap.username}")
    private String username;

    @Value("${spring.mail.imap.password}")
    private String password;

    public void recibirCorreos() {
        try {
            System.out.println("recibir correo:  iniciando la lectura de correo no leido");
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imap.host", imapHost);
            properties.put("mail.imap.port", imapPort);
            properties.put("mail.imap.ssl.enable", "true");

            Session session = Session.getDefaultInstance(properties, null);
            Store store = session.getStore("imaps");
            store.connect(imapHost, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] mensajes = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (Message mensaje : mensajes) {
                System.out.println("Asunto: " + mensaje.getSubject());
                System.out.println("De: " + mensaje.getFrom()[0]);
                System.out.println("Contenido: " + mensaje.getContent().toString());
                mensaje.setFlag(jakarta.mail.Flags.Flag.SEEN, true);
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
