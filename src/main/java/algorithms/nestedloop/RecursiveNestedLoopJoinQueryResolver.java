package algorithms.nestedloop;

import algorithms.QueryResolver;
import javafx.util.Pair;
import query.Atom;
import query.Query;
import query.Relation;
import query.Tuple;

import java.util.*;

/**
 * class RecursiveNestedLoopJoinQueryResolver
 * <p>
 * Nested loop join implementation according to the Algorithm interface.
 * The result is a list of join results, where a join result is a list of pairs [matched variable name : value]
 *
 * @param <T>
 */
public class RecursiveNestedLoopJoinQueryResolver<T> implements QueryResolver<T> {

    private Relation[] relations;
    private HashMap<String, List<Pair<Relation, Integer>>> relationsEqualityMap = new HashMap<>();
    private Stack<Pair<Relation, Tuple>> actualTuples = new Stack<>();
    private List<Map<String, T>> cumulativeResult;

    @Override
    public void bootstrap(Query query) {

        cumulativeResult = new ArrayList<>();

        // prepare iterators and the condition tester
        Set<Relation> rSet = new HashSet<>();

        for (Atom atom : query.getAtoms()) {

            // input data extraction: list of relations with their iterators
            rSet.add(atom.getRelation());

            // query variables extraction:
            Integer position = 0;
            for (String variableName : atom.getVariables()) {
                if (relationsEqualityMap.containsKey(variableName)) {
                    relationsEqualityMap.get(variableName).add(new Pair<>(atom.getRelation(), position));
                } else {
                    List list = new ArrayList<Pair<Relation, Integer>>();
                    list.add(new Pair<>(atom.getRelation(), position));
                    relationsEqualityMap.put(variableName, list);
                }
                position++;
            }

        }

        // Casting Set to array
        relations = new Relation[rSet.size()];
        int i = 0;
        for (Relation r : rSet) {
            relations[i] = r;
            i++;
        }
    }

    /**
     * The main - recursive join - method (nested loop)
     *
     * @param list
     * @param depth
     */
    private void nestedLoop(Relation[] list, int depth) {

        for (Object tuple : list[depth]) {
            actualTuples.push(new Pair<>(list[depth], (Tuple) tuple));
            if (depth < list.length - 1) {
                nestedLoop(list, depth + 1);
            } else {
                // reached the right depth
                Map<String, T> results = getResultOrNullForTuples(actualTuples, relationsEqualityMap);

                if (results != null) {
                    cumulativeResult.add(results);
                }
            }
            actualTuples.pop();
        }

    }

    private Map<String, T> getResultOrNullForTuples(Stack<Pair<Relation, Tuple>> actualTuples, HashMap<String, List<Pair<Relation, Integer>>> conditionList) {

        Map<String, T> results = new HashMap<>();

        // all conditions must be met
        for (Map.Entry<String, List<Pair<Relation, Integer>>> variableConditions : conditionList.entrySet()) {

            // Checking conditions for variable variableConditions.getKey()
            T actualValue = null;

            for (Pair conditionPair : variableConditions.getValue()) {

                for (Pair actualPair : this.actualTuples) {
                    if (conditionPair.getKey().equals(actualPair.getKey())) {         // the same relation

                        Tuple t = (Tuple) actualPair.getValue();

                        if (actualValue == null) {
                            actualValue = (T) t.get((Integer) conditionPair.getValue());
                        } else if (!t.get((Integer) conditionPair.getValue()).equals(actualValue)) {
                            return null;
                        }
                    }
                }
            }
            results.put(variableConditions.getKey(), actualValue);
        }

        return results;
    }

    @Override
    public List<Map<String, T>> getFullResult() throws Exception {

        int depth = 0;                  // starting at 0
        nestedLoop(relations, depth);   // recursion

        return this.cumulativeResult;
    }
}