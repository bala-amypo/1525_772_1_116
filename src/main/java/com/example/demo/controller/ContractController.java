package com.example.demo.controller;

import com.example.demo.entity.Contract;
import com.example.demo.service.ContractService;
import org.springframework.http.HttpStatus;
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

    // Create Contract
    @PostMapping
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
        return new ResponseEntity<>(
                contractService.createContract(contract),
                HttpStatus.CREATED
        );
    }

    // Update Contract
    @PutMapping("/{id}")
    public ResponseEntity<Contract> updateContract(
            @PathVariable Long id,
            @RequestBody Contract contract) {

        return ResponseEntity.ok(
                contractService.updateContract(id, contract)
        );
    }

    // Get Contract by ID
    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable Long id) {
        return ResponseEntity.ok(
                contractService.getContractById(id)
        );
    }

    // Get All Contracts
    @GetMapping
    public ResponseEntity<List<Contract>> getAllContracts() {
        return ResponseEntity.ok(
                contractService.getAllContracts()
        );
    }

    // Update Contract Status
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateContractStatus(@PathVariable Long id) {
        contractService.updateContractStatus(id);
        return ResponseEntity.ok().build();
    }
}
