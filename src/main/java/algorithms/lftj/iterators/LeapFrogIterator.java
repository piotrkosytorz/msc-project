package algorithms.lftj.iterators;

public interface LeapFrogIterator<T> {

    /**
     * Returns the key at the current iterator position
     *
     * @return T
     */
    public T key();

    /**
     * Proceeds to the next key
     */
    public void next();

    /**
     * Position the iterator at a least upper bound for seekKey, i.e. the least key ≥ seekKey, or move to
     * end if no such key exists. The sought key must be ≥ the key at the current position.
     *
     * @param seekKey
     * @return int
     */
    public int seek(T seekKey);

    /**
     * True when iterator is at the end
     *
     * @return bool
     */
    public boolean atEnd();

    /**
     * Opens iterator - only for tree iterators
     */
    public void open() throws Exception;

    /**
     * Return to the parent key at the previous depth - only for tree iterators
     */
    public void up() throws Exception;
}
