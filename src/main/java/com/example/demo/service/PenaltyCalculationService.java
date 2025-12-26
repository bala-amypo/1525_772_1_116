package com.example.demo.service;

import java.math.BigDecimal;

public interface PenaltyCalculationService {

    BigDecimal calculatePenalty(Long contractId);
}
