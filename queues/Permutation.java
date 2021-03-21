import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int n;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
            if (args.length > 1) {
                n = n > args.length ? args.length - 1 : n;
                for (int i = 0; i < n; i++) {
                    queue.enqueue(args[i + 1]);
                }
            }
        } else {
            n = StdIn.readInt();
            for (int i = 0; i < n; i++) {
                queue.enqueue(StdIn.readString());
            }
        }

        queue.forEach(StdOut::println);
    }
}
