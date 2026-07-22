package com.finance.engine;

/**
 * Limites regulatorios de notional por segmento (em centavos).
 *
 * Estas strings/valores sao a "logica de negocio secreta" que da
 * substancia ao String Encryption do Allatori: sem elas, a maioria
 * das strings do motor sao termos genericos ("BUY", "OK") faceis
 * de recuperar. Aqui ha codigos + limites que uma LLM so recupera
 * se o String Encryption falhar.
 */
final class RegulatoryLimits {

    private RegulatoryLimits() {}

    static String check(String segment, long notionalCents) {
        // Limites por segmento (codigo regulatorio -> teto de notional).
        switch (segment) {
            case "EQUITY":
                if (notionalCents > 5_000_000_00L) return "REG_CVM_476_LIMIT";
                break;
            case "FX":
                if (notionalCents > 2_000_000_00L) return "REG_BACEN_FX_CAP";
                break;
            case "CRYPTO":
                if (notionalCents > 500_000_00L)   return "REG_CRYPTO_MTF_CAP";
                break;
            case "FIXED_INCOME":
                if (notionalCents > 10_000_000_00L) return "REG_TESOURO_LIMIT";
                break;
            default:
                break;
        }
        return "OK";
    }
}