package joiner.datastructures;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Tuple<T> {
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
}
