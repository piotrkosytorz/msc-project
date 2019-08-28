package algorithms.lftj;

import algorithms.lftj.iterators.LeapFrogIterator;

import java.util.Arrays;

public class LeapFrogJoin<T extends Comparable<T>> {

    private LeapFrogIterator<T>[] iterators;
    private boolean atEnd = false;
    private int p;
    private T key;

    /**
     * The leapfrog-init method is provided an array of iterators; it ensures the iterators are sorted according
     * to the key at which they are positioned, an invariant that is maintained throughout.
     *
     * @param iterators List of Leap Frog Iterators
     */
    public LeapFrogJoin(LeapFrogIterator<T>[] iterators) {
        this.iterators = iterators;
    }

    /**
     * Algorithm 1: leapfrog-init()
     *
     * <pre>
     * if any iterator has atEnd() true then
     *   atEnd := true ;
     * else
     *   atEnd := false ;
     *   sort the array Iter[0..k − 1] by keys at which the iterators are positioned ;
     *   p := 0 ;
     *   leapfrog-search()
     * </pre>
     */
    public void leapfrogInit() {

        this.atEnd = false;

        for (LeapFrogIterator iterator : this.iterators) {
            if (iterator.atEnd()) {
                this.atEnd = true;
                break;
            }
        }

        // LinearIterator implements Comparable interface and has a compareTo method that
        // refers to keys at which the iterators are positioned.
        Arrays.sort(iterators);
        this.p = 0;
        leapfrogSearch();
    }

    /**
     * Algorithm 2: leapfrog-search()
     *
     * <pre>
     * x′:= Iter[(p − 1) mod k].key() ; // Max key of any iter
     * while true do
     *   x := Iter[p].key() ; // Least key of any iter
     *   if x = x′
     *     then
     *       key := x ; // All iters at same key
     *       return;
     *   else
     *     Iter[p].seek(x′);
     *     if Iter[p].atEnd() then
     *       atEnd := true ;
     *       return;
     *     else
     *       x′:= Iter[p].key();
     *       p := p + 1 mod k;
     * </pre>
     */

    private void leapfrogSearch() {

        int k = this.iterators.length;
        T x1 = this.iterators[signedMod(this.p - 1, k)].key();

        while (true) {
            T x = this.iterators[this.p].key();

            if (x.compareTo(x1) == 0) {
                this.key = x;
                return;
            } else {
                this.iterators[this.p].seek(x1);
                if (this.iterators[this.p].atEnd()) {
                    this.atEnd = true;
                    return;
                } else {
                    x1 = iterators[this.p].key();
                    this.p = (this.p + 1) % k;
                }
            }
        }
    }

    /**
     * Algorithm 3: leapfrog-next()
     *
     * <pre>
     * Iter[p].next();
     * if Iter[p].atEnd() then
     *   atEnd := true;
     * else
     *   p := p + 1 mod k;
     *   leapfrog-search();
     * </pre>
     */
    public void leapfrogNext() {
        int k = this.iterators.length;
        this.iterators[this.p].next();
        if (this.iterators[this.p].atEnd()) {
            this.atEnd = true;
        } else {
            this.p = (this.p + 1) % k;
            this.leapfrogSearch();
        }
    }

    /**
     * Algorithm 4: leapfrog-seek(int seekKey)
     *
     * <pre>
     * Iter[p].seek(seekKey);
     * if Iter[p].atEnd() then
     *   atEnd := true;
     * else
     *   p := p + 1 mod k;
     *   leapfrog-search();
     * </pre>
     */
    public void leapfrogSeek(T seekKey) {

        int k = this.iterators.length;

        this.iterators[this.p].seek(seekKey);

        if (this.iterators[this.p].atEnd()) {
            this.atEnd = true;
        } else {
            this.p = (this.p + 1) % k;
            this.leapfrogSearch();
        }
    }

    public boolean isAtEnd() {
        return atEnd;
    }

    public T getKey() {
        return key;
    }

    public LeapFrogIterator[] getIterators() {
        return iterators;
    }

    /**
     * Modulo on Möbius strip
     *
     * @param n
     * @param m
     * @return
     */
    private static int signedMod(int n, int m) {
        return (n >= 0) ? n % m : m - ((-n) % m);
    }
}
