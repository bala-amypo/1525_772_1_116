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
            throw new BadRequestException("Agreed delivery date must be in the future");
        }

        contractRepository.findByContractNumber(contract.getContractNumber())
                .ifPresent(c -> {
                    throw new BadRequestException("Contract number already exists");
                });

        return contractRepository.save(contract);
    }

    @Override
    public Contract updateContract(Long id, Contract updatedContract) {

        Contract existing = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));

        if (updatedContract.getBaseContractValue() != null &&
                updatedContract.getBaseContractValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Base contract value must be greater than zero");
        }

        existing.setTitle(updatedContract.getTitle());
        existing.setCounterpartyName(updatedContract.getCounterpartyName());
        existing.setAgreedDeliveryDate(updatedContract.getAgreedDeliveryDate());
        existing.setBaseContractValue(updatedContract.getBaseContractValue());

        return contractRepository.save(existing);
    }

    @Override
    public Contract getContractById(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));
    }

    @Override
    public java.util.List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public void updateContractStatus(Long id) {

        Contract contract = getContractById(id);

        deliveryRecordRepository.findFirstByContractIdOrderByDeliveryDateDesc(id)
                .ifPresentOrElse(record -> {
                    if (record.getDeliveryDate()
                            .isAfter(contract.getAgreedDeliveryDate())) {
                        contract.setStatus("BREACHED");
                    } else {
                        contract.setStatus("COMPLETED");
                    }
                }, () -> contract.setStatus("ACTIVE"));

        contractRepository.save(contract);
    }
}
