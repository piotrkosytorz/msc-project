package query;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * class Tuple
 *
 * Tuple is an ordered set of basic elements.
 *
 * @param <T>
 */
public class Tuple<T extends Comparable<T>> implements Comparable<Tuple<T>> {
    private final List<T> elements;

    public Tuple(List<T> elements) {
        this.elements = elements;
    }

    @SuppressWarnings("unchecked")
    public Tuple(T... elements) {
        this.elements = Arrays.asList(elements);
    }

    public List<T> getElements() {
        return elements;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        return (T[]) elements.toArray();
    }

    public T get(int index) {
        return elements.get(index);
    }

    public int size() {
        return elements.size();
    }

    @Override
    public String toString() {

        StringJoiner joiner = new StringJoiner(",");

        for (T item : elements) {
            joiner.add(item.toString());
        }

        return '[' + joiner.toString() + ']';
    }

    @Override
    @SuppressWarnings("all")
    public int compareTo(Tuple<T> o2) {
        for (int i = 0; i < Math.min(this.getElements().size(), o2.getElements().size()); i++) {
            int c = this.getElements().get(i).compareTo(o2.getElements().get(i));
            if (c != 0) {
                return c;
            }
        }
        return Integer.compare(this.getElements().size(), o2.getElements().size());
    }
}
