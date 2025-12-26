package com.example.demo.service;

import com.example.demo.entity.Contract;

import java.util.List;
import java.util.Optional;

public interface ContractService {

    Contract createContract(Contract contract);

    Contract updateContract(Long id, Contract contract);

    Optional<Contract> getContractById(Long id);

    List<Contract> getAllContracts();

    Contract updateContractStatus(Long id);
}
