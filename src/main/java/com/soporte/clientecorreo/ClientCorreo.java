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

import java.io.IOException;
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
            emailService.processUnreadMessages(message -> {
                try {
                    System.out.println("Email Subject: " + message.getSubject());

                    Object content = message.getContent();
                    if (content instanceof String) {
                        // Contenido simple de texto
                        System.out.println("Email Content: " + content);
                    } else if (content instanceof Multipart) {
                        // Contenido multiparte
                        Multipart multipart = (Multipart) content;
                        printMultipart(multipart);
                    } else {
                        System.out.println("Email Content: " + content.toString());
                    }

                    String senderEmail = Arrays.stream(message.getFrom())
                            .map(Address::toString)
                            .findFirst()
                            .map(this::extractEmail)
                            .orElse(null);

                    if (senderEmail != null) {
                        //registrar correo
                        EmpleadoDto empleadoDto = empleadoService.readByCorreo(senderEmail);
                        if(empleadoDto == null) {
                            System.out.println("No se encontró empleado con correo: " + senderEmail);
                            //Marca correo como cliente no registrado
                            return;
                        }
                        EmpresaDto empresaDto = empleadoDto.getEmpresa();
                        //SI empresaDto == null marcar como NO TIENE EMPRESA
                        if(empresaDto == null) {
                            System.out.println("No se encontró empresa para el empleado con correo: " + senderEmail);
                            //Marca correo como cliente sin empresa
                            return;
                        }
                        ProductoDto productoDto = new ProductoDto();
                        //si productoDto == null marcar como NO TIENE PRODUCTO
                        if (productoDto == null) {
                            System.out.println("No se encontró producto para la empresa: " + empresaDto.getRazonSocial());
                            //Marca correo como empresa sin producto
                            return;
                        }
                        productoDto.setId(1);

                        SoporteDto soporteDto = soporteService.readByEmpresaAndProducto(empresaDto, productoDto);
                        //si soporteDto == null marcar como NO TIENE SOPORTE
                        if(soporteDto == null) {
                            System.out.println("No se encontró soporte para la empresa: " + empresaDto.getRazonSocial());
                            //Marca correo como empresa sin soporte
                            return;
                        }


                        // Procesa el soporteDto según sea necesario
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private void printMultipart(Multipart multipart) throws MessagingException, IOException {
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                System.out.println("Text content: " + bodyPart.getContent());
            } else if (bodyPart.isMimeType("text/html")) {
                System.out.println("HTML content: " + bodyPart.getContent());
            } else if (bodyPart.getContent() instanceof Multipart) {
                printMultipart((Multipart) bodyPart.getContent());
            }
        }
    }
    private String extractEmail(String fullAddress) {
        int startIndex = fullAddress.lastIndexOf('<');
        int endIndex = fullAddress.lastIndexOf('>');
        if (startIndex >= 0 && endIndex > startIndex) {
            return fullAddress.substring(startIndex + 1, endIndex);
        } else {
            // Si no encuentra los caracteres < >, asume que toda la cadena es el correo
            return fullAddress.trim();
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
