object Task2B extends App {
  private var counter: Int = 0

  for(i <- 1 to 2) {
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

  def increaseCounter(): Unit = {
    counter += 1
  }

  def printCounter(): Unit = {
    println("Current counter: " + counter);
  }

}
