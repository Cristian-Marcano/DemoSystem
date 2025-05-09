package com.util;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Cristian
 */
public class RSA extends ReaderEnv{
    
    private int blockSize; // Tamaño del bloque en bytes
    
    public RSA(){
        blockSize = (n.bitLength() / 8) - 1; // Tamaño máximo de bloque
    }
    
    public String getPublicKey() {
        return "(" + e +","+ n + ")";
    }
    
    public String getPrivateKey() {
        return "(" + d +","+ n + ")";
    }
    
    // Cifrar un bloque de bytes
    public BigInteger encryptBlock(byte[] block) {
        BigInteger message = new BigInteger(1, block); // Convertir bloque a número
        return message.modPow(e, n); // c = m^e mod n
    }
    
    // Descifrar un bloque de bytes
    public byte[] decryptBlock(BigInteger encryptedBlock) {
        BigInteger message = encryptedBlock.modPow(d, n); // m = c^d mod n
        return message.toByteArray(); // Convertir número a bytes
    }
    
    // Cifrar un mensaje dividiéndolo en bloques
    public List<BigInteger> encryptMessage(String message) {
        byte[] bytes = message.getBytes(); // Convertir mensaje a bytes
        List<BigInteger> encryptedBlocks = new ArrayList<>();

        // Dividir mensaje en bloques de blockSize
        for (int i = 0; i < bytes.length; i += blockSize) {
            int length = Math.min(blockSize, bytes.length - i);
            byte[] block = new byte[length];
            System.arraycopy(bytes, i, block, 0, length);
            encryptedBlocks.add(encryptBlock(block)); // Cifrar cada bloque
        }
        return encryptedBlocks;
    }
    
    // Descifrar un mensaje dividiéndolo en bloques
    public String decryptMessage(List<BigInteger> encryptedBlocks) {
        List<byte[]> decryptedBlocks = new ArrayList<>();

        // Descifrar cada bloque
        for (BigInteger encryptedBlock : encryptedBlocks) {
            decryptedBlocks.add(decryptBlock(encryptedBlock));
        }

        // Concatenar todos los bloques
        int totalSize = decryptedBlocks.stream().mapToInt(b -> b.length).sum();
        byte[] decryptedMessage = new byte[totalSize];
        int position = 0;

        for (byte[] block : decryptedBlocks) {
            System.arraycopy(block, 0, decryptedMessage, position, block.length);
            position += block.length;
        }

        return new String(decryptedMessage); // Convertir bytes a String
    }
}
