package com.empresa.vinhos.service;

import com.empresa.vinhos.model.dto.ClienteFielResponseDTO;
import com.empresa.vinhos.model.entity.Cliente;
import com.empresa.vinhos.model.entity.CompraItem;
import com.empresa.vinhos.model.entity.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private MockDataConsumer mockDataConsumer;

    public Page<ClienteFielResponseDTO> getClientesFieis(Pageable pageable, int limit) {
        List<ClienteFielResponseDTO> clientesFieis = construirListaClientesFieis();

        List<ClienteFielResponseDTO> topClientes = clientesFieis.stream()
                .sorted(Comparator
                        .comparing(ClienteFielResponseDTO::valorTotalGasto, Comparator.reverseOrder())
                        .thenComparing(ClienteFielResponseDTO::totalCompras, Comparator.reverseOrder()))
                .limit(limit)
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new PageImpl<>(topClientes, pageable, Math.min(limit, clientesFieis.size()));
    }

    private List<ClienteFielResponseDTO> construirListaClientesFieis() {
        List<Cliente> clientes = mockDataConsumer.getClientes();
        List<Produto> produtos = mockDataConsumer.getProdutos();

        return clientes.stream()
                .map(cliente -> {
                    BigDecimal valorTotalGasto = cliente.compras().stream()
                            .map(compra -> produtos.stream()
                                    .filter(p -> p.codigo().equals(Integer.valueOf(compra.codigo())))
                                    .findFirst()
                                    .map(produto -> produto.preco()
                                            .multiply(BigDecimal.valueOf(compra.quantidade())))
                                    .orElse(BigDecimal.ZERO)
                            )
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    int totalCompras = cliente.compras().stream()
                            .mapToInt(CompraItem::quantidade)
                            .sum();

                    return new ClienteFielResponseDTO(
                            cliente.nome(),
                            cliente.cpf(),
                            totalCompras,
                            valorTotalGasto
                    );
                }).toList();
    }


}
