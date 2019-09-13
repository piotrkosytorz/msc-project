package algorithms.joinplan;

import query.Query;

import java.util.List;
import java.util.Map;

public interface BinaryJoinAlgorithm<T> {

    List<Map<String, T>> executeJoin(Query query);

}
