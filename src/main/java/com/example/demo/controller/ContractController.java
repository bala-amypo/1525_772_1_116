package com.example.demo.controller;

import com.example.demo.entity.Contract;
import com.example.demo.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<Contract> create(@RequestBody Contract contract) {
        return ResponseEntity.ok(contractService.createContract(contract));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> update(
            @PathVariable Long id,
            @RequestBody Contract contract) {
        return ResponseEntity.ok(contractService.updateContract(id, contract));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getContractById(id));
    }

    @GetMapping
    public ResponseEntity<List<Contract>> getAll() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        contractService.updateContractStatus(id);
        return ResponseEntity.ok().build();
    }
}
