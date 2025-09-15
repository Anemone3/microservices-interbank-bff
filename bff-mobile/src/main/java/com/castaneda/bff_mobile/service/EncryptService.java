package com.castaneda.bff_mobile.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import com.castaneda.bff_mobile.exceptions.EncryptionException;

@Service
public class EncryptService {

    private static HashMap<String, Object> mapKeys = new HashMap<>();

    public EncryptService() {
        createKeys();
    }
    
    public void createKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            mapKeys.put("public-key",publicKey);
            mapKeys.put("private-key",privateKey);
        }catch (NoSuchAlgorithmException e) {
            throw new EncryptionException("Error crítico: No se pudieron generar las claves de encriptación");
        }catch (Exception e) {
            throw new EncryptionException("Error crítico: No se pudieron generar las claves de encriptación");
        }
    }

    public String encryptMessage(String input){

        if (input == null) {
            throw new EncryptionException("El mensaje a encriptar no puede ser nulo");
        }
        
        if (input.trim().isEmpty()) {
            throw new EncryptionException("El mensaje a encriptar no puede estar vacío");
        }
        
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            PublicKey publicKey = (PublicKey) mapKeys.get("public-key");
            
            if (publicKey == null) {
                throw new EncryptionException("Error interno: Clave pública no disponible");
            }
            
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(input.getBytes());
            String result = Base64.getUrlEncoder().withoutPadding().encodeToString(encrypted);
            
            return result;
            
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException("Error interno: Algoritmo de encriptación no disponible");
        } catch (NoSuchPaddingException e) {
            throw new EncryptionException("Error interno: Configuración de padding inválida");
        } catch (InvalidKeyException e) {
            throw new EncryptionException("Error interno: Clave de encriptación inválida");
        } catch (IllegalBlockSizeException e) {
            throw new EncryptionException("El mensaje es demasiado largo para encriptar con RSA");
        } catch (BadPaddingException e) {
            throw new EncryptionException("Error interno: Problema de padding en encriptación");
        } catch (Exception e) {
            throw new EncryptionException("Error inesperado durante la encriptación");
        }
    }

    public String decryptMessage(String encrypted){
        if (encrypted == null) {
            throw new EncryptionException("El mensaje encriptado no puede ser nulo");
        }
        
        if (encrypted.trim().isEmpty()) {
            throw new EncryptionException("El mensaje encriptado no puede estar vacío");
        }
        if (encrypted.length() < 300) { 
            throw new EncryptionException("El mensaje encriptado parece estar incompleto o corrupto");
        }
        
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            PrivateKey privateKey = (PrivateKey) mapKeys.get("private-key");
            
            if (privateKey == null) {
                throw new EncryptionException("Error interno: Clave privada no disponible");
            }
            
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            
            byte[] encryptedBytes;
            try {
                encryptedBytes = Base64.getUrlDecoder().decode(encrypted);
            } catch (IllegalArgumentException e) {
                throw new EncryptionException("El formato del mensaje encriptado es inválido (no es Base64 válido)");
            }
            
            byte[] decrypted = cipher.doFinal(encryptedBytes);
            String result = new String(decrypted);
            
            return result;
            
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException("Error interno: Algoritmo de desencriptación no disponible");
        } catch (NoSuchPaddingException e) {
            throw new EncryptionException("Error interno: Configuración de padding inválida");
        } catch (InvalidKeyException e) {
            throw new EncryptionException("Error interno: Clave de desencriptación inválida");
        } catch (IllegalBlockSizeException e) {
            throw new EncryptionException("El tamaño del mensaje encriptado es inválido");
        } catch (BadPaddingException e) {
            throw new EncryptionException("El mensaje encriptado está corrupto o fue encriptado con una clave diferente");
        } catch (Exception e) {
            throw new EncryptionException("Error inesperado durante la desencriptación");
        }
    }
}
