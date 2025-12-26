package com.example.demo.service.impl;

import com.example.demo.entity.Contract;
import com.example.demo.entity.DeliveryRecord;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.service.ContractService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;

    public ContractServiceImpl(ContractRepository contractRepository,
                               DeliveryRecordRepository deliveryRecordRepository) {
        this.contractRepository = contractRepository;
        this.deliveryRecordRepository = deliveryRecordRepository;
    }

    public ContractServiceImpl() {
        this.contractRepository = null;
        this.deliveryRecordRepository = null;
    }

    @Override
    public Contract createContract(Contract contract) {
        if (contract.getBaseContractValue() == null ||
            contract.getBaseContractValue().compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        if (contractRepository != null &&
            contractRepository.findByContractNumber(contract.getContractNumber()).isPresent()) {
            throw new BadRequestException("Contract already exists");
        }

        if (contract.getAgreedDeliveryDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Delivery date must be future");
        }

        if (contract.getStatus() == null) {
            contract.setStatus("ACTIVE");
        }

        return contractRepository.save(contract);
    }

    @Override
    public Contract updateContract(Long id, Contract updated) {
        Contract existing = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));

        if (updated.getBaseContractValue() != null &&
            updated.getBaseContractValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Base contract value");
        }

        existing.setTitle(updated.getTitle());
        existing.setCounterpartyName(updated.getCounterpartyName());
        existing.setAgreedDeliveryDate(updated.getAgreedDeliveryDate());
        existing.setBaseContractValue(updated.getBaseContractValue());

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

        Optional<DeliveryRecord> latest =
                deliveryRecordRepository.findFirstByContractIdOrderByDeliveryDateDesc(id);

        if (latest.isEmpty()) {
            contract.setStatus("ACTIVE");
        } else {
            DeliveryRecord record = latest.get();
            if (record.getDeliveryDate().isAfter(contract.getAgreedDeliveryDate())) {
                contract.setStatus("BREACHED");
            } else {
                contract.setStatus("COMPLETED");
            }
        }
        contractRepository.save(contract);
    }
}
