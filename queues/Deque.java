import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last;
    private int n = 0;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next, prev;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Argument can't be null");
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        if (oldFirst != null) {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        if (last == null) {
            last = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Argument can't be null");
        Node<Item> oldLast = last;
        last = new Node<>();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            if (oldLast != null) {
                oldLast.next = last;
            }
            last.prev = oldLast;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = first.item;
        first = first.next;
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        }
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more items to return");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deq = new Deque<>();

        deq.addFirst("-1");
        deq.addFirst("0");

        deq.addLast("1");
        deq.addLast("2");
        deq.addLast("3");
        deq.forEach(System.out::print);
        System.out.println("\nn: " + deq.size());

        deq.addFirst("4");
        deq.addFirst("5");
        deq.addFirst("6");
        deq.forEach(System.out::print);
        System.out.println("\nn: " + deq.size());

        deq.removeFirst();
        deq.removeFirst();
        deq.forEach(System.out::print);
        System.out.println("\nn: " + deq.size());

        deq.removeLast();
        deq.removeLast();
        deq.forEach(System.out::print);
        System.out.println("\nn: " + deq.size());

    }

}