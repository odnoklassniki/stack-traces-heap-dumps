package demo3;

/**
 * Counts a number of recursive calls before StackOverflowError.
 * The number differes each time because of background JIT compilation.
 * See https://stackoverflow.com/a/36024368/3448419 for details.
 */
public class Recursion2 {
    static int depth;

    static void recursion() {
        depth++;
        recursion();
    }

    public static void main(String[] args) {
        try {
            recursion();
        } catch (StackOverflowError e) {
            System.out.println(depth);
        }
    }
}
