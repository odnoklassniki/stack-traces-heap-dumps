package demo2;

import java.util.Random;

/**
 * Shows JDK 9 internal LiveStackFrame API which allows to obtain
 * local variables from stack traces.
 */
public class Labyrinth {
    static final byte FREE = 0;
    static final byte OCCUPIED = 1;
    static final byte VISITED = 2;

    private final byte[][] field;

    public Labyrinth(int size) {
        Random random = new Random(0);
        field = new byte[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (random.nextInt(10) > 7) {
                    field[x][y] = OCCUPIED;
                }
            }
        }

        field[0][0] = field[size - 1][size - 1] = FREE;
    }

    public int size() {
        return field.length;
    }

    public String toString() {
        return "Labyrinth";
    }

    public boolean visit(int x, int y) {
        if (x == 0 && y == 0) {
            // Thread.dumpStack();  // default stack dump
            LiveStackTrace.dump();  // stack dump with local variable info
            return true;
        }

        if (x < 0 || x >= size() || y < 0 || y >= size() || field[x][y] != FREE) {
            return false;
        }

        field[x][y] = VISITED;
        return visit(x - 1, y) || visit(x, y - 1) || visit(x + 1, y) || visit(x, y + 1);
    }

    public static void main(String[] args) {
        Labyrinth lab = new Labyrinth(10);
        lab.visit(9, 9);
    }
}
