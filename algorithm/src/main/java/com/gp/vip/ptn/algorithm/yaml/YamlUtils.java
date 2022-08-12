package com.gp.vip.ptn.algorithm.yaml;

import org.yaml.snakeyaml.Yaml;

import java.util.*;

/**
 * @author: Fred
 * @date: 2022/8/12 15:26
 * @description: (类的描述)
 */
public class YamlUtils {

    private static final Map<String, String> cacheYamlMap = new HashMap<>();

    public enum ConstantEnum {
        ADD("add"),

        REMOVE("remove");

        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        ConstantEnum(String code) {
            this.code = code;
        }

        public static ConstantEnum getInstance(String code) {
            for (ConstantEnum constantEnum : values()) {
                if (constantEnum.getCode().equals(code)) {
                    return constantEnum;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        //加两个attr节点下属性，删addd属性
        String newContent = "nacos:\n" +
                "  dg: 889990\n" +
                "  addd: 444\n" +
                "spring:\n" +
                "  redis:\n" +
                "    path: 3344\n";
        String oldContent = "nacos:\n" +
                "  dg: 889333333990\n" +
                "spring:\n" +
                "  redis:\n" +
                "    path: 8899\n" +
                "    attr:\n" +
                "      name: 3333\n" +
                "      value: 4444";

        System.out.println(oldContent);
        System.out.println(newContent);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
        Yaml yaml = new Yaml();
        //以就模板为基准模板，对比新模板，筛选出新增的属性
        List<Map<String, String>> addAttr = compareAndGetDiffAttr(oldContent, newContent, ConstantEnum.ADD);
        //以旧模板为基准模板，对比新模板，筛选删除属性
        List<String> removeAttr = compareAndGetDiffAttr(oldContent, newContent, ConstantEnum.REMOVE);

        //将筛选出的属性集合，转化为linkMap格式
        Map<String, Object> addMap = (Map<String, Object>) transMapListToLinkMap(addAttr);
        Map<String, Object> removeMap = (Map<String, Object>) transListToLinkMap(removeAttr);

        //以旧模板值为标准值，除了增加的属性值，其他均为旧模板值
        Map originMap = yaml.loadAs(oldContent, Map.class);
        //对旧模板进行新增，删除属性操作
        //addAndRemoveYaml(originMap,removeMap,addMap);
        addYamlMap(originMap, addMap);
        String dump = yaml.dumpAsMap(originMap);
        System.out.println(dump);
    }

    /**
     * trans key list to map
     * demo: spring.redis.host to {spring={redis={host=}}}
     *
     * @param yamlKey
     * @return
     */
    public static Object transListToLinkMap(List<String> yamlKey) {
        Map<String, Object> linkMap = new LinkedHashMap();
        for (String val : yamlKey) {
            Map<String, Object> map = (Map<String, Object>) transStringToLinkMap(val, null);
            addYamlMap(linkMap, map);
        }

        return linkMap;
    }

    public static Object transMapListToLinkMap(List<Map<String, String>> yamlKey) {
        Map<String, Object> linkMap = new LinkedHashMap();
        for (Map<String, String> val : yamlKey) {
            for (Map.Entry<String, String> value : val.entrySet()) {
                Map<String, Object> map = (Map<String, Object>) transStringToLinkMap(value.getKey(), value.getValue());
                addYamlMap(linkMap, map);
            }
        }

        return linkMap;
    }

    public static void addAndRemoveYaml(Map<String, Object> originMap, Map<String, Object> removeMap, Map<String, Object> addMap) {
        removeYamlMap(originMap, removeMap);
        addYamlMap(originMap, addMap);
    }

    public static boolean removeYamlMap(Map<String, Object> originMap, Map<String, Object> removeMap) {
        for (Map.Entry<String, Object> val : removeMap.entrySet()) {
            for (Map.Entry<String, Object> obj : originMap.entrySet()) {
                if (val.getKey().equals(obj.getKey())) {
                    try {
                        Map<String, Object> objVal = (Map<String, Object>) obj.getValue();
                        Map<String, Object> addVal = (Map<String, Object>) val.getValue();
                        if (removeYamlMap(objVal, addVal)) {
                            originMap.remove(val.getKey());
                            break;
                        }
                    } catch (Exception e) {
                        originMap.remove(val.getKey());
                        break;
                    }
                }
            }
        }
        if (originMap == null || originMap.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * {spring={redis={host=}}} | {spring={rabbit={host=}}} merge to {spring={redis={host=},rabbit={host=}}}
     * <p>
     * Combine the addMap collection, do not allow the same key value to be overwritten
     *
     * @param originMap
     * @param addMap
     */
    public static void addYamlMap(Map<String, Object> originMap, Map<String, Object> addMap) {
        //遍历新旧两个嵌套集合，将值，属性进行合并
        for (Map.Entry<String, Object> val : addMap.entrySet()) {
            boolean common = false;
            for (Map.Entry<String, Object> obj : originMap.entrySet()) {
                if (val.getKey().equals(obj.getKey())) {
                    common = true;
                    try {
                        Map<String, Object> objVal = (Map<String, Object>) obj.getValue();
                        Map<String, Object> addVal = (Map<String, Object>) val.getValue();
                        addYamlMap(objVal, addVal);
                    } catch (Exception e) {
                        break;
                    }
                    break;
                }
            }
            if (!common) {
                originMap.put(val.getKey(), val.getValue());
            }
        }
    }

    /**
     * spring.redis.host to {spring={redis={host=}}}
     * <p>
     * trans string to map，Parse the yaml string
     *
     * @param val
     * @return
     */
    private static Object transStringToLinkMap(String yamlKey, String val) {
        int i = yamlKey.indexOf(".");
        if (i < 0) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(yamlKey, val);
            return map;
        }

        String key = yamlKey.substring(0, i);
        String value = yamlKey.substring(i + 1);
        Map<String, Object> linkedHashMap = (Map<String, Object>) transStringToLinkMap(value, val);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, linkedHashMap);
        return map;
    }

    /**
     * compare yaml content get diff key list
     *
     * @param oldContent
     * @param newContent
     * @return
     */
    public static List compareAndGetDiffAttr(String oldContent, String newContent, ConstantEnum type) {
        Yaml yaml = new Yaml();
        //获取旧模板的key集合
        Iterable<Object> oldYaml = yaml.loadAll(oldContent);
        List<String> oldKey = analyzeYamlToList(oldYaml);

        Iterable<Object> newYaml = yaml.loadAll(newContent);

        //新增比较特殊，需要保留原值，
        if (ConstantEnum.ADD.equals(type)) {
            //获取新模板的Key value集合
            List<Map<String, String>> list = analyzeYamlToListMap(newYaml);
            Iterator<Map<String, String>> iterator = list.iterator();

            //遍历，与旧模板进行对比，删除掉相同key的元素，留下的就是新增的
            while (iterator.hasNext()) {
                //以新模板为基准对比
                for (Map.Entry<String, String> value : iterator.next().entrySet()) {
                    for (String key : oldKey) {
                        if (key.equals(value.getKey())) {
                            iterator.remove();
                            break;
                        }
                    }
                }
            }

            return list;
        } else {
            //旧模板与新模板进行取差集，得到旧模板与新模板的差异属性，即需要删除的属性
            List<String> recent = analyzeYamlToList(newYaml);
            //以旧模板为基准
            List<String> compareList = new ArrayList<>(oldKey);
            compareList.removeAll(recent);
            return compareList;
        }
    }

    public static List<String> analyzeYamlToList(Iterable<Object> newYaml) {
        //将yaml遍历器中的嵌套集合转化为单个KEY VALUE对应的Map集合，存放至静态变量
        for (Iterator<Object> it = newYaml.iterator(); it.hasNext(); ) {
            Map<String, Object> object = (Map<String, Object>) it.next();
            parseYamlToMap(object, null);
        }

        //将上一个步骤存放的集Map集合提取成List集合，提取单个Key值
        List<String> keyList = new ArrayList<>();
        cacheYamlMap.forEach((key, val) -> {
            keyList.add(key);
        });

        cacheYamlMap.clear();
        return keyList;
    }

    public static List<Map<String, String>> analyzeYamlToListMap(Iterable<Object> newYaml) {
        //将yaml遍历器中的嵌套集合转化为单个KEY VALUE对应的Map集合，存放至静态变量
        for (Iterator<Object> it = newYaml.iterator(); it.hasNext(); ) {
            Map<String, Object> object = (Map<String, Object>) it.next();
            parseYamlToMap(object, null);
        }
        //将上一个步骤存放的集Map集合提取成List集合，并且拆分为单个Map，放入集合（便于后续便利）
        List<Map<String, String>> keyList = new ArrayList<>();
        cacheYamlMap.forEach((key, val) -> {
            Map<String, String> map = new HashMap<>();
            map.put(key, val);
            keyList.add(map);
        });

        cacheYamlMap.clear();
        return keyList;
    }

    /**
     * {spring={redis={host=},rabbit={host=}}} to {spring.redis.host= ,spring.rabbit.host=}
     * <p>
     * trans yaml map to key map
     *
     * @param item
     * @param key
     */
    public static void parseYamlToMap(Map<String, Object> item, String key) {
        //将嵌套Map集合，转化为单个Key value对应的属性集合
        item.forEach((k, v) -> {
            if (Objects.isNull(v)) {
                if (key == null) {
                    cacheYamlMap.put(k, "");
                } else {
                    cacheYamlMap.put(key.concat(".").concat(k), v.toString());
                }
            } else if (v instanceof LinkedHashMap) {
                if (key == null) {
                    parseYamlToMap((Map<String, Object>) v, k);
                } else {
                    parseYamlToMap((Map<String, Object>) v, key.concat(".").concat(k));
                }
            } else if (key == null) {
                cacheYamlMap.put(k, v.toString());
            } else {
                cacheYamlMap.put(key.concat(".").concat(k), v.toString());
            }
        });
    }


}

