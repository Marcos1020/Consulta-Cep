package com.sanches.consultacep.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

import static java.util.Map.entry;
@Component
public class ValorFretePorRegiao {

    private static final Map<String, Double> valoresFrete = Map.ofEntries(

            entry("SP", 7.85),
            entry("RJ", 7.85),
            entry("MG", 7.85),
            entry("ES", 7.85),

            entry("MS", 12.5),
            entry("MT", 12.5),
            entry("GO", 12.5),
            entry("DF", 12.5),

            entry("BA", 15.98),
            entry("PE", 15.98),
            entry("CE", 15.98),
            entry("AL", 15.98),
            entry("PI", 15.98),
            entry("RN", 15.98),
            entry("SE", 15.98),
            entry("PB", 15.98),
            entry("MA", 15.98),

            entry("RS", 17.3),
            entry("SC", 17.3),
            entry("PR", 17.3),

            entry("AM", 20.83),
            entry("PA", 20.83),
            entry("RO", 20.83),
            entry("AC", 20.83),
            entry("AP", 20.83),
            entry("TO", 20.83),
            entry("RR", 20.83)
    );

    public static double getValorFrete(String estadoParaCobrançaDoFrete) {
        if (estadoParaCobrançaDoFrete == null ) {
            return 0.0;
        }
        return valoresFrete.getOrDefault(estadoParaCobrançaDoFrete.toUpperCase(), 0.0);
    }
}