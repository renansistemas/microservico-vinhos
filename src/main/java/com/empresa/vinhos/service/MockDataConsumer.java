package com.empresa.vinhos.service;

import com.empresa.vinhos.controller.exception.ConsumeMockException;
import com.empresa.vinhos.model.entity.Cliente;
import com.empresa.vinhos.model.entity.Produto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Component
public class MockDataConsumer {

    Logger logger = Logger.getLogger(MockDataConsumer.class.getName());

    @Autowired
    private ObjectMapper objectMapper;

    private List<Produto> produtos;
    private List<Cliente> clientes;

    @PostConstruct
    public void consumirDadosMock() {
        try {

            ClassPathResource produtosResource = new ClassPathResource("data/produtos.json");
            produtos = objectMapper.readValue(produtosResource.getInputStream(),
                    new TypeReference<>() {
                    });

            ClassPathResource clientesResource = new ClassPathResource("data/clientes.json");
            clientes = objectMapper.readValue(clientesResource.getInputStream(),
                    new TypeReference<>() {
                    });

            logger.info("Dados mock consumidos com sucesso!");

        } catch (IOException e) {
            throw new ConsumeMockException();
        }
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
