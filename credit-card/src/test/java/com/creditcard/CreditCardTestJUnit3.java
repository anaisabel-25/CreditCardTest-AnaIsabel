package com.creditcard;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Pruebas unitarias para la clase CreditCard utilizando JUnit 3.
 * Estas pruebas verifican el comportamiento de los métodos isExpired, ingresar y retirar.
 */



public class CreditCardTestJUnit3 extends TestCase {
    private CreditCard card;
    private CreditCard expiredcard;

    public void setUp() throws Exception {
        card = new CreditCard("1234567891012", "Ana Isabel", "12/26", "123", 5000);
        expiredcard = new CreditCard("1234567891012345", "Ana Isabel", "01/20", "123", 5000);
    }

    public void tearDown() throws Exception {
        card = null;
        expiredcard = null;
    }

    public void testIsExpired() {
        assertTrue(expiredcard.isExpired());
    }

    public void testIngresar() throws Exception {
        double cantidad = 1000;
        double expectedsaldo = card.getSaldo() + cantidad;
        card.ingresar(cantidad);
        assertEquals(expectedsaldo, card.getSaldo(), 0.01);
    }

    public void testRetirar() throws Exception {
        double cantidad = 500;
        card.ingresar(2000); // Primero ingresamos para tener saldo disponible
        double expectedsaldo = card.getSaldo() - cantidad;
        card.retirar(cantidad);
        assertEquals(expectedsaldo, card.getSaldo(), 0.01);
    }

    // Prueba para número de tarjeta inválido (debe lanzar IllegalArgumentException)

    public void testConstructorInvalidNumber() {
        // Intenta crear una tarjeta con un número que no tenga entre 13 y 19 dígitos
        try {
            new CreditCard("123", "Ana Isabel", "12/25", "123", 5000);
            fail("Se esperaba IllegalArgumentException para número de tarjeta inválido");
        } catch (IllegalArgumentException e) {
            // Excepción esperada, la prueba pasa
        }
    }
    
    public void testConstructorInvalidExpirationDate() {
        // Intenta crear una tarjeta con un formato de fecha incorrecto
        try {
            new CreditCard("1234567891012345", "Ana Isabel", "13/25", "123", 5000); // Mes 13 no es válido
            fail("Se esperaba IllegalArgumentException para fecha de expiración inválida");
        } catch (IllegalArgumentException e) {
            // Excepción esperada, la prueba pasa
        }
    }
    
    public void testConstructorInvalidCvv() {
        // Intenta crear una tarjeta con un CVV que no tenga 3 o 4 dígitos
        try {
            new CreditCard("1234567891012345", "Ana Isabel", "12/25", "12", 5000); // Solo 2 dígitos
            fail("Se esperaba IllegalArgumentException para CVV inválido");
        } catch (IllegalArgumentException e) {
            // Excepción esperada, la prueba pasa
        }
    }

    public void testIngresarInvalidAmount() throws Exception {
        try {
            card.ingresar(-100); // Cantidad negativa
            fail("Se esperaba IllegalArgumentException para cantidad negativa en ingresar");
        } catch (IllegalArgumentException e) {
            // Excepción esperada, la prueba pasa
        }
    }

    public void testRetirarInsufficientFunds() throws Exception {
        try {
            card.retirar(6000); // Más que el saldo inicial (0) o límite
            fail("Se esperaba Exception para retiro de fondos insuficientes");
        } catch (Exception e) {
            // Excepción esperada, la prueba pasa
        }
    }

    public void testIngresarSupLimite() throws Exception {
        try {
            card.ingresar(6000); // Intenta ingresar más que el límite de crédito
            fail("Se esperaba Exception para ingreso superior al límite de crédito");
        } catch (Exception e) {
            // Excepción esperada, la prueba pasa
        }
    }

    public void testIngresarExpirada() throws Exception {
        try {
            expiredcard.ingresar(1000);
            fail("Se esperaba Exception para ingreso en tarjeta expirada");
        } catch (Exception e) {
            // Excepción esperada, la prueba pasa
        }
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CreditCardTestJUnit3.class);
        return suite;
    }
}
