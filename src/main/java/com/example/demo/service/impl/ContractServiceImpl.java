package com.example.demo.service.impl;

import com.example.demo.entity.Contract;
import com.example.demo.entity.DeliveryRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.service.ContractService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    private ContractRepository contractRepository;
    private DeliveryRecordRepository deliveryRecordRepository;

    // REQUIRED for TestNG
    public ContractServiceImpl() {
    }

    // Used by Spring
    public ContractServiceImpl(ContractRepository contractRepository,
                               DeliveryRecordRepository deliveryRecordRepository) {
        this.contractRepository = contractRepository;
        this.deliveryRecordRepository = deliveryRecordRepository;
    }

    @Override
    public Contract createContract(Contract contract) {
        if (contract.getBaseContractValue() == null ||
                contract.getBaseContractValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Base contract value must be greater than zero");
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
    public Contract updateContractStatus(Long id) {
        Contract contract = getContractById(id);

        Optional<DeliveryRecord> latest =
                deliveryRecordRepository.findFirstByContractIdOrderByDeliveryDateDesc(id);

        if (latest.isPresent()) {
            if (latest.get().getDeliveryDate()
                    .isAfter(contract.getAgreedDeliveryDate())) {
                contract.setStatus("BREACHED");
            } else {
                contract.setStatus("ACTIVE");
            }
        } else {
            contract.setStatus("ACTIVE");
        }

        return contractRepository.save(contract);
    }
}
