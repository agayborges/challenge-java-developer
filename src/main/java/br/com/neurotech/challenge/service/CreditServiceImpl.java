package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.dto.CreditResponseDto;
import br.com.neurotech.challenge.dto.CreditStatus;
import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.FeeType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.ClientRepository;
import br.com.neurotech.challenge.repository.CreditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CreditServiceImpl implements CreditService {

    private static final long FIX_FEE_VALUE = 5;
    private final ClientRepository clientRepository;

    private final CreditRepository creditRepository;

    public CreditServiceImpl(ClientRepository clientRepository, CreditRepository creditRepository) {
        this.clientRepository = clientRepository;
        this.creditRepository = creditRepository;
    }

    @Override
    public Optional<Boolean> checkCredit(UUID clientId, VehicleModel model) {
        Optional<NeurotechClient> client = clientRepository.findById(clientId);

        if (client.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(model.checkCredit(client.get()));
    }

    @Override
    public CreditResponseDto requestCredit(UUID clientId, VehicleModel model) {
        Optional<NeurotechClient> optionalClient = clientRepository.findById(clientId);

        if (optionalClient.isEmpty()) {
            return new CreditResponseDto(null, CreditStatus.CLIENT_NOT_FOUND, "Cliente não encontrado");
        }

        NeurotechClient client = optionalClient.get();
        boolean isCreditValid = model.checkCredit(client);

        if (!isCreditValid) {
            return new CreditResponseDto(null, CreditStatus.REJECTED, "Crédito inválido");
        }

        Credit credit = createCredit(client, model);
        Credit saved = creditRepository.save(credit);

        return new CreditResponseDto(saved.getId(), CreditStatus.APPROVED, "Crédito aprovado");
    }

    private Credit createCredit(NeurotechClient client, VehicleModel vehicleModel) {
        FeeType feeType = determineFeeType(client.getAge());
        Credit credit = new Credit(client, vehicleModel, feeType);

        if (feeType == FeeType.FIX) {
            credit.setTaxValue(BigDecimal.valueOf(FIX_FEE_VALUE));
        }

        return credit;
    }

    private FeeType determineFeeType(short age) {
        if (age < 18) return FeeType.MINOR;
        if (age <= 25) return FeeType.FIX;
        if (age < 65) return FeeType.VARIABLE;
        return FeeType.CONSIGNED;
    }

    @Override
    public Optional<Credit> get(UUID id) {
        return creditRepository.findById(id);
    }
}
