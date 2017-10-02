package demo1;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Demonstrates swallowed stack traces of implicit exceptions.
 * You'll see many exception messages without a stack trace:
 *     java.lang.ArrayIndexOutOfBoundsException
 * To enable stack traces, use -XX:-OmitStackTraceInFastThrow
 */
public class OffByOne {

    private boolean isSorted(long[] A) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] > A[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public void run() {
        for (int i = 0; i < 100_000_000; i++) {
            try {
                long[] A = ThreadLocalRandom.current().longs(10).toArray();
                if (isSorted(A)) {
                    System.out.println("Sorted");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new OffByOne().run();
    }
}
