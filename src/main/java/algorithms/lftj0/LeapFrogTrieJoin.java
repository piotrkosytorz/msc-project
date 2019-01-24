package algorithms.lftj0;

import algorithms.Algorithm;
import query.Query;

import java.util.List;
import java.util.Map;

public class LeapFrogTrieJoin<T extends Comparable> implements Algorithm<T> {

    private algorithms.lftj0.modules.old.LeapFrogTrieJoin lftj;

    @Override
    public void prepareQuery(Query query) {
        this.lftj = new algorithms.lftj0.modules.old.LeapFrogTrieJoin(query.getAtoms());
    }

    @Override
    public List<Map<String, T>> run() throws Exception {
        return lftj.collectAllJoinResults();
    }

}
