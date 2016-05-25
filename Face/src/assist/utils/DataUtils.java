package assist.utils;

import java.lang.reflect.Array;

import java.util.List;

public class DataUtils {
    public static String[] concatStringArray(String[] a, String[] b) {
        String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static Object[] concatObjectArray(Object[] oa1, Object[] oa2) { //数组链接
        Object[] res = new Object[oa1.length + oa2.length];
        for (int i = 0; i < oa1.length; i++) {
            res[i] = oa1[i];
        }
        int oa1len = oa1.length;
        for (int i = 0; i < oa2.length; i++) {
            res[oa1len + i] = oa2[i];
        }
        return res;
    }

    public static String upperFirst(String attr) { //第一个字母大写
        String res = attr.substring(0, 1).toUpperCase() + attr.substring(1);
        return res;
    }
    //List<T>转换为Array<T>
    public static <T> T[] listToArray(Class<T> c, List<T> source) {
        T[] keyArray = (T[]) Array.newInstance(c, source.size());
        for (int i = 0; i < source.size(); i++) {
            keyArray[i] = source.get(i);
        }
        return keyArray;
    }

    public static String repeatBlank(int i) {
        String res = " ";
        for (int j = 0; j < i; j++) {
            res = res + " ";
        }
        return res;
    }
}
