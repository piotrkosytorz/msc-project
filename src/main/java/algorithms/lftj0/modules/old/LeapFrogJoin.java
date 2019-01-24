package algorithms.lftj0.modules.old;

import java.util.Arrays;

public class LeapFrogJoin<T extends Comparable> {

    private LFTrieBasedRelationIterator iterators[];                    // iterators
    private Integer p;                                              // position
    private boolean atEnd;                                          // all iterators at end
    private T key;                                                  // current key element

    public LeapFrogJoin(LFTrieBasedRelationIterator... iterators) {
        this.iterators = iterators;
    }

    public void init() {

        this.atEnd = false;

        for (LFTrieBasedRelationIterator i : this.iterators) {
            if (i.atEnd()) {
                this.atEnd = true;
                return;
            }
        }

        // Sort the array iterators by keys at which the iterators are positioned.
        // Note: this is a crappy sort, @todo investigate and improve
        Arrays.sort(iterators);

        this.p = 0;
        this.search();
    }

    /**
     * search
     */
    @SuppressWarnings("unchecked")
    public void search() {

        if (this.iterators.length == 0)
            return;

        T xprime = (T) this.iterators[Math.abs((this.p - 1) % (this.iterators.length))].key();

        while (true) {
            T x = (T) this.iterators[this.p].key();
            if (x.equals(xprime)) {
                this.key = x;
                return;
            } else {
                this.iterators[this.p].seek(xprime);
                if (iterators[this.p].atEnd()) {
                    atEnd = true;
                    return;
                }

                xprime = (T) iterators[this.p].key();
                this.p = (this.p + 1) % (this.iterators.length);

            }
        }
    }


    /**
     * Just as in the previous case (search) - there is a bug in the paper, the atEnd flag should be set, and the search
     * must be performed one last time anyway!
     *
     * @throws Exception
     */
    public void next() throws Exception {

        if (this.iterators.length == 0)
            return;

        this.iterators[this.p].next();

        if (this.iterators[this.p].atEnd()) {
            this.atEnd = true;
        }

        this.p = (this.p + 1) % (this.iterators.length);
        search();
    }

    public void seek(T seekKey) {

        if (this.iterators.length == 0)
            return;

        this.iterators[this.p].seek(seekKey);

        if (this.iterators[this.p].atEnd()) {
            this.atEnd = true;
        } else {
            this.p = (this.p + 1) % (this.iterators.length);
            search();
        }
    }

    public boolean atEnd() {
        return this.atEnd;
    }

    public T currentKey() {
        return key;
    }

    public LFTrieBasedRelationIterator[] getIterators() {
        return iterators;
    }

    public void run() throws Exception {
        this.init();
        while (!atEnd) {
            this.next();
        }
    }
}
