package algorithms.lftj;

import algorithms.Algorithm;
import algorithms.lftj.datasctructures.Trie.TrieRelation;
import algorithms.lftj.iterators.LeapFrogIterator;
import algorithms.lftj.iterators.TrieIterator;
import query.Atom;
import query.Query;

import java.util.*;

public class LeapFrogTrieJoin<T extends Comparable> implements Algorithm<T> {

    // The triejoin uses a variable depth to track the current variable for which a binding is being sought; initially
    // depth = -1 to indicate the triejoin is positioned at the root of the binding trie. (see the paper)
    private int depth = -1;

    private String[] variables;
    private LeapFrogJoin[] leapfrogJoins;

    private Stack<T> currentKeysStack = new Stack<>();
    private List<Map<String, T>> cumulativeResult;

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
    @SuppressWarnings("Duplicates")
    public void bootstrap(Atom[] atoms) {

        // I decided to sort the query in buckets of variables with relations referenced to them:
        HashMap<String, List<TrieRelation>> buckets = new HashMap<>();

        for (Atom atom : atoms) {
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
        leapfrogJoins = new LeapFrogJoin[variables.length];

        // The leapfrog join for a variable x is given an array of pointers to trie-iterators, one for each atom
        // in which x appears. (see paper)
        // Here I am constructing iterators and leapfrog joins
        for (int i = 0; i < variables.length; i++) {
            List<TrieRelation> trieRelations = buckets.get(variables[i]);
            TrieIterator[] trieIterators = new TrieIterator[trieRelations.size()];
            for (int j = 0; j < trieRelations.size(); j++) {
                trieIterators[j] = trieRelations.get(j).getTrieIterator();
            }
            leapfrogJoins[i] = new LeapFrogJoin(trieIterators);
        }
    }

    /**
     * LFTJ uses the variable "depth" to control which iterators should be triggered at their own times.
     *
     * @return Integer maxDepth
     */
    private int maxDepth() {
        return variables.length - 1;
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
        return (T) leapfrogJoins[depth].getKey();
    }

    /**
     * True when LeapFrogJoin at the current depth is at end.
     *
     * @return boolean
     */
    public boolean atEnd() {
        return leapfrogJoins[depth].isAtEnd();
    }

    /**
     * Proceeds to the next element at current depth.
     *
     * @throws Exception - exception is forwarded from leapfrog join portion.
     */
    private void next() {
        leapfrogJoins[depth].leapfrogNext();
        currentKeysStack.pop();
        currentKeysStack.push(key());
    }

    /**
     * Seeks value (single value) at current depth
     *
     * @param seekKey
     */
    public void seek(T seekKey) {
        leapfrogJoins[depth].leapfrogSeek(seekKey);
    }

    /**
     * Opens the next level
     *
     * @throws Exception
     */
    @SuppressWarnings("Duplicates")
    public void open() throws Exception {

        depth++;

        // each iter in leapfrog join at current depth
        LeapFrogIterator[] iterators = leapfrogJoins[depth].getIterators();
        for (LeapFrogIterator iterator : iterators) {
            iterator.open();
        }

        leapfrogJoins[depth].leapfrogInit();

        this.currentKeysStack.add(key());
    }

    public void up() throws Exception {

        for (LeapFrogIterator iterator : leapfrogJoins[depth].getIterators()) {
            iterator.up();
        }

        depth--;

        currentKeysStack.pop();
    }

    @Override
    public void prepareQuery(Query query) {
        this.bootstrap(query.getAtoms());
    }

    @Override
    @SuppressWarnings("all")
    public List<Map<String, T>> run() throws Exception {

        while (depth < maxDepth()) {
            open();
        }

        System.out.println("OPEN");
        System.out.println(currentKeysStack);
        System.out.println("AtEnd: " + atEnd());

        next();
        System.out.println("NEXT");
        System.out.println("AtEnd: " + atEnd());
        System.out.println(currentKeysStack);

        up();
        System.out.println("UP");
        System.out.println("AtEnd: " + atEnd());
        System.out.println(currentKeysStack);

        next();
        System.out.println("NEXT");
        System.out.println("AtEnd: " + atEnd());
        System.out.println(currentKeysStack);

        up();
        System.out.println("UP");
        System.out.println("AtEnd: " + atEnd());
        System.out.println(currentKeysStack);

        next();
        System.out.println("NEXT");
        System.out.println("AtEnd: " + atEnd());
        System.out.println(currentKeysStack);

//        open();
//        System.out.println("AtEnd: " + atEnd());



//        System.out.println(currentKeysStack);


        return null;
    }
}

