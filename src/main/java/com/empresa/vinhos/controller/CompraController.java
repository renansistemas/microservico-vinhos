package com.empresa.vinhos.controller;

import com.empresa.vinhos.model.dto.CompraResponseDTO;
import com.empresa.vinhos.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping("/compras")
    public ResponseEntity<Page<CompraResponseDTO>> listarCompras(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "valorTotal") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        try {
            Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
            Pageable pageable = PageRequest.of(page, size, sortDirection, sort);
            Page<CompraResponseDTO> compras = compraService.listarCompras(pageable);
            return ResponseEntity.ok(compras);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/maior-compra/{ano}")
    public ResponseEntity<CompraResponseDTO> getMaiorCompraPorAno(@PathVariable int ano) {
        try {
            return compraService.getMaiorCompraPorAno(ano)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
