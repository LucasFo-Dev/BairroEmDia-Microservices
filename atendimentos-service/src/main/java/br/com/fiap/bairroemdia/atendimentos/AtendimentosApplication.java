package br.com.fiap.bairroemdia.atendimentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@org.springframework.cloud.openfeign.EnableFeignClients
public class AtendimentosApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtendimentosApplication.class, args);
    }
}
