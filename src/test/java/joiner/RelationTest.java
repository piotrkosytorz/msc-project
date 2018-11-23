package joiner;

import joiner.datastructures.Relation;
import joiner.datastructures.Tuple;
import org.junit.Test;

public class RelationTest {

    @Test(expected = Exception.class)
    public void arityExceptionTest() throws Exception {
        Relation<Integer> r = new Relation<>(3);
        r.add(new Tuple<>(1, 2, 3));
        r.add(new Tuple<>(1, 2, 3, 4));
    }
}
