package com.soporte.clientecorreo;



import com.soporte.dto.*;
import com.soporte.service.*;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class ClientCorreo {

    private final EmailService emailService;
    private final EmpleadoService empleadoService;
    private final SoporteService soporteService;
    private final ProductoService productoService;
    private final OpenAIService openAIService;
    private final TicketService ticketService;



    @Scheduled(fixedRate = 60000)
    public void getCorreoNoLeidos() {
        AtomicReference<String> respuesta = new AtomicReference<>("");

        try {
            emailService.processUnreadMessages(message -> {
                try {
                    System.out.println("Email Subject: " + message.getSubject());
                    String subject = message.getSubject();
                    Object content = message.getContent();
                    if (content instanceof String) {
                        System.out.println("Email Content: " + content);
                    } else if (content instanceof Multipart) {
                        Multipart multipart = (Multipart) content;
                        String multipartContent = convertMultipartToString(multipart);
                        content = multipartContent;
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

                        ProductoDto productoDto = productoService.findProductosByNombre(subject);
                        //si productoDto == null marcar como NO TIENE PRODUCTO
                        if (productoDto == null) {
                            System.out.println("No se encontro el producto");
                            return;
                        }

                        SoporteDto soporteDto = soporteService.readByEmpresaAndProducto(empresaDto, productoDto);
                        //si soporteDto == null marcar como NO TIENE SOPORTE

                        if(soporteDto == null) {
                            System.out.println("No se encontró soporte para la empresa: " + empresaDto.getRazonSocial());
                            //Marca correo como empresa sin soporte
                            return;
                        }
                        if(soporteDto.getEstado()){
                            TicketDto ticketDto =this.createTicket(empleadoDto,soporteDto,subject);
                            respuesta.set(openAIService.chatGptIa(content.toString()));
                            String correoRespuesta=respuesta.get();
                            System.out.println(correoRespuesta);
                            emailService.sendEmail(senderEmail,subject,correoRespuesta);

                        }
                       /* if (!respuesta.get().isEmpty()){
                            sendSimpleMessage(senderEmail,"Soporte Tesabiz",respuesta.get());
                        }*/

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

    /*
    public void sendEmail(String to, String subject, String content) {
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
*/
   /* public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("luisbryancuevaparada@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
*/
    private String convertMultipartToString(Multipart multipart) throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        int count = multipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent().toString());
            } else if (bodyPart.isMimeType("text/html")) {
                result.append(bodyPart.getContent().toString());
            } else if (bodyPart.getContent() instanceof Multipart) {
                result.append(convertMultipartToString((Multipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }

    private TicketDto createTicket(EmpleadoDto empleadoDto,SoporteDto soporteDto,String asunto){
        try{
            TicketDto ticketDto=new TicketDto();
            ticketDto.setAsunto(asunto);
            ticketDto.setDescripcion("descripcion");
            ticketDto.setEstado("ABIERTO");
            ticketDto.setSolicitante(empleadoDto);
            AgenteDto agenteDto=new AgenteDto();
            agenteDto.setId(1);
            ticketDto.setResponsable(agenteDto);
            ticketDto.setSoporte(soporteDto);
            ticketDto=ticketService.create("",ticketDto);
            return ticketDto;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }
}
