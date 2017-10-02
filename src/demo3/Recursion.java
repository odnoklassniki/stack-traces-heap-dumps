package demo3;

/**
 * Just an infinite recursion
 */
public class Recursion {
    static int depth;

    static void recursion() {
        depth++;
        recursion();
    }

    public static void main(String[] args) {
        recursion();
    }
}
