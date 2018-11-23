package joiner;

import joiner.datastructures.Tuple;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TupleTest {

    @Test
    public void integerTupleToStringTest() {
        Tuple<Integer> integerTuple = new Tuple<>(Arrays.asList(1, 2, 3, 3, 5, 14, 4));
        assertEquals("[1,2,3,3,5,14,4]", integerTuple.toString());
    }

    @Test
    public void stringTupleToStringTest() {
        Tuple<String> integerTuple = new Tuple<>(Arrays.asList("one", "two", "three"));
        assertEquals("[one,two,three]", integerTuple.toString());
    }
}
