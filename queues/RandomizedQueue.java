import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;   // queue elements
    private int n;          // number of elements on queue
    private int first;      // index of first element of queue
    private int last;       // index of next available slot
    private int rndIndex;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Argument can't be null");
        // double size of array if necessary and recopy to front of array
        if (n == items.length) resize(2*items.length);   // double size of array if necessary
        items[last++] = item;                        // add item
        if (last == items.length) last = 0;          // wrap-around
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = getRandomItem();
        items[rndIndex] = items[first];
        items[first] = null;                            // to avoid loitering
        n--;
        first++;
        if (first == items.length) first = 0;           // wrap-around
        // shrink size of array if necessary
        if (n > 0 && n == items.length/4) resize(items.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return getRandomItem();
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private final Item[] rndItems;

        public ArrayIterator() {
            rndItems = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                rndItems[j] = items[(first + j) % items.length];
            }
            StdRandom.shuffle(rndItems);
        }

        public boolean hasNext()  { return i < n;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = rndItems[i];
            i++;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) StdOut.println("queue.dequeue(): " + queue.dequeue());
            StdOut.println("random queue: [" + String.join(", ", queue) + "]");
        }
    }

    private Item getRandomItem() {
        Item item = null;
        while (item == null) {
            rndIndex = StdRandom.uniform(items.length);
            item = items[rndIndex];
        }
        return item;
    }
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = items[(first + i) % items.length];
        }
        items = copy;
        first = 0;
        last  = n;
    }

}