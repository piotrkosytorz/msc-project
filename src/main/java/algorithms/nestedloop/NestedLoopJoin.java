package algorithms.nestedloop;

import algorithms.Algorithm;
import algorithms.Atom;
import com.sun.tools.javac.util.Pair;
import joiner.datastructures.Relation;
import joiner.datastructures.Tuple;

import java.util.*;

public class NestedLoopJoin<T> implements Algorithm<T> {


    private Relation[] relations;
    private HashMap<String, List<Pair<Relation, Integer>>> relationsEqualityMap = new HashMap<>();
    private Stack<Pair<Relation, Tuple>> actualTuples = new Stack<>();
    private List<Map<String, T>> cumulativeResult;

    @Override
    public void prepareQuery(Atom[] atoms) {

        cumulativeResult = new ArrayList<>();

        // prepare iterators and the condition tester
        Set<Relation> rSet = new HashSet<>();

        for (Atom atom : atoms) {

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

        // for not known reason, Java doesn't want to cast the whole list, so I have to do it one by one.
        relations = new Relation[rSet.size()];
        int i = 0;
        for (Relation r : rSet) {
            relations[i] = r;
            i++;
        }
    }

    private void nestedLoop(Relation[] list, int depth) {

        for (Object tuple : list[depth].toArray()) {
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
                    if (conditionPair.fst.equals(actualPair.fst)) {         // the same relation

                        Tuple t = (Tuple) actualPair.snd;

                        if (actualValue == null) {
                            actualValue = (T) t.get((Integer) conditionPair.snd);
                        } else if (!t.get((Integer) conditionPair.snd).equals(actualValue)) {
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
    public List<Map<String, T>> run() {

        int depth = 0;                  // starting at 0
        nestedLoop(relations, depth);   // recursion

        return this.cumulativeResult;
    }
}
