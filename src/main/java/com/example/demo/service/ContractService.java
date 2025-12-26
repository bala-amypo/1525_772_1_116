package com.example.demo.service;

import com.example.demo.entity.Contract;
import com.example.demo.repository.ContractRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ContractService {

    private ContractRepository contractRepository;

    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Optional<Contract> getContractById(Long id) {
        return contractRepository.findById(id);
    }
}
