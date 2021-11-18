import java.util.NoSuchElementException

class Bank(val allowedAttempts: Integer = 3) {

    private val transactionsQueue: TransactionQueue = new TransactionQueue()
    private val processedTransactions: TransactionQueue = new TransactionQueue()

    def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {    
        // TODO
        // project task 2
        // create a new transaction object and put it in the queue
        // spawn a thread that calls processTransactions

        val newTransaction: Transaction = new Transaction(transactionsQueue, processedTransactions, from, to, amount, allowedAttempts);
        transactionsQueue.push(newTransaction);

        val thread = new Thread {
            override def run: Unit = {
                processTransactions
            }
        }
        thread.start();
    }

    private def processTransactions: Unit = {
        // TOO
        // project task 2
        // Function that pops a transaction from the queue
        // and spawns a thread to execute the transaction.
        // Finally do the appropriate thing, depending on whether
        // the transaction succeeded or not

        val thread = new Thread {
            override def run: Unit = {
                try {
                    val transaction = transactionsQueue.pop;
                    transaction.run();
                    if (transaction.status == TransactionStatus.PENDING) {
                        transactionsQueue.push(transaction);
                        processTransactions;
                    }
                    else {
                        processedTransactions.push(transaction)
                    }
                } catch {
                    case e: NoSuchElementException => //Do nothing. Thread has no work to do;
                }
            }
        }
        thread.start();
    }


    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }

}
