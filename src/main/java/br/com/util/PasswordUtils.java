package br.com.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Método para criptografar uma senha
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, PasswordUtils.gensalt());
    }

    // Método para verificar se uma senha em texto plano corresponde ao hash criptografado
    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    private static String gensalt() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
