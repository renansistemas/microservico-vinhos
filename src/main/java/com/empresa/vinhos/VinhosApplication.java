package com.empresa.vinhos;

import com.empresa.vinhos.controller.exception.InitializeApplicationException;
import com.empresa.vinhos.service.MockDataConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.logging.Logger;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class VinhosApplication implements CommandLineRunner {

    @Autowired
    private MockDataConsumer mockDataConsumer;


    public static void main(String[] args) {
        SpringApplication.run(VinhosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Logger logger = Logger.getLogger(VinhosApplication.class.getName());
        try {
            validaCarregamentoDados(logger);
            logger.info("Aplicação iniciada com sucesso!");
        } catch (Exception e) {
            throw new InitializeApplicationException(e.getMessage());
        }
    }

    private void validaCarregamentoDados(Logger logger) {

        int totalProdutos = mockDataConsumer.getProdutos() != null ? mockDataConsumer.getProdutos().size() : 0;
        int totalClientes = mockDataConsumer.getClientes() != null ? mockDataConsumer.getClientes().size() : 0;
        logger.info("Qtde de Produtos carregados: " + totalProdutos);
        logger.info("Qtde de Clientes carregados: " + totalClientes);

        if (totalProdutos > 0 && totalClientes > 0) {
            logger.info("Dados carregados com sucesso!");
        } else {
            logger.warning("Alerta: Alguns dados podem não ter sido carregados corretamente");
        }

    }
}