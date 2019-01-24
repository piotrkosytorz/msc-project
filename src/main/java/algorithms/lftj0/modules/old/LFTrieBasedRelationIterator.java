package algorithms.lftj0.modules.old;

import algorithms.lftj.datasctructures.Trie.Trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class LFTrieBasedRelationIterator<T extends Comparable> implements Comparable<LFTrieBasedRelationIterator> {

    private Trie<T>.Node currentNode;
    private Stack<Integer> indexStack = new Stack<>();  // contains current path from the root

    public LFTrieBasedRelationIterator(Trie<T>.Node startNode) {
        this.currentNode = startNode;
    }

    /**
     * Returns the key at the current lfIterator position
     *
     * @return key
     */
    T key() {
        return this.currentNode.getKey();
    }

    /**
     * Proceeds to the next key
     */
    void next() throws Exception {

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
    void seek(T seekKey) {
        int i = indexStack.pop();
        List<T> elementsAtCurrentDepth = getElementsAtCurrentDepth();
        while (i < elementsAtCurrentDepth.size() && elementsAtCurrentDepth.get(i).compareTo(seekKey) < 0) {
            i++;
        }

        if (i >= elementsAtCurrentDepth.size()) {
            i = Integer.MAX_VALUE;  // +∞ @todo consider if this should be done differently
        }
        indexStack.push(i);
        currentNode = currentNode.getParent().getNthChild(i);
    }

    /**
     * True when lfIterator is at the end.
     *
     * @return
     */
    boolean atEnd() {
        return !(currentNode.hasParent() && currentNode.getParent().getChildren().size() > Integer.sum(indexStack.peek(), 1)); // is its parent last child = has no more siblings
    }

    /**
     * Proceed to the first key at the next depth
     */
    void open() throws Exception {
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
    void up() throws Exception {
        Trie<T>.Node parent = currentNode.getParent();
        if (parent != null) {
            indexStack.pop(); // - current level is reset
            currentNode = parent;
        } else {
            throw new Exception("Illegal operation: called \"up()\" on root element.");
        }
    }

    @Override
    public int compareTo(LFTrieBasedRelationIterator LFTrieBasedRelationIterator2) {
        int l = Integer.min(getElementsAtCurrentDepth().size(), LFTrieBasedRelationIterator2.getElementsAtCurrentDepth().size());
        for (int i = 0; i < l; i++) {
            if (getElementsAtCurrentDepth().get(i).compareTo(LFTrieBasedRelationIterator2.getElementsAtCurrentDepth().get(i)) < 0)
                return -1;
            if (getElementsAtCurrentDepth().get(i).compareTo(LFTrieBasedRelationIterator2.getElementsAtCurrentDepth().get(i)) > 0)
                return 1;
        }
        return 0;
    }

    private List<T> getElementsAtCurrentDepth() {
        List<T> l = new ArrayList<>();
        if (currentNode.hasParent()) {
            List<Trie<T>.Node> children = currentNode.getParent().getChildren();
            for (Trie<T>.Node n : children) {
                l.add((T) n.getKey());
            }
            Collections.sort(l);
            return l;
        }
        return new ArrayList<>();
    }
}
