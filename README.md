# CreditCardTest-AnaIsabel
Clase CreditCard y sus pruebas unitarias con jUnit
classDiagram
    class CreditCard {
        -String number
        -String holder
        -double balance
        -double creditLimit
        -String pin
        -boolean isActivated
        +activate(String pin)
        +charge(double amount) bool
        +pay(double amount)
        +getBalance() double
    }

