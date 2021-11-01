object Task1B extends App {
  //Testing code
  println(sum(Array(1, 2, 3, 4)))

  // Create a function that sums the elements in an array of integers using a for loop.
  def sum(array : Array[Int]): Int = {
    var sum = 0;

    for(element <- array) {
      sum += element;
    }

    return sum
  }
}
