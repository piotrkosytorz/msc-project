package algorithms.joinplan.hashjoin;

import algorithms.joinplan.BinaryJoinAlgorithm;
import helpers.SetOperationsOnStrArraysHelper;
import query.Atom;
import query.Query;
import query.Tuple;

import java.util.*;

public class BinaryHashJoin<T extends Comparable> implements BinaryJoinAlgorithm {


    @Override
    public List<Map<String, T>> executeJoin(Query query) {

        Atom[] atoms = query.getAtoms();

        List<String> variables0 = Arrays.asList(atoms[0].getVariables());
        List<String> variables1 = Arrays.asList(atoms[1].getVariables());


        String[] keysThatMustMatch = SetOperationsOnStrArraysHelper.getIntersecttionOfStrArray(
                atoms[0].getVariables(),
                atoms[1].getVariables()
        );

        Arrays.sort(keysThatMustMatch);

        String[] otherKeys = SetOperationsOnStrArraysHelper.getSymmetricDifferenceOfStrArrays(
                atoms[0].getVariables(),
                atoms[1].getVariables()
        );

        ArrayList<Integer>[] keysPositionsThatMustMatch = new ArrayList[2];

        keysPositionsThatMustMatch[0] = new ArrayList<>();
        for (String variableInKeys : keysThatMustMatch) {
            for (int i = 0; i < atoms[0].getVariables().length; i++) {
                // variableInRelation0 = atoms[0].getVariables()[i]
                if (atoms[0].getVariables()[i].equals(variableInKeys)) {
                    keysPositionsThatMustMatch[0].add(i);
                }
            }
        }

        keysPositionsThatMustMatch[1] = new ArrayList<>();
        for (String variableInKeys : keysThatMustMatch) {
            for (int i = 0; i < atoms[1].getVariables().length; i++) {
                // variableInRelation1 = atoms[1].getVariables()[i]
                if (atoms[1].getVariables()[i].equals(variableInKeys)) {
                    keysPositionsThatMustMatch[1].add(i);
                }
            }
        }

        int smallerRelationIndex = 0;
        int biggerRelationIndex = 1;

        if (atoms[0].getRelation().size() > atoms[1].getRelation().size()) {
            smallerRelationIndex = 1;
            biggerRelationIndex = 0;
        }

        int requredSize = SetOperationsOnStrArraysHelper.getUnionOfStrArrays(
                atoms[biggerRelationIndex].getVariables(),
                atoms[smallerRelationIndex].getVariables()
        ).length;

        Map<String, List<Tuple>> map = new HashMap<>();

        for (Object tupleS : atoms[smallerRelationIndex].getRelation().getElements()) {
            StringBuilder hash = new StringBuilder();
            for (Integer position : keysPositionsThatMustMatch[smallerRelationIndex]) {
                hash.append(((Tuple) tupleS).get(position).toString());
                hash.append(".");
            }
            List<Tuple> tuples = map.getOrDefault(hash.toString(), new ArrayList<>());
            tuples.add((Tuple) tupleS);
            map.put(hash.toString(), tuples);
        }

        List<Map<String, T>> results = new ArrayList<>();

        for (Object tupleB : atoms[biggerRelationIndex].getRelation().getElements()) {
            StringBuilder hash = new StringBuilder();
            for (Integer position : keysPositionsThatMustMatch[biggerRelationIndex]) {
                hash.append(((Tuple) tupleB).get(position).toString());
                hash.append(".");
            }

            Map<String, T> partialResultBase = new HashMap<>();

            for (String s : atoms[biggerRelationIndex].getVariables()) {
                for (Object x : ((Tuple) tupleB).getElements()) {
                    if (s == atoms[biggerRelationIndex].getVariables()[((Tuple) tupleB).getElements().indexOf(x)]) {
                        partialResultBase.put(s, (T) x);
                    }
                }
            }

            List<Tuple> tuples = map.get(hash.toString());

            if (tuples != null && !tuples.isEmpty()) {

                for (Tuple tuple : tuples) {

                    Map<String, T> partialResult = new HashMap<>(partialResultBase);

                    for (int i = 0; i < atoms[smallerRelationIndex].getVariables().length; i++) {
                        partialResult.put(
                                atoms[smallerRelationIndex].getVariables()[i],
                                (T) tuple.get(i)
                        );
                    }

                    if (partialResult.size() == requredSize) {
                        results.add(partialResult);
                    }

                }

            }
        }

        return results;
    }
}