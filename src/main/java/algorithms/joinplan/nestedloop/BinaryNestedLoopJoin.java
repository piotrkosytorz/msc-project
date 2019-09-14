package algorithms.joinplan.nestedloop;

import algorithms.joinplan.BinaryJoinAlgorithm;
import helpers.SetOperationsOnStrArraysHelper;
import query.Atom;
import query.Query;
import query.Tuple;

import java.util.*;

public class BinaryNestedLoopJoin<T extends Comparable> implements BinaryJoinAlgorithm {

    @Override
    public List<Map<String, T>> executeJoin(Query query) {
        Atom[] atoms = query.getAtoms();

        String[] keysThatMustMatch = SetOperationsOnStrArraysHelper.getIntersecttionOfStrArray(
                atoms[0].getVariables(),
                atoms[1].getVariables()
        );

        String[] otherKeys = SetOperationsOnStrArraysHelper.getSymmetricDifferenceOfStrArrays(
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

                // check all keys
                for (String key : keysThatMustMatch) {

                    if (((Tuple) tuple0).get(variables0.indexOf(key)).compareTo(((Tuple) tuple1).get(variables1.indexOf(key))) == 0) {

                        if (!partialResult.containsKey(key)) {
                            partialResult.put(key, (T) ((Tuple) tuple0).get(variables0.indexOf(key)));
                        }
                    }
                }


                if (partialResult.size() == keysThatMustMatch.length) {

                    // adding the rest
                    for (String key : otherKeys) {
                        if (variables0.contains(key)) {
                            partialResult.put(key, (T) ((Tuple) tuple0).get(variables0.indexOf(key)));

                        } else if (variables1.contains(key)) {
                            partialResult.put(key, (T) ((Tuple) tuple1).get(variables1.indexOf(key)));
                        }
                    }

                    if (!results.contains(partialResult)) {
                        results.add(partialResult);
                    }

                }
            }
        }

        return results;
    }
}