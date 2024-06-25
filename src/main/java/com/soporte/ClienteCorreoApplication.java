package com.soporte;

import com.soporte.clientecorreo.ClientCorreo;
import com.soporte.dto.AgenteDto;
import com.soporte.dto.EmpleadoDto;
import com.soporte.dto.SoporteDto;
import com.soporte.dto.TicketDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ClienteCorreoApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClienteCorreoApplication.class, args);
        /*ApplicationContext context = SpringApplication.run(ClientCorreo.class, args);
        ClientCorreo clienteCorreo = context.getBean(ClientCorreo.class);
        EmpleadoDto e=new EmpleadoDto();
        e.setId(1);
        SoporteDto s=new SoporteDto();
        s.setId(1);
        AgenteDto a=new AgenteDto();
        a.setId(1);
        String asunto="test1";
        TicketDto t=clienteCorreo.createTicket(e,s,asunto);*/
    }

}
