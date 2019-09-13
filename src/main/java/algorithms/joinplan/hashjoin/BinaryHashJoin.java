package algorithms.joinplan.hashjoin;

import algorithms.joinplan.BinaryJoinAlgorithm;
import helpers.SetOperationsOnStrArraysHelper;
import query.Atom;
import query.Query;
import query.Tuple;

import java.util.*;

public class BinaryHashJoin<T extends Comparable> implements BinaryJoinAlgorithm {


    @Override
    public List<Map> executeJoin(Query query) {

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

        Map<String, List<Tuple>> map = new HashMap<>();

        for (Object tupleS : atoms[smallerRelationIndex].getRelation().getElements()) {
            String hash = "";
            for (Integer position : keysPositionsThatMustMatch[smallerRelationIndex]) {
                hash += ((Tuple) tupleS).get(position).toString() + ".";
            }
            List<Tuple> v = map.getOrDefault(hash, new ArrayList<>());
            v.add((Tuple) tupleS);
            map.put(hash, v);
        }

        List<Map<String, T>> results = new ArrayList<>();

        return null;
    }
}