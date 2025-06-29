package com.empresa.vinhos.service;

import com.empresa.vinhos.model.dto.ProdutoRecomendadoDTO;
import com.empresa.vinhos.model.dto.RecomendacaoResponseDTO;
import com.empresa.vinhos.model.entity.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RecomendacaoService {

    @Autowired
    private MockDataConsumer mockDataConsumer;

    public RecomendacaoResponseDTO getRecomendacaoDeVinho(String cpf) {
        var clientes = mockDataConsumer.getClientes();
        var produtos = mockDataConsumer.getProdutos();

        return clientes.stream()
                .filter(c -> c.cpf().equals(cpf))
                .findFirst()
                .map(cliente -> {
                    // Mapeia os tipos de vinho com a quantidade total comprada
                    Map<String, Integer> tiposVinhoComprados = cliente.compras().stream()
                            .map(compra -> produtos.stream()
                                    .filter(p -> p.codigo().equals(Integer.valueOf(compra.codigo())))
                                    .findFirst()
                                    .map(p -> Map.entry(p.tipoVinho(), compra.quantidade()))
                                    .orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    Integer::sum
                            ));

                    String tipoVinhoPreferido = tiposVinhoComprados.entrySet().stream()
                            .max(Map.Entry.comparingByValue())
                            .map(Map.Entry::getKey)
                            .orElse(null);

                    if (tipoVinhoPreferido == null) {
                        return null;
                    }

                    Produto produtoRecomendado = produtos.stream()
                            .filter(p -> p.tipoVinho().equals(tipoVinhoPreferido))
                            .findFirst()
                            .orElse(null);

                    if (produtoRecomendado == null) {
                        return null;
                    }

                    ProdutoRecomendadoDTO produtoDTO = new ProdutoRecomendadoDTO(
                            produtoRecomendado.codigo(),
                            produtoRecomendado.tipoVinho(),
                            produtoRecomendado.preco(),
                            produtoRecomendado.safra(),
                            produtoRecomendado.anoCompra()
                    );

                    return new RecomendacaoResponseDTO(
                            cpf,
                            tipoVinhoPreferido,
                            produtoDTO
                    );
                })
                .orElse(null);
    }


}
