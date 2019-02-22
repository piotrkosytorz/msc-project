package dataimporters;

import query.Relation;
import query.Tuple;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public final class DataImporter {

    private DataImporter() {
    }

    public static Relation<Integer> importFromFile(String path) {

        Relation<Integer> relation = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().startsWith("#")) {
                    String[] stringSplit = line.split("\\s+");
                    Integer[] intSplit = Arrays.stream(stringSplit).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);

                    if (relation == null) {
                        relation = new Relation<>(stringSplit.length);
                    }

                    relation.add(new Tuple<>(intSplit));
                }
            }
            reader.close();
            return relation;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", path);
            e.printStackTrace();
            return null;
        }
    }
}
