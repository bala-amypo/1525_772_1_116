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
            throw new BadRequestException("Base contract value invalid");
        }
        if (contract.getAgreedDeliveryDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("future date required");
        }
        if (contractRepository.findByContractNumber(contract.getContractNumber()).isPresent()) {
            throw new BadRequestException("Contract already exists");
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
        boolean late = deliveryRecordRepository.findByContractId(id)
                .stream()
                .anyMatch(d -> d.getDeliveryDate().isAfter(contract.getAgreedDeliveryDate()));
        contract.setStatus(late ? "BREACHED" : "COMPLETED");
        contractRepository.save(contract);
    }
}
