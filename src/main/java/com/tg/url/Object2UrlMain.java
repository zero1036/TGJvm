package com.tg.url;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

/**
 * Created by linzc on 2018/4/10.
 */
public class Object2UrlMain {
    private static Gson gson = new Gson();

    public static void main(String[] params) throws Exception {
        TestObject object = new TestObject();
        object.setName("");
        object.setId(1);
        object.setAccount(new BigDecimal(99.33).setScale(2, BigDecimal.ROUND_HALF_UP));
        List<Integer> list = new ArrayList<Integer>();
        list.add(343);
        object.setCars(list);


        List<Child> children = new ArrayList<Child>();
        Child child = new Child();
        child.setName("BB");
        child.setAge(2);
//        children.add(child);

        object.setChildren(children);


        String result = parseURLPair(object, (o) -> {

            return gson.toJson(o);
        });

        System.out.println(children.toString());
        System.out.println(list.toString());
        System.out.println(result);

    }

    public static String parseURLPair(Object o, Function<Object, String> function) throws Exception {
        Class<? extends Object> c = o.getClass();
        Field[] fields = c.getDeclaredFields();
        Map<String, Object> map = new TreeMap<String, Object>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ParseUrlIgnore.class)) {
                continue;
            }

            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(o);
            //
            if (value instanceof Collection) {
                if (((Collection) value).isEmpty()) {
                    continue;
                }

                value = function.apply(value);
            }

            if (value != null && !"".equals(value.toString())) {
                map.put(name, value);
            }
        }
        Set<Map.Entry<String, Object>> set = map.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            Map.Entry<String, Object> e = it.next();
            sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
