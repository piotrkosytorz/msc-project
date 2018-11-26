package algorithms.lftj;

import algorithms.Algorithm;
import algorithms.Atom;

import java.util.List;
import java.util.Map;

public class LeapFrogTrieJoin<T extends Comparable> implements Algorithm<T> {

    private algorithms.lftj.modules.LeapFrogTrieJoin lftj;

    @Override
    public void prepareQuery(Atom... atoms) {
        this.lftj = new algorithms.lftj.modules.LeapFrogTrieJoin(atoms);
    }

    @Override
    public List<Map<String, T>> run() throws Exception {
        return lftj.collectAllJoinResults();
    }

}
