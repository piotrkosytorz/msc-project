package algorithms.lftj.iterators;

import query.Relation;

import java.util.List;

/**
 * Linear Iterator for LeapFrog Join
 * <a href="http://openproceedings.org/ICDT/2014/paper_13.pdf">Source paper</a>
 */
public class LinearIterator<T extends Comparable<T>> implements Comparable<LinearIterator<T>> {

    private List<T> elements;
    public int p;

    /**
     * CAUTION!
     * There is an assumption that the list of elements is already sorted!
     *
     * @param relation
     */
    public LinearIterator(Relation<T> relation) {
        this.elements = relation.getElements();
        this.p = 0;
    }

    /**
     * CAUTION!
     * There is an assumption that the list of elements is already sorted!
     *
     * @param elements
     */
    public LinearIterator(List<T> elements) {
        this.elements = elements;
        this.p = 0;
    }

    /**
     * Returns the key at the current iterator position
     *
     * @return T
     */
    public T key() {
        return this.elements.get(this.p);
    }

    /**
     * Proceeds to the next key
     */
    public void next() {
        this.p++;
    }

    /**
     * Position the iterator at a least upper bound for seekKey, i.e. the least key ≥ seekKey, or move to
     * end if no such key exists. The sought key must be ≥ the key at the current position.
     *
     * @param seekKey
     * @return int
     */
    public int seek(T seekKey) {
        while (!this.atEnd() && (elements.get(p).compareTo(seekKey) < 0)) {
            this.next();
        }
        return this.p;
    }

    /**
     * True when iterator is at the end
     *
     * @return bool
     */
    public boolean atEnd() {
        return this.p >= this.elements.size() - 1;
    }

    /**
     * @return List<T> elements
     */
    List<T> getElements() {
        return this.elements;
    }

    @Override
    public int compareTo(LinearIterator<T> o2) {
        for (int i = 0; i < Math.min(this.getElements().size(), o2.getElements().size()); i++) {
            int c = this.getElements().get(i).compareTo(o2.getElements().get(i));
            if (c != 0) {
                return c;
            }
        }
        return Integer.compare(this.getElements().size(), o2.getElements().size());
    }
}
