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

    @PostMapping
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
        return new ResponseEntity<>(contractService.createContract(contract), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> updateContract(@PathVariable Long id,
                                                    @RequestBody Contract contract) {
        return ResponseEntity.ok(contractService.updateContract(id, contract));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getContractById(id));
    }

    @GetMapping
    public ResponseEntity<List<Contract>> getAllContracts() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        contractService.updateContractStatus(id);
        return ResponseEntity.ok().build();
    }
}


/* ===================== REPOSITORY ===================== */

package com.example.demo.repository;

import com.example.demo.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByContractNumber(String contractNumber);
}


/* ===================== SERVICE ===================== */

package com.example.demo.service;

import com.example.demo.entity.Contract;

import java.util.List;

public interface ContractService {

    Contract createContract(Contract contract);

    Contract updateContract(Long id, Contract contract);

    Contract getContractById(Long id);

    List<Contract> getAllContracts();

    void updateContractStatus(Long id);
}


/* ===================== SERVICE IMPLEMENTATION ===================== */

package com.example.demo.service.impl;

import com.example.demo.entity.Contract;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.service.ContractService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;

    public ContractServiceImpl(ContractRepository contractRepository,
                               DeliveryRecordRepository deliveryRecordRepository) {
        this.contractRepository = contractRepository;
        this.deliveryRecordRepository = deliveryRecordRepository;
    }

    @Override
    public Contract createContract(Contract contract) {

        if (contract.getBaseContractValue() == null ||
                contract.getBaseContractValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Base contract value must be greater than zero");
        }

        if (contract.getAgreedDeliveryDate() == null ||
                contract.getAgreedDeliveryDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Agreed delivery date cannot be past");
        }

        if (contractRepository.findByContractNumber(contract.getContractNumber()).isPresent()) {
            throw new BadRequestException("Contract number already exists");
        }

        return contractRepository.save(contract);
    }

    @Override
    public Contract updateContract(Long id, Contract updated) {

        Contract existing = getContractById(id);

        existing.setTitle(updated.getTitle());
        existing.setCounterpartyName(updated.getCounterpartyName());
        existing.setAgreedDeliveryDate(updated.getAgreedDeliveryDate());
        existing.setBaseContractValue(updated.getBaseContractValue());
        existing.setStatus(updated.getStatus());

        return contractRepository.save(existing);
    }

    @Override
    public Contract getContractById(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public void updateContractStatus(Long id) {
        Contract contract = getContractById(id);

        boolean deliveredLate = deliveryRecordRepository
                .findByContractId(id)
                .stream()
                .anyMatch(d -> d.getDeliveryDate().isAfter(contract.getAgreedDeliveryDate()));

        if (deliveredLate) {
            contract.setStatus("BREACHED");
        } else {
            contract.setStatus("COMPLETED");
        }

        contractRepository.save(contract);
    }
}
