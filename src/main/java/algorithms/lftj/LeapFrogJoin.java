package algorithms.lftj;

import algorithms.lftj.iterators.LinearIterator;
import query.Relation;

import java.util.Arrays;

public class LeapFrogJoin<T extends Comparable<T>> {

    private LinearIterator[] iterators;
    private boolean atEnd = false;
    private int p;
    private T key;

    private boolean lastIteration;

    /**
     * The leapfrog-init method is provided an array of iterators; it ensures the iterators are sorted according
     * to the key at which they are positioned, an invariant that is maintained throughout.
     *
     * @param relations
     */
    public LeapFrogJoin(Relation<T>[] relations) {

        // bootstrap
        this.iterators = new LinearIterator[relations.length];

        // get linear iterator for each relation
        for (int i = 0; i < relations.length; i++) {
            this.iterators[i] = new LinearIterator<>(relations[i]);
        }
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
        for (LinearIterator linearIterator : this.iterators) {
            if (linearIterator.atEnd()) {
                this.atEnd = true;
                break;
            }
        }

        // This is possible, because LinearIterator implements Comparable interface and has a compareTo method that
        // refers to keys at which the iterators are positioned.
        Arrays.sort(iterators);

        this.p = 0;

        System.out.println("<LeapFrogInit>");
        System.out.println("p: " + this.p);
        System.out.println("p-1: " + signedMod(this.p - 1, this.iterators.length));
        System.out.println("</LeapFrogInit>");

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
    @SuppressWarnings("all")
    public void leapfrogSearch() {

        int k = this.iterators.length;
        T x1 = (T) this.iterators[signedMod(this.p - 1, k)].key();

        while (true) {
            T x = (T) this.iterators[this.p].key();

//            System.out.println("<LeapFrogSearch>");
//            System.out.println("Iterator[" + signedMod(this.p - 1, k) + "][pos:" + this.iterators[signedMod(this.p - 1, k)].p + ", val:" + x1 + "]");
//            System.out.println("Iterator[" + this.p + "][pos:" + this.iterators[p].p + ", val:" + x + "]");
//            System.out.println("</LeapFrogSearch>");

            if (x.compareTo(x1) == 0) {
                this.key = x;
                if (this.lastIteration) {
                    this.atEnd = true;
                }
                return;
            } else {
                this.iterators[this.p].seek(x1);
                if (this.iterators[this.p].atEnd() && !this.lastIteration) {

                    this.lastIteration = true;

                    // Turn on the "roller".
                    // This is a modification on the original algorithm, which stops too early.
                    // The problem is, that once the first linear iterator reaches the end, there are still all the
                    // other iterators that must bechecked (thus at most k-1 iterators), because the last key may be
                    // equal to the current x and the tails of the other iterators must still be checked for that.
                    //
                    // Note: The iterations should stop immediately, after it is known that current x is the last key.

                } else if (this.iterators[this.p].atEnd() && this.lastIteration) {
                    this.atEnd = true;
                    return;
                } else {
                    x1 = (T) iterators[this.p].key();
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
    public void leapfrogSeek() {

        int k = this.iterators.length;

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

    /**
     * Modulo on one-sided torus (Möbius strip)
     *
     * @param n
     * @param m
     * @return
     */
    private static int signedMod(int n, int m) {
        return (n >= 0) ? n % m : m - ((-n) % m);
    }
}
