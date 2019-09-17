package algorithms.joinplan;

import helpers.SetOperationsOnStrArraysHelper;
import query.Atom;
import query.Query;
import query.Relation;
import query.Tuple;

import java.util.*;

public class NaiveJoinPlan<T> {

    /**
     * @param query
     * @return
     * @throws Exception
     */
    public List<Map<String, T>> resolveQuery(Query query, BinaryJoinAlgorithm algorithm) throws Exception {

        while (query.getAtoms().length > 2) {

            ArrayList<Atom> atoms = new ArrayList<>(Arrays.asList(query.getAtoms()));

            for (int i = 1; i < atoms.size(); i++) {

                if (SetOperationsOnStrArraysHelper.sharesAnElement(
                        atoms.get(0).getVariables(),
                        atoms.get(i).getVariables()
                )) {
                    // join the two and replace them by the query that has been returned
                    List<Map<String, T>> partialResult = algorithm.executeJoin(new Query(atoms.get(0), atoms.get(i)));

                    if (!partialResult.isEmpty()) {
                        atoms.remove(atoms.get(i));
                        atoms.remove(atoms.get(0));

                        atoms.add(this.resultToAtom(partialResult));

                        query = new Query(atoms.toArray(new Atom[0]));
                    } else {
                        return null;
                    }
                }
            }
        }

        // only 2 atoms in query

        List<Atom> atoms = Arrays.asList(query.getAtoms());
        List<Map<String, T>> res = algorithm.executeJoin(new Query(atoms.get(0), atoms.get(1)));
        return res;
    }

    public Atom resultToAtom(List<Map<String, T>> result) throws Exception {

        List<String> variables = new ArrayList<>();

        for (String key : result.get(0).keySet()) {
            variables.add(key);
        }

        Relation relation = new Relation(variables.size());

        // note - the maps should be always sorted in the same way, so I'm skipping sorting here.

        for (Map<String, T> element : result) {
            relation.add(new Tuple(new ArrayList<T>(element.values())));
        }

        return new Atom(relation, variables.toArray(new String[0]));
    }
}
