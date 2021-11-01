import exceptions._

import java.util.concurrent.locks.{ReadWriteLock, ReentrantReadWriteLock}

class Account(val bank: Bank, initialBalance: Double) {

    class Balance(var amount: Double) {
        val rwLock : ReadWriteLock = new ReentrantReadWriteLock();

        def readLock(): Unit = rwLock.readLock().lock();
        def readUnlock(): Unit = rwLock.readLock().unlock();
        def writeLock(): Unit = rwLock.writeLock().lock();
        def writeUnlock(): Unit = rwLock.writeLock().unlock();
    }

    val balance = new Balance(initialBalance)

    // TODO
    // for project task 1.2: implement functions
    // for project task 1.3: change return type and update function bodies
    def withdraw(amount: Double): Either[Unit, String] = {
        var either : Either[Unit, String] = null;

        balance.writeLock();

        if(balance.amount > amount) {
            if(amount >= 0) {
                balance.amount -= amount;
                either = Left();
            }
            else {
                either = Right("The amount to withdraw must be greater than 0");
            }
        }
        else {
            either = Right("There's not enough balance for the withdrawal");
        }

        balance.writeUnlock();

        return either;
    }
    def deposit (amount: Double): Either[Unit, String] = {
        var either : Either[Unit, String] = null;

        balance.writeLock();

        if(amount >= 0) {
            balance.amount += amount;
            either = Left();
        }
        else {
            either = Right("The amount to deposit must be greater than 0");
        }

        balance.writeUnlock();

        return either;
    }
    def getBalanceAmount: Double       = {
        balance.readLock();

        val amount = balance.amount;

        balance.readUnlock();

        return amount;
    }

    def transferTo(account: Account, amount: Double) = {
        bank addTransactionToQueue (this, account, amount)
    }


}
