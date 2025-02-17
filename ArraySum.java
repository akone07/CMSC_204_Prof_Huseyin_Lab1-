package recursive_Lab;

public class ArraySum {
    /**
     * Recursively sums the values in an array up to the given index.
     * 
     * @param a     The array of integers.
     * @param index The index of the last element to include in the sum.
     * @return The sum of the elements from index 0 to index.
     */
    public int sumOfArray(Integer[] a, int index) {
        // If the index is less than 0, stop and return 0 (no numbers left to add)
        if (index < 0) {
            return 0;
        }
        // Otherwise, add the current number to the sum of the previous numbers
        return a[index] + sumOfArray(a, index - 1);
    }
}
