package algorithms.hashjoin;

import algorithms.QueryResolver;
import algorithms.QueryResolverIterator;
import org.apache.commons.collections4.CollectionUtils;
import query.Atom;
import query.Query;
import query.Relation;
import query.Tuple;

import java.util.*;

public class HashJoinQueryResolver<T extends Comparable> implements QueryResolver<T>, QueryResolverIterator<T> {

    private Query query;

    @Override
    public void bootstrap(Query query) {
        this.query = query;
        // bootstrap (and indexing)

    }

    @Override
    public List<Map<String, T>> getFullResult() throws Exception {
        // cummulative result

        this.reduce(this.query);

        return null;
    }

    /**
     * @param query
     * @return
     * @throws Exception
     */
    private Query reduce(Query query) throws Exception {

        Atom[] atoms = query.getAtoms();

        if (atoms.length >= 2) {

            for (int i = 1; i < atoms.length; i++) {

                if (this.sharesAnElement(
                        atoms[0].getVariables(),
                        atoms[i].getVariables()
                )) {
                    // join the two and replace them by the query that has been returned
                    List<Map<String, T>> partialResult = this.nestedLoopJoin(new Query(atoms[0], atoms[i]));

                    Atom atom = this.resultToAtom(partialResult);

                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }
            }

        }

        return query;
    }

    /**
     * Nested loop join for a query with 2 atoms and 1 join element
     *
     * @param query
     * @return
     */
    private List<Map<String, T>> nestedLoopJoin(Query query) {

        Atom[] atoms = query.getAtoms();

        String[] keys = this.getIntersecttionOfStrArray(
                atoms[0].getVariables(),
                atoms[1].getVariables()
        );

        String[] diffKeys = this.getSymmetricDifferenceOfStrArrays(
                atoms[0].getVariables(),
                atoms[1].getVariables()
        );

        List<String> variables0 = Arrays.asList(atoms[0].getVariables());
        List<String> variables1 = Arrays.asList(atoms[1].getVariables());

        List<Map<String, T>> results = new ArrayList<>();

        // inner join = join on one or more column at the same time

        for (Object tuple0 : atoms[0].getRelation().getElements()) {
            for (Object tuple1 : atoms[1].getRelation().getElements()) {

                Map<String, T> partialResult = new HashMap<>();

                // chack all keys
                for (String key : keys) {

                    if (((Tuple) tuple0).get(variables0.indexOf(key)).compareTo(((Tuple) tuple1).get(variables1.indexOf(key))) == 0) {
                        partialResult.put(key, (T) ((Tuple) tuple0).get(variables0.indexOf(key)));
                    }
                }


                if (partialResult.size() > 0 && !results.contains(partialResult)) {

                    // adding the rest
                    for (String key : diffKeys) {
                        if (variables0.contains(key)) {
                            partialResult.put(key, (T) ((Tuple) tuple0).get(variables0.indexOf(key)));
                        } else if (variables1.contains(key)) {
                            partialResult.put(key, (T) ((Tuple) tuple1).get(variables1.indexOf(key)));
                        }
                    }

                    results.add(partialResult);

                }
            }
        }

        return results;

    }

//    private List<Map<String, T>> hashJoin(Query query) {
//
//    }

    private boolean sharesAnElement(String[] a, String[] b) {
        Set<String> bSet = new HashSet<>(Arrays.asList(b));

        for (String str : a) {
            if (bSet.contains(str)) {
                return true;
            }
        }

        return false;
    }

    private String[] getIntersecttionOfStrArray(String[] a, String[] b) {

        Set<String> bSet = new HashSet<>(Arrays.asList(b));
        List<String> sharedStrings = new ArrayList<>();

        for (String str : a) {
            if (bSet.contains(str)) {
                sharedStrings.add(str);
            }
        }

        return sharedStrings.size() > 0 ? sharedStrings.toArray(new String[0]) : null;
    }

    private String[] getSymmetricDifferenceOfStrArrays(String[] a, String[] b) {

        Set<String> aSet = new HashSet<>(Arrays.asList(a));
        Set<String> bSet = new HashSet<>(Arrays.asList(b));
        return CollectionUtils.disjunction(aSet, bSet).toArray(new String[0]);
    }

    private Atom resultToAtom(List<Map<String, T>> result) throws Exception {

        List<String> variables = new ArrayList<>();

        for (String key : result.get(0).keySet()) {
            variables.add(key);
        }

        Relation relation = new Relation(variables.size());

        // note - the maps should be always sorted in the same way, so I'm skipping sorting here.

        for (Map<String, T> element: result) {
            relation.add(new Tuple(new ArrayList<T>(element.values())));
        }

        return new Atom(relation, variables.toArray(new String[0]));
    }


    @Override
    public Iterator<Map<String, T>> getIterator(Query query) {
        return new Iterator<Map<String, T>>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Map<String, T> next() {
                return null;
            }
        };
    }
}