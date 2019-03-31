package com.urise.webapp;

import com.urise.webapp.exception.StorageException;

public class MainDeadLock {

    public static void main(String[] args) {
        final Account account1 = new Account(1000);
        final Account account2 = new Account(1000);

        new Thread(() -> transfer(account1, account2, 200)).start();
        new Thread(() -> transfer(account2, account1, 500)).start();
    }

    private static void transfer(Account account1, Account account2, int amount) {

        if (account1.getBalance() < amount) {
            throw new StorageException("Не хватает средств");
        }
        synchronized (account1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (account2) {
                account1.withdraw(amount);
                account2.deposit(amount);
            }
        }

    }

    private static class Account {
        private int balance;

        private Account(int initialBalance) {
            this.balance = initialBalance;
        }

        private int getBalance() {
            return balance;
        }

        private void withdraw(int amount) {
            balance -= amount;
        }

        private void deposit(int amount) {
            balance += amount;
        }
    }
}








