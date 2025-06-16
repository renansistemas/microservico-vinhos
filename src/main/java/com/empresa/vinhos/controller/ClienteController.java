package com.empresa.vinhos.controller;

import com.empresa.vinhos.model.dto.ClienteFielResponseDTO;
import com.empresa.vinhos.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes-fieis")
    public ResponseEntity<Page<ClienteFielResponseDTO>> getClientesFieis(
            @RequestParam(defaultValue = "3") int limit,
            @RequestParam(defaultValue = "0") int page) {

        try {
            Sort sortDirection = Sort.by(Sort.Direction.DESC, "valorTotalGasto");
            Pageable pageable = PageRequest.of(page, limit, sortDirection);
            Page<ClienteFielResponseDTO> clientesFieis = clienteService.getClientesFieis(pageable, limit);
            return ResponseEntity.ok(clientesFieis);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
