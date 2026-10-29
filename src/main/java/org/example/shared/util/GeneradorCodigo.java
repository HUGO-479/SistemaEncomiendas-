package org.example.shared.util;

import java.util.UUID;

public class GeneradorCodigo {
    public static String generarCodigoSeguimiento() {
        // Genera algo como: ENC-A1B2C3
        return "ENC-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
