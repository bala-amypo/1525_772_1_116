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
        if (contract.getBaseContractValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Base contract value must be greater than zero");
        }

        contractRepository.findByContractNumber(contract.getContractNumber())
                .ifPresent(c -> {
                    throw new BadRequestException("Contract already exists");
                });

        contract.setStatus("ACTIVE");
        return contractRepository.save(contract);
    }

    @Override
    public Contract updateContract(Long id, Contract contract) {
        Contract existing = getContractById(id);

        existing.setTitle(contract.getTitle());
        existing.setCounterpartyName(contract.getCounterpartyName());
        existing.setAgreedDeliveryDate(contract.getAgreedDeliveryDate());
        existing.setBaseContractValue(contract.getBaseContractValue());

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

        deliveryRecordRepository.findFirstByContractIdOrderByDeliveryDateDesc(id)
                .ifPresentOrElse(record -> {
                    if (record.getDeliveryDate().isAfter(contract.getAgreedDeliveryDate())) {
                        contract.setStatus("BREACHED");
                    } else {
                        contract.setStatus("COMPLETED");
                    }
                }, () -> {
                    if (contract.getAgreedDeliveryDate().isBefore(LocalDate.now())) {
                        contract.setStatus("BREACHED");
                    } else {
                        contract.setStatus("ACTIVE");
                    }
                });

        contractRepository.save(contract);
    }
}
