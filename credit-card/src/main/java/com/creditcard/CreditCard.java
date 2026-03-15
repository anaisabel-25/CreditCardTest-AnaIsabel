package com.creditcard;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreditCard {
    private String number;
    private String titular;
    private String expirationDate; // Formato MM/yy
    private String cvv;
    private double saldo;
    private double limit;

    public CreditCard(String number, String titular, String expirationDate, String cvv, double limit) {
        validateNumber(number);
        validateExpirationDate(expirationDate);
        validateCvv(cvv);

        this.number = number;
        this.titular = titular;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.limit = limit;
        this.saldo = 0.0;
    }

    // --- Lógica de Negocio ---

    // --- Validaciones internas ---
    private static void validateNumber(String number) {
        if (number == null || !number.matches("\\d{13,19}")) {
            throw new IllegalArgumentException("Número de tarjeta inválido");
        }
    }

    private static void validateExpirationDate(String expirationDate) {
        if (expirationDate == null || !expirationDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            throw new IllegalArgumentException("Formato de fecha debe ser MM/yy");
        }
    }

    private static void validateCvv(String cvv) {
        if (cvv == null || !cvv.matches("\\d{3,4}")) {
            throw new IllegalArgumentException("CVV inválido (debe tener 3 o 4 dígitos)");
        }
    }
    // -------- Métodos de la tarjeta --------
    public boolean isExpired() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
            // Usamos el día 01 para comparar meses
            LocalDate expiry = LocalDate.parse("01/" + this.expirationDate, formatter).plusMonths(1);
            return expiry.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return true; 
        }
    }

    public void ingresar(double amount) throws Exception {
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (isExpired()) {
            throw new Exception("Tarjeta expirada");
        }
        if (saldo + amount > limit) {
            throw new Exception("Límite de crédito excedido");
        }
        this.saldo += amount;
    }

    public void retirar(double amount) throws Exception {
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser positivo");
        }

        if (amount > saldo) {
            throw new Exception("Saldo insuficiente para realizar el retiro");
        }

        // Lógica corregida: Restamos del saldo disponible
        this.saldo -= amount;
        System.out.println("Retiro exitoso. Nuevo saldo disponible: " + this.saldo);
    }

    // --- Getters ---

    public String getNumber() { return number; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public double getLimit() { return limit; }
    public String getExpirationDate() { return expirationDate; }
}