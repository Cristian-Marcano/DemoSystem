package com.util;

import java.util.List;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Cristian
 * Clase que ayuda en la validacion de inputs de las interfaces visuales del proyecto
 */
public class ValidateInput {
    
    //* Valida si el listado de inputs estan vacios y enlista sus nombres en excepciones, para mostrarlo al usuario
    public static void isEmptyOrBlank(List<JTextComponent> inputs) throws Exception {
        String names = "";
        for(JTextComponent input: inputs)
            if(input.getText().isEmpty() || input.getText().isBlank()) names += (names.isEmpty()) ? input.getName() : ", " + input.getName();
        
        if(!names.isEmpty()) throw new Exception(names + " estan vacios");
    }
    
    //* Valida si el input cumple con el minimo de caracteres
    public static void isMinimumLength(JTextField input, int length) throws Exception {
        if(input.getText().length() < length) throw new Exception(input.getName() + "debe de tener un minimo de " + length + " caracteres");
    }
    
    //* Valida si el inputPassword cumple con el minimo de caracteres
    public static void isMinimumLength(JPasswordField input, int length) throws Exception {
        if(input.getPassword().length < length) throw new Exception(input.getName() + " debe de tener un minimo de " + length + " caracteres");
    }
}
