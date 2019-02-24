package algorithms.nestedloop;

import javafx.util.Pair;
import query.Atom;
import query.Query;
import query.Relation;
import query.Tuple;

import java.util.*;

public class IterativeNestedLoopJoin<T extends Comparable<T>> {

    // just a list of relations;
    List<Relation> relations = new ArrayList<>();

    private Integer[] positions;

    // map containing <Variable name, List<Pair<<Relation pointer, position>>>>
    private HashMap<String, List<Pair<Relation, Integer>>> equalityConditionsMap = new HashMap<>();

    private HashMap<Relation, Integer> relationIndexInRelationsList = new HashMap<>();

    public IterativeNestedLoopJoin(Query query) {

        for (Atom atom : query.getAtoms()) {

            // make a list of unique relations
            if (!relations.contains(atom.getRelation())) {
                relations.add(atom.getRelation());
            }

            //  prepare equality conditions map
            Integer position = 0;
            for (String variable : atom.getVariables()) {
                if (equalityConditionsMap.containsKey(variable)) {
                    equalityConditionsMap.get(variable).add(new Pair<>(atom.getRelation(), position));
                } else {
                    List list = new ArrayList<Pair<Relation, Integer>>();
                    list.add(new Pair<>(atom.getRelation(), position));
                    equalityConditionsMap.put(variable, list);
                }
                position++;
            }

        }

        // filling in iterators list
        this.positions = new Integer[relations.size()];

        for (int i = 0; i < relations.size(); i++) {
            this.relationIndexInRelationsList.put(relations.get(i), i);
            this.positions[i] = 0;
        }

    }

    /**
     * Checks current permutation against conditions from equalityConditionsMap, and if they are met, returns a full
     * result, otherwise returns null.
     *
     * @return Map<String, T>|null
     */
    public Map<String, T> getResultOrNull() {

        Iterator<Map.Entry<String, List<Pair<Relation, Integer>>>> it = equalityConditionsMap.entrySet().iterator();

        Map<String, T> currentResult = new HashMap<>();

        // for every list of conditions (equality)
        while (it.hasNext()) {

            Map.Entry<String, List<Pair<Relation, Integer>>> entry = it.next();

            List list = entry.getValue();

            // check if all elements in current condition are true;

            T currentValue = null;

            for (Object item : list) {

                Pair relationPosition = (Pair<Relation, Integer>) item;
                Relation r = (Relation) relationPosition.getKey();
                Integer p = (Integer) relationPosition.getValue();

                Integer relationIndex = relationIndexInRelationsList.get(r);
                Tuple t = (Tuple) r.getElements().get(positions[relationIndex]);

                if (currentValue == null) {

                    currentValue = (T) t.get(p);
                    currentResult.put(entry.getKey(), (T) t.get(p));

                } else {

                    if (currentValue != (T) t.get(p)) {
                        return null;
                    }
                }
            }
        }

        return currentResult;
    }

    /**
     * Moves the multi-iterator one step ahead and returns true if reached the ultimate end or false if not.
     *
     * @return boolean
     */
    public boolean moveOneForward() {

        // if all iterators at end -> done
        if (this.allAtEnd())
            return true;

        int i = this.relations.size() - 1;

        // unless it's not the first column (iterator) go from right to left and reset the iterators that are at end
        while (i > 0 && this.positions[i] >= this.relations.get(i).size() - 1) {
            // reset column
            this.positions[i] = 0;
            i--;
        }

        this.positions[i]++;
        return false;
    }

    /**
     * Checks if all iterators are at end
     *
     * @return boolean
     */
    private boolean allAtEnd() {

        boolean atEnd = true;

        for (int k = 0; k < this.relations.size(); k++) {
            atEnd = atEnd && positions[k] == this.relations.get(k).size() - 1;
        }

        return atEnd;
    }


}
