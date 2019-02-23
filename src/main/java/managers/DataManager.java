package managers;

import query.Relation;
import query.Tuple;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DataManager {

    private DataManager() {
    }

    public static List<String[]> importFromFile(String path) {

        List<String[]> data = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().startsWith("#")) {
                    String[] strArr = line.split("\\s+");
                    data.add(strArr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return data;
    }

    public static Relation<String> convertToStringRelation(List<String[]> strings) throws Exception {
        Relation<String> result = null;
        for (String[] elements : strings) {
            Tuple<String> stringTuple = new Tuple<>(elements);
            if (result == null) {
                result = new Relation<>(stringTuple.size());
            }
            result.add(stringTuple);
        }
        return result;
    }

    public static Relation<Integer> convertToIntegerRelation(List<String[]> strings) throws Exception {
        Relation<Integer> result = null;
        for (String[] elements : strings) {
            Tuple<Integer> integerTupleTuple = new Tuple<>(Arrays.stream(elements).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new));
            if (result == null) {
                result = new Relation<>(integerTupleTuple.size());
            }
            result.add(integerTupleTuple);
        }
        return result;
    }
}
