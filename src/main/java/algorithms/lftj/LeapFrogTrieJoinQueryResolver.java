package algorithms.lftj;

import algorithms.QueryResolver;
import algorithms.QueryResolverIterator;
import query.Query;

import java.util.*;

public class LeapFrogTrieJoinQueryResolver<T extends Comparable> implements QueryResolver<T>, QueryResolverIterator<T> {

    @Override
    public QueryResolverIterator<T> getQueryResolverIterator(Query query) {

//        LeapFrogTrieJoin<T> leapFrogTrieJoin = new LeapFrogTrieJoin<>();
//        leapFrogTrieJoin.bootstrap(query.getAtoms());

        return null;

    }

    @Override
    public List<Map<String, T>> getFullResult(Query query) throws Exception {

        LeapFrogTrieJoin<T> leapFrogTrieJoin = new LeapFrogTrieJoin<>(query);

        // reset all
        List<Map<String, T>> cumulativeResult = new ArrayList<>();
        leapFrogTrieJoin.currentKeysStack = new Stack<>();

        while (!allIteratorsAtEnd(leapFrogTrieJoin)) {

            digDown(leapFrogTrieJoin);

            // while bottom iterator can proceed
            while (!leapFrogTrieJoin.atEnd()) {

                // It is the bottom iterator, thus the currentKeysStack contains a full result

                //  currentKeysStack to cumulative hash stack conversion
                Map<String, T> result = new HashMap<>();
                for (int i = 0; i < leapFrogTrieJoin.currentKeysStack.size(); i++) {
                    result.put(leapFrogTrieJoin.variables[i], leapFrogTrieJoin.currentKeysStack.get(i));
                }

                cumulativeResult.add(result);

                // proceed to next result at current level
                leapFrogTrieJoin.next();
            }

            // not all iterators at
            jumpOver(leapFrogTrieJoin);
        }


        return cumulativeResult;
    }

    private boolean allIteratorsAtEnd(LeapFrogTrieJoin leapFrogTrieJoin) {
        for (int i = 0; i < leapFrogTrieJoin.variables.length; i++) {
            if (!leapFrogTrieJoin.leapfrogJoins[i].isAtEnd()) {
                return false;
            }
        }
        return true;
    }

    private boolean digDown(LeapFrogTrieJoin leapFrogTrieJoin) throws Exception {
        while (leapFrogTrieJoin.depth < leapFrogTrieJoin.maxDepth()) {
            leapFrogTrieJoin.open();
        }
        return leapFrogTrieJoin.depth == leapFrogTrieJoin.maxDepth();
    }

    private boolean jumpOver(LeapFrogTrieJoin leapFrogTrieJoin) throws Exception {
        if (leapFrogTrieJoin.depth > 0) {
            leapFrogTrieJoin.up();
            leapFrogTrieJoin.next();
            if (!leapFrogTrieJoin.atEnd()) {
                while (leapFrogTrieJoin.depth < leapFrogTrieJoin.maxDepth()) {
                    leapFrogTrieJoin.open();
                }
                return true;
            } else {
                jumpOver(leapFrogTrieJoin);
            }
        }
        return false;
    }
}