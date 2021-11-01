object Task2C extends App {
    
    private var counter: Int = 0

    for(i <- 1 to 200000) {
        new Thread() {
            override def run() : Unit = {
                increaseCounter();
            }
        }.start();
    }

    new Thread() {
        override def run() : Unit = {
            printCounter();
        }
    }.start();

    def increaseCounter(): Unit = synchronized {
        counter += 1
    }

    def printCounter(): Unit = {
        println("Current counter: " + counter);
    }
}