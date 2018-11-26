package algorithms.lftj.modules;

import java.util.Arrays;

public class LeapFrogJoin<T extends Comparable> {

    private LFTrieBasedRelationIterator iters[];                    // iterators
    private Integer p;                                              // position
    private boolean atEnd;                                          // all iterators at end
    private T key;                                                  // current key element

    public LeapFrogJoin(LFTrieBasedRelationIterator... iters) {
        this.iters = iters;
    }

    public void init() {
        this.atEnd = false;
        for (LFTrieBasedRelationIterator i : this.iters) {
            if (i.atEnd()) {
                this.atEnd = true;
            }
        }

        // Sort the array iters by keys at which the iters are positioned.
        // Note: this is a crappy sort, @todo investigate and improve
        Arrays.sort(iters);

        this.p = 0;
        this.search();
    }

    /**
     * search
     */
    @SuppressWarnings("unchecked")
    public void search() {

        T xp = (T) iters[signedMod((this.p - 1), (this.iters.length))].key();

        int loopCountdown = this.iters.length;

        while (loopCountdown >= 0) {
            T x = (T) iters[p].key();
            if (x.equals(xp)) {
                key = x;
                return;
            } else {
                iters[p].seek(xp);
                if (iters[p].atEnd()) {
                    atEnd = true;
                }

                xp = (T) iters[p].key();

                p = (p + 1) % (this.iters.length);

                if (atEnd) {
                    loopCountdown--;
                }

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

        iters[p].next();

        if (iters[p].atEnd()) {
            atEnd = true;
        }

        p = (p + 1) % (this.iters.length);

        search();
    }

    public void seek(T seekKey) {
        iters[p].seek(seekKey);
        if (iters[p].atEnd()) {
            atEnd = true;
        } else {
            p = (p + 1) % (this.iters.length);
            search();
        }
    }

    public boolean atEnd() {
        return this.atEnd;
    }

    public T currentKey() {
        return key;
    }

    public LFTrieBasedRelationIterator[] getIters() {
        return iters;
    }

    public void run() throws Exception {
        this.init();
        while (!atEnd) {
            this.next();
        }
    }


    /**
     * Helper method: modulo on one-sided torus (Möbius strip)
     *
     * @param n
     * @param m
     * @return
     */
    private int signedMod(int n, int m) {
        return (n >= 0) ? n % m : m - ((-n) % m);
    }
}
