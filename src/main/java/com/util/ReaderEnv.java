package com.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cristian
 */
public class ReaderEnv {
    
    protected static BigInteger n = new BigInteger("987654321098765432109876543210987654321"), e = new BigInteger("65537"), d = new BigInteger("123456789012345678901234567890123456789");
    protected static String keyVigenere;
    protected static BigInteger eKey;
    protected static Integer bitLength = 1024;
    
    public ReaderEnv() {
        Map<String, String> envVariables = new HashMap<>();
        String line;
        if(validateAttributes())
        try (BufferedReader br = new BufferedReader(new FileReader(".env"))) {
            while ((line = br.readLine()) != null) {
                // Ignorar líneas vacías y comentarios
                if (line.trim().isEmpty() || line.startsWith("#"))
                    continue;
                
                String[] parts = line.split("=", 2);
                
                if(parts.length == 2) 
                    envVariables.put(parts[0].trim(), parts[1].trim());
            }
            
            if(!validateEnv(envVariables)) {
                keyVigenere = envVariables.get("keyVigenere");
                bitLength = Integer.valueOf(envVariables.get("bit_length"));
                eKey = new BigInteger(envVariables.get("eKey"));
                n = new BigInteger (envVariables.get("RSA_N"));
                e = new BigInteger (envVariables.get("RSA_E"));
                d = new BigInteger (envVariables.get("RSA_D")); 
            }
        } catch(IOException e) {
            System.err.println("Not found envs");
            System.err.println(e.getMessage());
        }
    }
    
    //* Verificar si los atributos que almacenaran los datos de las variables de entorno (.env) estan vacios 
    private boolean validateAttributes() {
        return keyVigenere == null && eKey == null;
    }
    
    //* Validar si se encuentra las variables de entorno esperadas
    private boolean validateEnv(Map<String, String> envs) {
        return envs.get("keyVigenere")==null && envs.get("publicKeyRSA")==null && envs.get("privateKeyRSA")==null; 
    }
}
