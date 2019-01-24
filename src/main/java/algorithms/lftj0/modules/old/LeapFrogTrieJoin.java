package algorithms.lftj0.modules.old;

import query.Atom;
import algorithms.lftj.datasctructures.Trie.TrieBasedRelation;

import java.util.*;

public class LeapFrogTrieJoin<T extends Comparable> {

    private String[] variablesOrdering;
    private LeapFrogJoin[] leapfrogJoins;

    private Stack<T> currentKeysStack = new Stack<>();
    private List<Map<String, T>> cumulativeResult;

    // The triejoin uses a variable depth to track the current variable for which a binding is being sought; initially
    // depth = -1 to indicate the triejoin is positioned at the root of the binding trie. (see the paper)
    private int depth = -1;

    /**
     * Bootstraps the tree walker. A helper for constructors.
     * Puts the relations in the right buckets (per variable from the query)
     *
     * @param atoms
     */
    private void bootstrap(Atom[] atoms) {
        HashMap<String, List<TrieBasedRelation>> buckets = new HashMap<>();
        // Determine variables ordering
        for (Atom atom : atoms) {

            // convert to trie-based relation per atom:
            TrieBasedRelation atomTrieBasedRelation = new TrieBasedRelation<>(atom.getRelation());

            for (String variableName : atom.getVariables()) {
                if (buckets.containsKey(variableName)) {
                    buckets.get(variableName).add(atomTrieBasedRelation);
                } else {
                    List<TrieBasedRelation> TrieBasedRelations = new ArrayList<>();
                    TrieBasedRelations.add(atomTrieBasedRelation);
                    buckets.put(variableName, TrieBasedRelations);
                }
            }
        }

        this.variablesOrdering = buckets.keySet().toArray(new String[0]);

        // Prepare an array of leapfrogJoins - one for each variable
        leapfrogJoins = new LeapFrogJoin[variablesOrdering.length];

        // The leapfrog join for a variable x is given an array of pointers to trie-iterators, one for each atom
        // in which x appears. (see paper)
        for (int i = 0; i < variablesOrdering.length; i++) {
            List<TrieBasedRelation> TrieBasedRelations = buckets.get(variablesOrdering[i]);
            LFTrieBasedRelationIterator[] iterators = new LFTrieBasedRelationIterator[TrieBasedRelations.size()];
            for (int j = 0; j < TrieBasedRelations.size(); j++) {
                iterators[j] = TrieBasedRelations.get(j).getLeapFrogTrieIterator();
            }
            leapfrogJoins[i] = new LeapFrogJoin(iterators);
        }
    }

    /**
     * Constructor
     *
     * @param atoms
     */
    public LeapFrogTrieJoin(Atom<T>[] atoms) {
        bootstrap(atoms);
    }

    /**
     * LFTJ uses the variable "depth" to control which iterators should be triggered at their own times.
     *
     * @return Integer maxDepth
     */
    private int maxDepth() {
        return variablesOrdering.length - 1;
    }

    // The linear lfIterator portions of the trie-lfIterator interface (namely key(), atEnd(), next(), and seek())
    // are delegated to the leap-frog join for the current variable. (see the paper)

    /**
     * Returns current key at current depth (single value)
     *
     * @return Integer key
     */
    @SuppressWarnings("unchecked")
    private T key() {
        return (T) leapfrogJoins[depth].currentKey();
    }

    /**
     * True when LeapFrogJoin at the current depth is at end.
     *
     * @return boolean
     */
    public boolean atEnd() {
        return leapfrogJoins[depth].atEnd();
    }

    /**
     * Proceeds to the next element at current depth.
     *
     * @throws Exception - exception is forwarded from leapfrog join portion.
     */
    private void next() throws Exception {

        leapfrogJoins[depth].next();

        // currently known part of the tuple
        currentKeysStack.pop();
        currentKeysStack.push(key());

        // Collect the whole tuple to the results list
        if (depth == maxDepth()) {

            // iterate through stack and add results to cumulativeResult

            Map<String, T> result = new HashMap<>();
            for (int i = 0; i < currentKeysStack.size(); i++) {
                result.put(variablesOrdering[i], currentKeysStack.get(i));
            }
            this.cumulativeResult.add(result);

        }
    }

    /**
     * Seeks value (single value) at current depth
     *
     * @param seekKey
     */
    @SuppressWarnings("unchecked")
    public void seek(T seekKey) {
        leapfrogJoins[depth].seek(seekKey);
    }

    /**
     * Opens the next level
     *
     * @throws Exception
     */
    public void open() throws Exception {

        depth++;

        for (int i = 0; i < leapfrogJoins[depth].getIterators().length; i++) {
            LFTrieBasedRelationIterator[] iters = leapfrogJoins[depth].getIterators();
            iters[i].open();
        }

        leapfrogJoins[depth].init();
        currentKeysStack.push(key());

        if (depth == maxDepth()) {
            Map<String, T> result = new HashMap<>();
            for (int i = 0; i < currentKeysStack.size(); i++) {
                result.put(variablesOrdering[i], currentKeysStack.get(i));
            }
            this.cumulativeResult.add(result);
        }
    }

    public void up() throws Exception {

        for (LFTrieBasedRelationIterator iterator : leapfrogJoins[depth].getIterators()) {
            iterator.up();
        }
        depth--;
        currentKeysStack.pop();
    }

    private boolean allLeapFrogJoinsAtEnd() {
        return Arrays.stream(leapfrogJoins).allMatch(LeapFrogJoin::atEnd);
    }

    private void jumpOver() throws Exception {

        // operate only within the variables scope
        if (depth > 0) {

            this.up();

            if (leapfrogJoins[depth].atEnd()) {
                jumpOver();
            } else {
                this.next();
            }
            this.open();

        } else {
            // out of scope -> end of the algorithm
            throw new Exception("The end.");
        }
    }

    public List<Map<String, T>> collectAllJoinResults() throws Exception {

        // reset all
        cumulativeResult = new ArrayList<>();
        currentKeysStack = new Stack<>();

        // opening the trees until the first join found
        for (int i = 0; i <= maxDepth(); i++) {
            this.open();
        }

        while (!allLeapFrogJoinsAtEnd()) {
            while (!leapfrogJoins[depth].atEnd()) {
                this.next();
            }
            try {
                jumpOver();
            } catch (Exception e) {
                break;
            }
        }

        return cumulativeResult;
    }

}