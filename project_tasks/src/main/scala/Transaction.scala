import exceptions._

import java.util.concurrent.locks.{Lock, ReadWriteLock, ReentrantLock, ReentrantReadWriteLock}
import scala.collection.mutable

object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}

class TransactionQueue {

    // TODO
    // project task 1.1
    // Add datastructure to contain the transactions
    val queue : mutable.Queue[Transaction] = new mutable.Queue[Transaction]();
    val queueLock : ReadWriteLock = new ReentrantReadWriteLock(); 

    // Remove and return the first element from the queue
    def pop: Transaction = {
      queueLock.writeLock().lock();
      val transaction : Transaction = queue.dequeue();
      queueLock.writeLock().unlock();

      return transaction;
    }

    // Return whether the queue is empty
    def isEmpty: Boolean = {
      queueLock.readLock().lock();
      val isEmpty : Boolean = queue.isEmpty;
      queueLock.readLock().unlock();

      return isEmpty;
    }

    // Add new element to the back of the queue
    def push(t: Transaction): Unit = {
      queueLock.writeLock().lock();
      queue.enqueue(t);
      queueLock.writeLock().unlock();
    }

    // Return the first element from the queue without removing it
    def peek: Transaction = {
      queueLock.readLock().lock();
      val transaction: Transaction = queue.front;
      queueLock.readLock().unlock();

      return transaction;
    }

    // Return an iterator to allow you to iterate over the queue
    def iterator: Iterator[Transaction] = {
      return queue.iterator;
    }
}

class Transaction(val transactionsQueue: TransactionQueue,
                  val processedTransactions: TransactionQueue,
                  val from: Account,
                  val to: Account,
                  val amount: Double,
                  val allowedAttemps: Int) extends Runnable {

  var status: TransactionStatus.Value = TransactionStatus.PENDING
  var attempt = 0
  var lock : Lock = new ReentrantLock();

  override def run(): Unit = {

      def doTransaction(): Either[Unit, String] = {
          attempt += 1;
          // TODO - project task 3
          // Extend this method to satisfy requirements.
          var either : Either[Unit, String] = from.withdraw(amount);

          if(either.isRight) {
            //Something went wrong. Putting the money back
            from.deposit(amount);
            return either;
          }

          either = to.deposit(amount);

          if(either.isRight) {
            //Something went wrong. Putting the money back from 'from' and withdrawing from 'to'
            from.deposit(amount);
            to.withdraw(amount);
            return either;
          }

          status = TransactionStatus.SUCCESS;

          return Left();
      }

      // TODO - project task 3
      // make the code below thread safe
      lock.lock();
      if (attempt < allowedAttemps && status == TransactionStatus.PENDING) {
          val either = doTransaction();

          if(either.isRight) {
            //Something went wrong.
            
          }

          Thread.sleep(50) // you might want this to make more room for
                           // new transactions to be added to the queue
      }
      lock.unlock();
    }
}
