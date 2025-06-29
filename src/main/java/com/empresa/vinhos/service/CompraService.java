package com.empresa.vinhos.service;

import com.empresa.vinhos.model.dto.CompraResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private MockDataConsumer mockDataConsumer;

    public Page<CompraResponseDTO> listarCompras(Pageable pageable) {
        var todasCompras = construirListaCompras();

        // Ordenar por valor total crescente
        var comprasOrdenadas = todasCompras
                .stream()
                .sorted(Comparator.comparing(CompraResponseDTO::valorTotal))
                .toList();

        // Paginar com skip e limit
        var comprasPaginadas = comprasOrdenadas.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();

        return new PageImpl<>(comprasPaginadas, pageable, todasCompras.size());
    }

    public Optional<CompraResponseDTO> getMaiorCompraPorAno(int ano) {
        return construirListaCompras().stream()
                .filter(compra -> compra.anoCompra().equals(ano))
                .max(Comparator.comparing(CompraResponseDTO::valorTotal));
    }

    private List<CompraResponseDTO> construirListaCompras() {
        var produtos = mockDataConsumer.getProdutos();
        var clientes = mockDataConsumer.getClientes();

        return clientes.stream()
                .flatMap(cliente -> cliente.compras().stream()
                        .map(compra -> produtos.stream()
                                .filter(p -> p.codigo().equals(Integer.valueOf(compra.codigo())))
                                .findFirst()
                                .map(produto -> {
                                    BigDecimal valorTotal = produto.preco()
                                            .multiply(BigDecimal.valueOf(compra.quantidade()));

                                    return new CompraResponseDTO(
                                            cliente.nome(),
                                            cliente.cpf(),
                                            produto.codigo(),
                                            produto.tipoVinho(),
                                            produto.preco(),
                                            produto.safra(),
                                            produto.anoCompra(),
                                            compra.quantidade(),
                                            valorTotal
                                    );
                                })
                        )
                )
                .flatMap(Optional::stream) // descarta os Optionals vazios automaticamente
                .toList();
    }


}
