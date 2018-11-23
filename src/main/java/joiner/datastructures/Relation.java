package joiner.datastructures;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Relation<T> implements Iterable<T> {

    private Set<T> container;
    private Integer arity;

    public Relation(Integer arity) {
        this.arity = arity;
        this.container = new HashSet<>();
    }

    @SuppressWarnings("unchecked")
    public void add(Tuple<T> tuple) throws Exception {

        if (tuple.size() != this.arity) {
            throw new Exception("Tuple size mismatch for relation arity.");
        }

        this.container.add((T) tuple);
    }

    @SuppressWarnings("unchecked")
    public void addAll(Tuple... tuples) {
        Collections.addAll(this.container, (T[]) tuples);
    }

    public Object[] toArray() {
        return this.container.toArray();
    }

    public Integer getArity() {
        return arity;
    }

    @Override
    public Iterator<T> iterator() {
        return container.iterator();
    }

}
