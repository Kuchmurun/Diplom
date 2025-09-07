package com.example.wallet.service.parsing;

import com.example.wallet.DTO.CurrencyDTO;
import org.springframework.stereotype.Service;

@Service
public class ParsingOperation {

    public String parsFromOperation(CurrencyDTO currencyDTO) {
        String[] ops = currencyDTO.getOperation().split(":");
        return ops[0];
    }

    public String parsToOperation(CurrencyDTO currencyDTO) {
        String[] ops = currencyDTO.getOperation().split(":");
        return ops[1];
    }
}
