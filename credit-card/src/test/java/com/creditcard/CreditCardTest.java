package com.creditcard;
/**
 * Pruebas unitarias para la clase CreditCard utilizando JUnit 4.
 * Estas pruebas verifican el comportamiento de los métodos isExpired, ingresar y retirar.
 */
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class CreditCardTest 
{
    //Declaro los objetos que voy a usar en las pruebas (inicializados en setUp)
    private CreditCard card;
    private CreditCard expiredCard;
    
    @Before
    public void setUp() throws Exception { //porque el constructor puede emitir excepciones
        card = new CreditCard("1234567891012", "Ana Isabel", "12/26", "123", 5000);
        expiredCard = new CreditCard("1234567891012345", "Ana Isabel", "01/20", "123", 5000);
    }

    @After
    public void tearDown() throws Exception {
        card = null;
        expiredCard = null;
    }

    @Test
    public void testIsExpired() {
        assertTrue(expiredCard.isExpired());
    }

    @Test
    public void testIngresar() throws Exception {
        double cantidad = 1000;
        double expectedsaldo = card.getSaldo() + cantidad;
        card.ingresar(cantidad);
        assertEquals(expectedsaldo, card.getSaldo(), 0.01);
    }

    @Test
    public void testRetirar() throws Exception {
        double cantidad = 500;
        card.ingresar(2000); // Primero ingresamos para tener saldo disponible
        double expectedsaldo = card.getSaldo() - cantidad;
        card.retirar(cantidad);
        assertEquals(expectedsaldo, card.getSaldo(), 0.01);
    }
    // NOTA: El 0.01 es el delta (tolerancia) que permite la comparación entre dos valores 
    // double en assertEquals. Significa que la prueba considera correctos los valores si 
    // están dentro de ±0.01 entre sí, porque las operaciones con double pueden tener pequeñas 
    // imprecisiones de punto flotante.
    // Prueba para número de tarjeta inválido (debe lanzar IllegalArgumentException)

    // Prueba para número de tarjeta inválido (debe lanzar IllegalArgumentException)

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidNumber() {
        // Intenta crear una tarjeta con un número que no tenga entre 13 y 19 dígitos
        new CreditCard("123", "Ana Isabel", "12/25", "123", 5000);
        fail("Se esperaba IllegalArgumentException para número de tarjeta inválido");
    }

    // Prueba para fecha de expiración inválida (debe lanzar IllegalArgumentException)
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidExpirationDate() {
        // Intenta crear una tarjeta con un formato de fecha incorrecto
        new CreditCard("1234567891012345", "Ana Isabel", "13/25", "123", 5000); // Mes 13 no es válido
        fail("Se esperaba IllegalArgumentException para fecha de expiración inválida");
    }

    // Prueba para CVV inválido (debe lanzar IllegalArgumentException)
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidCvv() {
        // Intenta crear una tarjeta con un CVV que no tenga 3 o 4 dígitos
        new CreditCard("1234567891012345", "Ana Isabel", "12/25", "12", 5000); // Solo 2 dígitos
        fail("Se esperaba IllegalArgumentException para CVV inválido");
    }

    // Prueba para monto negativo o cero en ingresar (debe lanzar IllegalArgumentException)
    @Test(expected = IllegalArgumentException.class)
    public void testIngresarInvalidAmount() throws Exception {
        card.ingresar(-100); // Cantidad negativa
        fail("Se esperaba IllegalArgumentException para cantidad negativa en ingresar");
    }

    // Prueba para retirar más de lo disponible (debe lanzar Exception)
    @Test(expected = Exception.class)
    public void testRetirarInsufficientFunds() throws Exception {
        card.retirar(6000); // Más que el saldo inicial (0) o límite
        fail("Se esperaba Exception para retiro de fondos insuficientes");
    
    }

    // Prueba para ingresar en una tarjeta expirada (debe lanzar Exception)
    @Test(expected = Exception.class)
    public void testIngresarExpirada() throws Exception {
        CreditCard expiredCard = new CreditCard("1234567891012345", "Ana Isabel", "01/20", "123", 5000);
        // Nota: Esta tarjeta ya está expirada según isExpired(), así que debería fallar
        expiredCard.ingresar(1000);
        fail("Se esperaba Exception para ingreso en tarjeta expirada");
    }
    // Prueba para ingresar más que el límite de crédito (debe lanzar Exception)
    @Test(expected = Exception.class)
    public void testIngresarSupLimite() throws Exception {
        card.ingresar(6000); // Intenta ingresar más que el límite de crédito
        fail("Se esperaba Exception para ingreso superior al límite de crédito");
    }
}
