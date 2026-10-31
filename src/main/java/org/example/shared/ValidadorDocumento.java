package org.example.shared;

import org.example.persona.model.TipoDocumento;

public class ValidadorDocumento {

    public static void validar(TipoDocumento tipo, String numero) {
        if (numero == null || numero.isEmpty()) {
            throw new RuntimeException("El número de documento no puede estar vacío.");
        }
        switch (tipo) {
            case DNI:
                if (!numero.matches("^[0-9]{8}$")) throw new RuntimeException("El DNI debe tener 8 dígitos numéricos.");
                break;
            case RUC:
                if (!numero.matches("^[0-9]{11}$")) throw new RuntimeException("El RUC debe tener 11 dígitos numéricos.");
                break;
            case PASAPORTE:
                if (!numero.matches("^[A-Za-z0-9]{6,20}$")) throw new RuntimeException("El Pasaporte debe ser alfanumérico (6 a 20 caracteres).");
                break;
        }
    }
}
