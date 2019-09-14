package helpers;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public final class SetOperationsOnStrArraysHelper {

    public static String[] getIntersecttionOfStrArray(String[] a, String[] b) {

        Set<String> bSet = new HashSet<>(Arrays.asList(b));
        List<String> sharedStrings = new ArrayList<>();

        for (String str : a) {
            if (bSet.contains(str)) {
                sharedStrings.add(str);
            }
        }

        return sharedStrings.size() > 0 ? sharedStrings.toArray(new String[0]) : null;
    }

    public static String[] getSymmetricDifferenceOfStrArrays(String[] a, String[] b) {

        Set<String> aSet = new HashSet<>(Arrays.asList(a));
        Set<String> bSet = new HashSet<>(Arrays.asList(b));
        return CollectionUtils.disjunction(aSet, bSet).toArray(new String[0]);
    }

    public static String[] getUnionOfStrArrays(String[] a, String[] b) {

        Set<String> aSet = new HashSet<>(Arrays.asList(a));
        Set<String> bSet = new HashSet<>(Arrays.asList(b));
        return CollectionUtils.union(aSet, bSet).toArray(new String[0]);
    }

    public static boolean sharesAnElement(String[] a, String[] b) {
        Set<String> bSet = new HashSet<>(Arrays.asList(b));

        for (String str : a) {
            if (bSet.contains(str)) {
                return true;
            }
        }

        return false;
    }
}
