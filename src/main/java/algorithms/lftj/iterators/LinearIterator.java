package algorithms.lftj.iterators;

import java.util.List;

/**
 * source: http://openproceedings.org/ICDT/2014/paper_13.pdf
 * <p>
 * int key() Returns the key at the current iterator position
 * next() Proceeds to the next key
 * seek(int seekKey) Position the iterator at a least upper bound for seekKey, i.e. the least key ≥ seekKey, or move to
 * end if no such key exists. The sought key must be ≥ the key at the current position.
 * bool atEnd() True when iterator is at the end
 */
public class LinearIterator<T extends Comparable<T>> {

    private List<T> elements;
    private int p;

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

    public T key() {
        return this.elements.get(this.p);
    }

    public void next() {
        this.p++;
    }

    public int seek(T seekKey) {
        while (!this.atEnd() && (elements.get(p).compareTo(seekKey) < 0)) {
            this.next();
        }
        return this.p;
    }

    public boolean atEnd() {
        return p >= this.elements.size() -1;
    }
}
