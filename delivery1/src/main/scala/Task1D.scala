object Task1D extends App {

  //Testing code
  println(fib(15));

  //Create a function to compute the nth Fibonacci number using recursion without using memoization
  //(or other optimizations). Use BigInt instead of Int. What is the difference between these two data
  //types?
  def fib(n : BigInt) : BigInt = {
    if(n == 1 || n == 2) {
      return 1;
    }
    else {
      return fib(n - 1) + fib(n - 2);
    }
  }
}
