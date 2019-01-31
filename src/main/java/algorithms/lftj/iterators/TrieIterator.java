package algorithms.lftj.iterators;

import algorithms.lftj.datasctructures.Trie.Trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class TrieIterator<T extends Comparable> implements LeapFrogIterator<T>, Comparable<TrieIterator> {

    private Trie<T>.Node currentNode;

    // indexStack is the bookkeeping part
    // contains current path from the root to current node
    private Stack<Integer> indexStack = new Stack<>();

    public TrieIterator(Trie<T>.Node startNode) {
        this.currentNode = startNode;
    }

    /**
     * Returns the key at the current lfIterator position
     *
     * @return key
     */
    @Override
    public T key() {
        return this.currentNode.getKey();
    }

    /**
     * Proceeds to the next key
     */
    @Override
    public void next() {

        if (atEnd()) {
            return;
        }

        int index = indexStack.pop();
        indexStack.push(index + 1);
        this.currentNode = this.currentNode.getNextSibling(index);

    }

    /**
     * Position the lfIterator at a least upper bound for seekKey, i.e. the least key ≥ seekKey, or move to end
     * if no such key exists. The sought key must be ≥ the key at the current position.
     *
     * @param seekKey
     */
    @Override
    public int seek(T seekKey) {
        int i = indexStack.pop();
        List<T> elementsAtCurrentDepth = getElementsAtCurrentDepth();

        while (!this.atEnd() && (elementsAtCurrentDepth.get(i).compareTo(seekKey) < 0)) {
            this.next();
        }

        indexStack.push(i);
        currentNode = currentNode.getParent().getNthChild(i);
        return i;
    }

    /**
     * True when lfIterator is at the end.
     *
     * @return
     */
    @Override
    public boolean atEnd() {
        // if current node is its parent last child => has no more siblings => iterator is at end
        return !(currentNode.hasParent() && currentNode.getParent().getChildren().size() > Integer.sum(indexStack.peek(), 1));
    }

    /**
     * Proceed to the first key at the next depth
     */
    @Override
    public void open() throws Exception {
        this.currentNode = this.currentNode.getFirstChild();
        if (this.currentNode != null) {
            indexStack.push(0);
        }
    }

    /**
     * Return to the parent key at the previous depth
     *
     * @throws Exception
     */
    @Override
    public void up() throws Exception {
        Trie<T>.Node parent = currentNode.getParent();
        if (parent != null) {
            indexStack.pop(); // - current level is reset
            currentNode = parent;
        } else {
            throw new Exception("Illegal operation: called \"up()\" on root element.");
        }
    }

    @Override
    public int compareTo(TrieIterator o2) {
        for (int i = 0; i < Math.min(this.getElementsAtCurrentDepth().size(), o2.getElementsAtCurrentDepth().size()); i++) {
            int c = this.getElementsAtCurrentDepth().get(i).compareTo(o2.getElementsAtCurrentDepth().get(i));
            if (c != 0) {
                return c;
            }
        }
        return Integer.compare(this.getElementsAtCurrentDepth().size(), o2.getElementsAtCurrentDepth().size());
    }

    /**
     * Returns a list of elements at current depth
     *
     * @return ArrayList
     */
    private List<T> getElementsAtCurrentDepth() {
        List<T> l = new ArrayList<>();
        if (currentNode.hasParent()) {
            List<Trie<T>.Node> children = currentNode.getParent().getChildren();
            for (Trie<T>.Node n : children) {
                l.add(n.getKey());
            }
            Collections.sort(l);
            return l;
        }
        return new ArrayList<>();
    }
}
