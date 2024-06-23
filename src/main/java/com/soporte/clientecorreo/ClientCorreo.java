package com.soporte.clientecorreo;



import com.soporte.dto.EmpleadoDto;
import com.soporte.dto.EmpresaDto;
import com.soporte.dto.ProductoDto;
import com.soporte.dto.SoporteDto;
import com.soporte.service.EmpleadoService;
import com.soporte.service.SoporteService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class ClientCorreo {

    private final EmailService emailService;
    private final EmpleadoService empleadoService;
    private final SoporteService soporteService;


    @Scheduled(fixedRate = 60000)
    public void getCorreoNoLeidos() {
        try {
            Message[] messages = emailService.getUnreadMessages();
            Arrays.stream(messages).forEach(message -> {
                try {
                    System.out.println("Email Subject: " + message.getSubject());

                    EmpleadoDto empleadoDto =empleadoService.readByCorreo(Arrays.stream(message.getFrom()).toList().get(0).toString());
                    EmpresaDto empresaDto =empleadoDto.getEmpresa();
                    ProductoDto productoDto=new ProductoDto();
                    productoDto.setId(1);
                    SoporteDto soporteDto =soporteService.readByEmpresaAndProducto(empresaDto, productoDto);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(String to, String subject, String content) {
        final String username = "your-email@gmail.com";
        final String password = "your-password";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
