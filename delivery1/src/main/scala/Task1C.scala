object Task1C extends App {

    var arr = Array(1, 2, 3, 4, 5)
    var l = arr.length

    def sum_recursively(array : Array[Int], len : Int): Int = {
        if (len == 1) {
            return array(0)
        }
        else {
            return sum_recursively(array, len-1) + array(len-1)
        }    
    }
    println(sum_recursively(arr, l))
}
