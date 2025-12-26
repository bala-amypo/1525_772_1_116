@Override
public Contract updateContractStatus(Long id) {
    Contract contract = getContractById(id);

    deliveryRecordRepository
            .findFirstByContractIdOrderByDeliveryDateDesc(id)
            .ifPresentOrElse(record -> {
                if (record.getDeliveryDate().isAfter(contract.getAgreedDeliveryDate())) {
                    contract.setStatus("BREACHED");
                } else {
                    contract.setStatus("ACTIVE");
                }
            }, () -> contract.setStatus("ACTIVE"));

    return contractRepository.save(contract);
}
