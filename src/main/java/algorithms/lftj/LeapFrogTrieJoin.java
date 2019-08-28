package algorithms.lftj;

import algorithms.lftj.datasctructures.Trie.TrieRelation;
import algorithms.lftj.iterators.LeapFrogIterator;
import algorithms.lftj.iterators.TrieIterator;
import query.Atom;
import query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class LeapFrogTrieJoin<T extends Comparable<T>> {

    // The triejoin uses a variable depth to track the current variable for which a binding is being sought; initially
    // depth = -1 to indicate the triejoin is positioned at the root of the binding trie. (see the paper)
    public int depth = -1;

    public String[] variables;
    public LeapFrogJoin<T>[] leapfrogJoins;

    // Stack of pointers to currently available part of the full solution tuple
    public Stack<T> currentKeysStack = new Stack<>();

    /**
     * LeapFrog Trie-Join Constructor
     * @param query
     */
    public LeapFrogTrieJoin(Query<T> query) {
        this.bootstrap(query.getAtoms());
    }

    /**
     * Bootstraps LFTJ.
     * <p>
     * -- PROCEDURE --
     * <p>
     * 1. Get all relations => here I assume that the a single relation is referenced only once per query,
     * thus that there is only one atom per query, referencing given relation. This is a precondition
     * for LFTJ (see original paper). It is handy to build here a query optimizer that will take care
     * of all preconditions of LFTJ.
     * IMPORTANT: at this point I am receiving Atoms, so relations have to be converted here to TrieRelation
     * type.
     * <p>
     * 2. Get trie iterators for each relation
     * <p>
     * 3. Get all variables
     * <p>
     * 4. Construct leapfrog join instances - one for each variable (in an array)
     * <p>
     * Note: The leapfrog join for a variable x is given an array of pointers to trie-iterators,
     * one for each atom in which x appears. (see paper)
     *
     * @param atoms
     */
    private void bootstrap(Atom<T>[] atoms) {

        // I decided to sort the query in buckets of variables with relations referenced to them:
        HashMap<String, List<TrieRelation>> buckets = new HashMap<>();

        for (Atom<T> atom : atoms) {
            // convert to TrieRelation per atom:
            TrieRelation trieRelation = new TrieRelation<>(atom.getRelation());

            // get variables, and segregate them into buckets - together with telations
            for (String variableName : atom.getVariables()) {
                if (buckets.containsKey(variableName)) {
                    buckets.get(variableName).add(trieRelation);
                } else {
                    List<TrieRelation> trieRelations = new ArrayList<>();
                    trieRelations.add(trieRelation);
                    buckets.put(variableName, trieRelations);
                }
            }
        }

        // At this point variables ordering is taken directly from the query. According to the paper, ordering
        // influences significantly the average execution time, but is irrelevant for worst cases.
        // This is the place where variables ordering optimization should take place.
        this.variables = buckets.keySet().toArray(new String[0]);

        // Prepare an array of leapfrogJoins - size = number of variables
        this.leapfrogJoins = new LeapFrogJoin[this.variables.length];

        // The leapfrog join for a variable x is given an array of pointers to trie-iterators, one for each atom
        // in which x appears. (see paper)
        // Here I am constructing iterators and leapfrog joins
        for (int i = 0; i < this.variables.length; i++) {
            List<TrieRelation> trieRelations = buckets.get(this.variables[i]);
            TrieIterator[] trieIterators = new TrieIterator[trieRelations.size()];
            for (int j = 0; j < trieRelations.size(); j++) {
                trieIterators[j] = trieRelations.get(j).getTrieIterator();
            }
            this.leapfrogJoins[i] = new LeapFrogJoin(trieIterators);
        }
    }

    /**
     * LFTJ uses the variable "depth" to control which iterators should be triggered at their own times.
     *
     * @return Integer maxDepth
     */
    public int maxDepth() {
        return this.variables.length - 1;
    }

    // The linear lfIterator portions of the trie-lfIterator interface (namely key(), atEnd(), next(), and seek())
    // are delegated to the leap-frog join for the current variable. (see the paper)

    /**
     * Returns current key at current depth (single value)
     *
     * @return Integer key
     */
    public T key() {
        return this.leapfrogJoins[this.depth].getKey();
    }

    /**
     * True when LeapFrogJoin at the current depth is at end.
     *
     * @return boolean
     */
    public boolean atEnd() {
        return this.leapfrogJoins[this.depth].isAtEnd();
    }

    /**
     * Proceeds to the next element at current depth.
     *
     * @throws Exception - exception is forwarded from leapfrog join portion.
     */
    public void next() {
        this.leapfrogJoins[this.depth].leapfrogNext();
        this.currentKeysStack.pop();
        this.currentKeysStack.push(key());
    }

//    /**
//     * Seeks value (single value) at current depth
//     *
//     * @param seekKey
//     */
//    public void seek(T  seekKey) {
//        this.leapfrogJoins[this.depth].leapfrogSeek(seekKey);
//    }

    /**
     * Opens the next level
     *
     * @throws Exception
     */
    @SuppressWarnings("Duplicates")
    public void open() throws Exception {

        this.depth++;

        // each iter in leapfrog join at current depth
        LeapFrogIterator[] iterators = this.leapfrogJoins[this.depth].getIterators();
        for (LeapFrogIterator iterator : iterators) {
            iterator.open();
        }

        this.leapfrogJoins[this.depth].leapfrogInit();
        this.currentKeysStack.push(key());

    }

    /**
     * Moves all iterators up
     *
     * @throws Exception
     */
    public void up() throws Exception {

        for (LeapFrogIterator iterator : this.leapfrogJoins[this.depth].getIterators()) {
            iterator.up();
        }

        this.depth--;
        this.currentKeysStack.pop();
    }
}

