object Task2A extends App {

    def first_foo(): Unit = println("hi")

    def second_foo(input_foo: => Unit): Thread = {
        val t = new Thread {
            override def run() = input_foo
        }
        t
    }

    second_foo(first_foo())

}
