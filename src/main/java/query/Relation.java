package query;

import java.util.*;

/**
 * class Relation
 * <p>
 * A relation is an ordered list of tuples of the same arity and type of the (basic) element.
 *
 * @param <T>
 */
public class Relation<T extends Comparable<T>> implements Iterable<T> {

    private List<T> elements;
    private Integer arity;

    public Relation(Integer arity) {
        this.arity = arity;
        this.elements = new ArrayList<>();
    }

    public Relation(Tuple<T>... tuples) throws Exception {
        if (tuples.length > 0) {
            this.arity = tuples[0].size();
            this.addAll(tuples);
        } else {
            throw new Exception("No tuples provided for relation constructor.");
        }
    }

    @SuppressWarnings("unchecked")
    public void add(Tuple<T> tuple) throws Exception {

        if (tuple.size() != this.arity) {
            throw new Exception("Tuple size mismatch for relation arity.");
        }

        if (this.elements == null) {
            this.elements = new ArrayList<>();
        }

        this.elements.add((T) tuple);
        Collections.sort(elements);
    }

    @SuppressWarnings("unchecked")
    public void addAll(Tuple... tuples) {

        if (this.elements == null) {
            this.elements = new ArrayList<>();
        }

        Collections.addAll(this.elements, (T[]) tuples);
        Collections.sort(elements);
    }

    public List<T> getElements() {
        return elements;
    }

    public Integer getArity() {
        return arity;
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    public Integer size() {
        return elements.size();
    }
}
