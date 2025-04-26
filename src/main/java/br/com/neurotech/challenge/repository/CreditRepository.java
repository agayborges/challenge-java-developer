package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.dto.ClientDto;
import br.com.neurotech.challenge.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CreditRepository extends JpaRepository<Credit, UUID> {

    @Query("""
            SELECT NEW br.com.neurotech.challenge.dto.ClientDto(nc.name, nc.income)
            FROM Credit c 
            LEFT JOIN c.neurotechClient nc
            WHERE nc.age BETWEEN 23 AND 49
            AND nc.income BETWEEN 5000 AND 15000
            AND c.feeType = br.com.neurotech.challenge.entity.FeeType.FIX
            GROUP BY nc.id, nc.name, nc.income
            """)
    List<ClientDto> findSpecialClientsForHatchCredit();
}
