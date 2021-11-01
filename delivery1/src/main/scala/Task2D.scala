object Task2D extends App {
  object Foo1 {
    lazy val value = 5
    lazy val a: Int = Foo2.b
  }

  object Foo2 {
    lazy val b: Int = Foo1.value
  }

  val thread1 = new Thread() {
    override def run(): Unit = {
      println(Foo1.a)
    }
  }
  val thread2 = new Thread() {
    override def run(): Unit = {
      println(Foo2.b)
    }
  }

  thread1.start();
  thread2.start();

  thread1.join();
  thread2.join();
}
