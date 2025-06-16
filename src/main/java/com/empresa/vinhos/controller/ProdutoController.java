package com.empresa.vinhos.controller;

import com.empresa.vinhos.model.dto.RecomendacaoResponseDTO;
import com.empresa.vinhos.service.RecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProdutoController {

    @Autowired
    private RecomendacaoService recomendacaoService;

    @GetMapping("/recomendacao/{cpf}/tipo")
    public ResponseEntity<RecomendacaoResponseDTO> getRecomendacaoDeVinho(@PathVariable String cpf) {
        try {
            RecomendacaoResponseDTO recomendacaoDeVinho = recomendacaoService.getRecomendacaoDeVinho(cpf);

            if (recomendacaoDeVinho != null) {
                return ResponseEntity.ok(recomendacaoDeVinho);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
