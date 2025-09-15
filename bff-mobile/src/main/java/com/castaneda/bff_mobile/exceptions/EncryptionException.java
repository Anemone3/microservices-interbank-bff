package com.castaneda.bff_mobile.exceptions;

public class EncryptionException extends RuntimeException {

    public EncryptionException(String message) {
        super(message);
    }



    public static EncryptionException paddingError(String uniqueCode) {
        return new EncryptionException(
                String.format("Error de desencriptación: El código único '%s' no es válido o está corrupto",
                        uniqueCode != null ? uniqueCode.substring(0, Math.min(uniqueCode.length(), 10)) + "..." : "null")
        );
    }

    public static EncryptionException codeNotFound() {
        return new EncryptionException("El código único proporcionado no existe o no se pudo procesar");
    }

    public static EncryptionException messageTooLarge() {
        return new EncryptionException("El mensaje a desencriptar excede el tamaño máximo permitido");
    }
}