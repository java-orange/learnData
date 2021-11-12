## 线程池的单例模式

```java

public class ConnectionExecutor {
    private int corePoolSize = 8;
    private int maxPoolSize = 32;

    /**
     * TODO
     * the capacity of the work queue, maxPoolSize won't take effect unless queued task is
     * larger than WorkQueueCapacity
     */
    private int WorkQueueCapacity = 10000;

    private ExecutorService executor;

    private static class ConnectionExecutorHolder {
        private static ConnectionExecutor instance = new ConnectionExecutor();
    }

    private ConnectionExecutor() {
        executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(WorkQueueCapacity));
    }

    public static ConnectionExecutor inst() {
        return ConnectionExecutorHolder.instance;
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}

```



## 慧程工具类：

### ClassUtil工具类

```java
package com.hvisions.iot.thing.dataservice.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hvisions.iot.common.utils.Json;
import com.hvisions.iot.common.utils.Timestamp;
import com.hvisions.iot.thing.dataservice.common.annotation.JSONField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
public class ClassUtil {


    public static <T> T toObject(JsonNode json, Class<T> clazz) {
        try {
            T obj = ClassUtil.createInstance(clazz);
            Field[] declaredFields = clazz.getDeclaredFields();
            HashMap<String, Field> map = new HashMap();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(JSONField.class)) {
                    String name = declaredField.getAnnotation(JSONField.class).name();
                    map.put(name, declaredField);
                } else {
                    map.put(declaredField.getName(), declaredField);
                }
            }
            Iterator<Map.Entry<String, JsonNode>> itItems = json.fields();
            while (itItems.hasNext()) {
                Map.Entry<String, JsonNode> entry = itItems.next();
                Field field;
                try {
                    field = map.get(entry.getKey());
                } catch (Exception e) {
                    log.error("There is no field {} defined in class {}, error: {}", entry.getKey(),
                            clazz.getName(), e.getMessage());
                    continue;
                }
                try {
                    Method method = clazz.getMethod("set" + StringUtils.capitalize(field.getName()),
                            field.getType());
                    method.invoke(obj, getValue(field, entry.getValue()));
                } catch (Exception e) {
                    log.error("Failed to set field {} value defined in class {}, error: {}", entry.getKey(),
                            clazz.getName(), e.getMessage());
                    continue;
                }
            }
            return obj;
        } catch (Exception e) {
            log.error("Failed to create object for class {}", clazz.getName());
            return null;
        }
    }

    /**
     * 将Json数组转换成List对象
     *
     * @param data  需要转换的Json数组
     * @param clazz 转换成的Class对象
     * @param <T>   转换成的对象类型
     * @return 对象的List
     */
    public static <T> List<T> toList(ArrayNode data, Class<T> clazz) {
        List<T> result = new LinkedList<>();

        data.forEach(json -> {
            T obj = toObject(json, clazz);
            if (obj == null) {
                log.error("Failed to convert json to class {}, json: {}", clazz.getName(), json);

                return;
            }
            result.add(obj);
        });

        return result;
    }

    private static Object getValue(Field field, JsonNode value) {
        if (value == null || value.isNull()) {
            return null;
        }

        Class<?> clazz = field.getType();

        if (clazz == Integer.class) {
            return value.asInt();
        } else if (clazz == Short.class) {
            return new Integer(value.asInt()).shortValue();
        } else if (clazz == Byte.class) {
            return new Integer(value.asInt()).byteValue();
        } else if (clazz == Long.class) {
            return value.longValue();
        } else if (clazz == Float.class) {
            return new Double(value.asDouble()).floatValue();
        } else if (clazz == Double.class) {
            return value.asDouble();
        } else if (clazz == String.class) {
            return value.asText();
        } else if (clazz == BigDecimal.class) {
            return BigDecimal.valueOf(value.asDouble());
        } else if (clazz == LocalDate.class) {
            return Timestamp.toLocalDate(value.asText());
        } else if (clazz == LocalTime.class) {
            return Timestamp.toLocalTime(value.asText());
        } else if (clazz == LocalDateTime.class) {
            return Timestamp.toLocalDatetime(value.asText());
        } else if (clazz == ZonedDateTime.class) {
            return Timestamp.toZoneDateTime(value.asText(), null);
        } else if (clazz == ObjectNode.class || clazz == ArrayNode.class) {
            return Json.toObject(value, clazz);
        } else {
            return Json.toObject(value, clazz);
        }
    }

    /**
     * 扫描指定package，得到所需要匹配的class列表
     *
     * @param scanPackage 扫描的package名称
     * @param clazz       Class对象
     * @param <T>         对象定义
     * @return 匹配的class列表
     */
    public static <T> List<Class<? extends T>> getAllClasses(String scanPackage, Class<T> clazz) {
        Reflections reflections = new Reflections(scanPackage);

        Set<Class<? extends T>> allClasses = reflections.getSubTypesOf(clazz);

        return new LinkedList<>(allClasses);
    }

    /**
     * 扫描整个工程包，得到所需要匹配的class列表
     *
     * @param clazz Class对象
     * @param <T>   对象定义
     * @return 匹配的class列表
     */
    public static <T> List<Class<? extends T>> getAllClasses(Class<T> clazz) {
        Reflections reflections = new Reflections();

        Set<Class<? extends T>> allClasses = reflections.getSubTypesOf(clazz);

        return new LinkedList<>(allClasses);
    }

    /**
     * 通过class创建类实例
     *
     * @param clazz 类class
     * @param param 构造函数的参数，如果存在多个参数的情况，统一放在一个json中进行处理
     * @param <T>   class类型
     * @return 创建的新的类对象
     * @throws Exception 创建类发生错误
     */
    public static <T> T createInstance(Class<T> clazz, JsonNode param) throws Exception {
        Constructor constructor = getConstructor(clazz);
        if (constructor == null) {
            return null;
        }

        Class<?>[] paramTypes = constructor.getParameterTypes();

        Object[] params = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            Object object = Json.toObject(param, paramTypes[i]);
            params[i] = object;
        }

        return (T) constructor.newInstance(params);
    }

    /**
     * 通过class创建类实例，类的构造函数不带参数
     *
     * @param clazz 类class
     * @param <T>   class类型
     * @return 创建的新的类对象
     * @throws Exception 创建类发生错误
     */
    public static <T> T createInstance(Class<T> clazz) throws Exception {
        // TODO find the first constructor with no parameters
        Constructor constructor = getConstructor(clazz);
        if (constructor == null) {
            return null;
        }

        Class<?>[] paramTypes = constructor.getParameterTypes();

        if (paramTypes.length > 0) {
            throw new RuntimeException("The constructor should has no parameter for class " + clazz.getName());
        }

        return (T) constructor.newInstance();
    }

    /**
     * 获取类型的构造函数
     *
     * @param clazz 类class
     * @param <T>   类定义
     * @return 构造函数
     */
    public static <T> Constructor<T> getConstructor(Class<T> clazz) {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length <= 0) {
            return null;
        }

        // FIXME, here just get the first constructor
        return constructors[0];
    }

    /**
     * 获取main所在的package名称
     *
     * @return main package名称
     */
    public static String getMainPackage() {
        try {
            StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                if ("main".equals(stackTraceElement.getMethodName())) {
                    return Class.forName(stackTraceElement.getClassName()).getPackage().getName();
                }
            }
        } catch (ClassNotFoundException ex) {
            // Swallow and continue
        }
        return null;
    }

    /**
     * 判断是否是大写字母
     *
     * @param c
     * @return boolean
     */
    public static Boolean isUp(char c) {
        return c >= 'A' && c <= 'Z';
    }

    /**
     * java对象属性转换为数据库格式，如userName-->user_name
     *
     * @param property 对象属性
     * @return String
     */
    public static String propertyToField(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer field = new StringBuffer();
        for (char c : chars) {
            if (isUp(c)) {
                field.append("_" + String.valueOf(c).toLowerCase());
            } else {
                field.append(c);
            }
        }
        return field.toString();
    }


    /**
     * 将数据库字段转换为驼峰字段，如user_name-->userName
     *
     * @param fieldName 字段名
     * @return String 字段名
     */
    public static String fieldToProperty(String fieldName) {
        if (null == fieldName) {
            return "";
        }
        char[] chars = fieldName.toCharArray();
        StringBuffer property = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    property.append(String.valueOf(chars[j]).toUpperCase());
                    i++;
                }
            } else {
                property.append(c);
            }
        }
        return property.toString();
    }

    public static <T> T toObjectByConvert(JsonNode json, Class<T> clazz) {
        try {
            T obj = ClassUtil.createInstance(clazz);
            Field[] declaredFields = clazz.getDeclaredFields();
            HashMap<String, Field> map = new HashMap();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(JSONField.class)) {
                    String name = declaredField.getAnnotation(JSONField.class).name();
                    map.put(name, declaredField);
                } else {
                    map.put(propertyToField(declaredField.getName()), declaredField);
                }
            }
            Iterator<Map.Entry<String, JsonNode>> itItems = json.fields();
            while (itItems.hasNext()) {
                Map.Entry<String, JsonNode> entry = itItems.next();
                Field field;
                try {
                    field = map.get(entry.getKey());
                } catch (Exception e) {
                    log.error("There is no field {} defined in class {}, error: {}", entry.getKey(),
                            clazz.getName(), e.getMessage());
                    return null;
                }
                try {
                    Method method = clazz.getMethod("set" + StringUtils.capitalize(field.getName()),
                            field.getType());
                    method.invoke(obj, getValue(field, entry.getValue()));
                } catch (Exception e) {
                    log.error("Failed to set field {} value defined in class {}, error: {}", entry.getKey(),
                            clazz.getName(), e.getMessage());
                    return null;
                }
            }
            return obj;
        } catch (Exception e) {
            log.error("Failed to create object for class {}", clazz.getName());
            return null;
        }
    }

    /**
     * 将Json数组转换成List对象
     *
     * @param data  需要转换的Json数组
     * @param clazz 转换成的Class对象
     * @param <T>   转换成的对象类型
     * @return 对象的List
     */
    public static <T> List<T> toListByConvert(ArrayNode data, Class<T> clazz) {
        List<T> result = new LinkedList<>();

        data.forEach(json -> {
            T obj = toObjectByConvert(json, clazz);
            if (obj == null) {
                log.error("Failed to convert json to class {}, json: {}", clazz.getName(), json);

                return;
            }
            result.add(obj);
        });

        return result;
    }

}

```



### Json 工具处理

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.hvisions.iot.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Json {
    private static final Logger log = LoggerFactory.getLogger(Json.class);
    private static final char KeyNested = '.';
    private static final String JsonNullValue = "null";

    public Json() {
    }

    public static boolean has(JsonNode json, String key) {
        return json.has(key) && !json.get(key).isNull() && !"null".equals(json.get(key).asText());
    }

    public static boolean isJson(String text) {
        boolean valid = false;

        try {
            (new ObjectMapper()).readTree(text);
            valid = true;
        } catch (Exception var3) {
        }

        return valid;
    }

    public static boolean isNull(JsonNode json) {
        if (json == null) {
            return true;
        } else if (json.isNull()) {
            return true;
        } else if (json.isTextual() && "null".equals(json.asText())) {
            return true;
        } else if (!json.isObject()) {
            return false;
        } else {
            boolean nullable = true;
            Iterator itFields = json.fields();

            while(itFields.hasNext()) {
                Entry<String, JsonNode> entry = (Entry)itFields.next();
                if (!isNull((JsonNode)entry.getValue())) {
                    nullable = false;
                    break;
                }
            }

            return nullable;
        }
    }

    public static Object getValue(JsonNode json) {
        if (json == null) {
            return null;
        } else if (json.isBoolean()) {
            return json.asBoolean();
        } else if (json.isShort()) {
            return (new Integer(json.asInt())).shortValue();
        } else if (json.isInt()) {
            return json.asInt();
        } else if (json.isLong()) {
            return json.asLong();
        } else if (json.isBigInteger()) {
            return json.asLong();
        } else if (json.isLong()) {
            return json.asLong();
        } else if (!json.isFloat() && !json.isDouble()) {
            if (json.isNumber()) {
                return new BigInteger(json.asText());
            } else {
                return json.isTextual() ? json.asText() : json;
            }
        } else {
            return json.asDouble();
        }
    }

    public static String getString(JsonNode json, String key) {
        if (!has(json, key)) {
            return null;
        } else {
            JsonNode value = get(json, key);
            return value != null ? value.asText() : null;
        }
    }

    public static Integer getInt(JsonNode json, String key) {
        if (!has(json, key)) {
            return null;
        } else {
            JsonNode value = get(json, key);
            return value != null ? value.asInt() : null;
        }
    }

    public static JsonNode get(JsonNode json, String name) {
        if (name.indexOf(46) == -1) {
            return json.get(name);
        } else {
            String[] names = StringUtils.split(name, '.');
            JsonNode result = json;
            String[] var4 = names;
            int var5 = names.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String key = var4[var6];
                result = json.path(key);
            }

            return result.isMissingNode() ? null : result;
        }
    }

    public static void nestedPut(ObjectNode json, String name, Object value) {
        if (value != null) {
            if (value instanceof JsonNode) {
                put(json, name, (JsonNode)value);
            } else if (name.indexOf(46) == -1) {
                put(json, name, value);
            } else {
                String[] names = StringUtils.split(name, '.');
                ObjectNode node = (new ObjectMapper()).createObjectNode();
                json.set(name, node);

                for(int i = 1; i < names.length; ++i) {
                    if (i == names.length - 1) {
                        put(node, names[i], value);
                    } else {
                        ObjectNode nodeAdd = (new ObjectMapper()).createObjectNode();
                        node.set(name, nodeAdd);
                        node = nodeAdd;
                    }
                }

            }
        }
    }

    public static void put(ObjectNode json, String name, JsonNode jsonNode) {
        if (name.indexOf(46) != -1) {
            String[] names = StringUtils.split(name, '.');
            ObjectNode node = json;

            for(int i = 0; i < names.length; ++i) {
                if (i == names.length - 1) {
                    node.set(names[i], jsonNode);
                } else if (!node.has(names[i])) {
                    ObjectNode on = (new ObjectMapper()).createObjectNode();
                    node.set(names[i], on);
                    node = on;
                } else {
                    node = (ObjectNode)node.get(names[i]);
                }
            }

        } else {
            if (json.has(name) && jsonNode.isObject()) {
                merge((ObjectNode)json.get(name), (ObjectNode)jsonNode);
            } else {
                json.set(name, jsonNode);
            }

        }
    }

    public static void put(ObjectNode json, String name, Object value) {
        if (value == null) {
            json.set(name, NullNode.instance);
        } else if (value.getClass().isArray()) {
            ArrayNode array = (ArrayNode)(new ObjectMapper()).valueToTree(value);
            json.set(name, array);
        } else {
            if (value instanceof List) {
                json.set(name, (new ObjectMapper()).valueToTree(value));
            }

            if (value instanceof Byte) {
                json.put(name, (short)(Byte)value);
            } else if (value instanceof Short) {
                json.put(name, (Short)value);
            } else if (value instanceof Integer) {
                json.put(name, (Integer)value);
            } else if (value instanceof Long) {
                json.put(name, (Long)value);
            } else if (value instanceof String) {
                json.put(name, (String)value);
            } else if (value instanceof Boolean) {
                json.put(name, (Boolean)value);
            } else if (value instanceof Double) {
                json.put(name, (Double)value);
            } else if (value instanceof Float) {
                json.put(name, (Float)value);
            } else if (value instanceof BigDecimal) {
                json.put(name, value.toString());
            } else if (value instanceof LocalDate) {
                json.put(name, value.toString());
            } else if (value instanceof LocalTime) {
                json.put(name, value.toString());
            } else if (value instanceof LocalDateTime) {
                json.put(name, value.toString());
            } else if (value instanceof ObjectNode) {
                if (json.has(name)) {
                    merge((ObjectNode)json.get(name), (ObjectNode)value);
                } else {
                    json.set(name, (ObjectNode)value);
                }
            } else {
                json.put(name, value.toString());
            }

        }
    }

    public static void add(ArrayNode array, Object value) {
        if (value == null) {
            array.add(NullNode.instance);
        } else {
            if (value instanceof Byte) {
                array.add((Byte)value);
            } else if (value instanceof Short) {
                array.add((Short)value);
            } else if (value instanceof Integer) {
                array.add((Integer)value);
            } else if (value instanceof Long) {
                array.add((Long)value);
            } else if (value instanceof String) {
                array.add((String)value);
            } else if (value instanceof Boolean) {
                array.add((Boolean)value);
            } else if (value instanceof Double) {
                array.add((Double)value);
            } else if (value instanceof Float) {
                array.add((Float)value);
            } else if (value instanceof BigDecimal) {
                array.add(value.toString());
            } else if (value instanceof LocalDate) {
                array.add(value.toString());
            } else if (value instanceof LocalTime) {
                array.add(value.toString());
            } else if (value instanceof LocalDateTime) {
                array.add(value.toString());
            } else if (value instanceof ObjectNode) {
                array.add((ObjectNode)value);
            } else {
                log.warn("Unknown data type of {} for appending to json, just add it as string", value.getClass());
                array.add(value.toString());
            }

        }
    }

    public static JsonNode merge(ObjectNode base, ObjectNode merged) {
        base.setAll(merged);
        return base;
    }

    public static JsonNode filterNull(JsonNode json) {
        if (!(json instanceof ObjectNode)) {
            return json;
        } else {
            Iterator itFields = json.fields();

            while(itFields.hasNext()) {
                Entry<String, JsonNode> entry = (Entry)itFields.next();
                if (isNull((JsonNode)entry.getValue())) {
                    itFields.remove();
                }
            }

            return json;
        }
    }

    public static Map<String, Object> toMap(JsonNode jsonNode) {
        return (Map)getMapper().convertValue(jsonNode, new TypeReference<Map<String, Object>>() {
        });
    }

    public static <T> Map<String, T> toMapData(JsonNode jsonNode) {
        return (Map)getMapper().convertValue(jsonNode, new TypeReference<Map<String, T>>() {
        });
    }

    public static <T> T toObject(Map<?, ?> map, Class<T> clazz) {
        return getMapper().convertValue(map, clazz);
    }

    public static <T> T toObject(Object object, Class<T> clazz) {
        try {
            return toObject(toJsonNode(object), clazz);
        } catch (Exception var3) {
            log.error(var3.getMessage(), var3);
            return null;
        }
    }

    public static <T> List<T> toList(ArrayNode arrayNode) {
        ObjectMapper mapper = getMapper();

        try {
            List<T> result = (List)mapper.readValue(arrayNode.toString(), new TypeReference<List<T>>() {
            });
            return result;
        } catch (JsonProcessingException var3) {
            log.error("Failed to convert json array to list object: {}", arrayNode);
            return null;
        }
    }

    public static List<Object> toList(ArrayNode arrayNode, Class<?> clazz) {
        List<Object> result = new LinkedList();
        arrayNode.forEach((jsonNode) -> {
            result.add(toObject(jsonNode, clazz));
        });
        return result;
    }

    public static <T> List<T> toList(List<?> listData, Class<T> clazz) {
        List<T> result = new LinkedList();
        Iterator var3 = listData.iterator();

        while(var3.hasNext()) {
            Object source = var3.next();
            result.add(toObject(source, clazz));
        }

        return result;
    }

    public static <T> T toObject(JsonNode jsonNode, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(getEnumModule());
        mapper.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat());
        if (jsonNode.isArray() && !clazz.isArray() && List.class.isAssignableFrom(clazz)) {
            return null;
        } else {
            try {
                return mapper.treeToValue(jsonNode, clazz);
            } catch (JsonProcessingException var4) {
                log.error("json to object failed: {}", jsonNode.toString(), var4);
                return null;
            }
        }
    }

    public static <T> T toObject(JsonNode jsonNode, Class<T> clazz, List<String> ignoredFields) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            public boolean hasIgnoreMarker(final AnnotatedMember m) {
                return ignoredFields.contains(m.getName()) || super.hasIgnoreMarker(m);
            }
        });
        mapper.registerModule(getEnumModule());

        try {
            return mapper.treeToValue(jsonNode, clazz);
        } catch (JsonProcessingException var5) {
            log.error("json to object failed: {}", jsonNode.toString(), var5);
            return null;
        }
    }

    private static SimpleModule getEnumModule() {
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new BeanDeserializerModifier() {
            public JsonDeserializer<Enum> modifyEnumDeserializer(DeserializationConfig config, final JavaType type, BeanDescription beanDesc, final JsonDeserializer<?> deserializer) {
                return new JsonDeserializer<Enum>() {
                    public Enum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
                        Class rawClass = type.getRawClass();

                        try {
                            return Enum.valueOf(rawClass, jp.getValueAsString());
                        } catch (Exception var5) {
                            return Enum.valueOf(rawClass, jp.getValueAsString().toUpperCase());
                        }
                    }
                };
            }
        });
        module.addSerializer(Enum.class, new StdSerializer<Enum>(Enum.class) {
            public void serialize(Enum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeString(value.name().toLowerCase());
            }
        });
        return module;
    }

    public static JsonNode toJsonNode(byte[] data) {
        try {
            return getMapper().readTree(data);
        } catch (IOException var2) {
            log.error(var2.getMessage(), var2);
            return null;
        }
    }

    public static JsonNode toJsonNode(String data) {
        return toJsonNode(data.getBytes());
    }

    public static JsonNode toJsonNode(Object object) {
        ObjectMapper mapper = getMapper();
        return (JsonNode)mapper.convertValue(object, JsonNode.class);
    }

    public static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(getEnumModule()).registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
        mapper.setDateFormat(new StdDateFormat());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    public static String toString(Object object) {
        try {
            return getMapper().writeValueAsString(object);
        } catch (JsonProcessingException var2) {
            log.error(var2.getMessage(), var2);
            return "";
        }
    }

    public static void writeToFile(Object object, String filename) throws IOException {
        writeToFile(toJsonNode(object), filename);
    }

    public static void writeToFile(Map<?, ?> map, String fileName) throws IOException {
        writeToFile(toJsonNode((Object)map), fileName);
    }

    public static void writeToFile(JsonNode json, String filename) throws IOException {
        ObjectMapper mapper = getMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(filename), filterNull(json));
    }

    public static String toPrettyString(Object object) {
        try {
            return getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException var2) {
            log.error(var2.getMessage(), var2);
            return "";
        }
    }

    public static JsonNode empty() {
        return (new ObjectMapper()).createObjectNode();
    }
}

```



### TimeStmp 时间处理

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.hvisions.iot.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Timestamp {
    private static DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS[XXX]");

    public Timestamp() {
    }

    public static String current() {
        return ZonedDateTime.now().toOffsetDateTime().format(FMT);
    }

    public static LocalDateTime currentOfLocal() {
        return LocalDateTime.now();
    }

    public static ZonedDateTime currentWithZone() {
        return ZonedDateTime.now();
    }

    public static LocalDate toLocalDate(String date) {
        String validDate = date;
        if (date.length() > 10) {
            validDate = date.substring(0, 10);
        }

        return LocalDate.parse(validDate, DateTimeFormatter.ISO_DATE);
    }

    public static LocalTime toLocalTime(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
    }

    public static LocalDateTime toLocalDatetime(String datetime) {
        ZonedDateTime zonedDateTime = toZoneDateTime(datetime);
        return zonedDateTime.toLocalDateTime();
    }

    public static ZonedDateTime changeZone(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        return zonedDateTime.withZoneSameInstant(zoneId);
    }

    public static ZonedDateTime toZoneDateTime(String datetime, DateTimeFormatter fmt) {
        return fmt == null ? ZonedDateTime.parse(datetime) : ZonedDateTime.parse(datetime, fmt);
    }

    public static ZonedDateTime toZoneDateTime(String datetime) {
        return ZonedDateTime.parse(datetime);
    }

    public static String toString(ZonedDateTime zonedDateTime, DateTimeFormatter fmt) {
        return fmt == null ? zonedDateTime.format(FMT) : zonedDateTime.format(fmt);
    }

    public static String toString(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(FMT);
    }

    public static Boolean isSameDate(LocalDateTime dateTimeFirst, LocalDateTime dateTimeSecond) {
        return dateTimeFirst != null && dateTimeSecond != null ? LocalDate.from(dateTimeFirst).equals(LocalDate.from(dateTimeSecond)) : false;
    }
}

```



### @JsonField

```java
package com.hvisions.iot.thing.dataservice.common.annotation;

import java.lang.annotation.*;


/**
 * @author czh
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONField {
    String name();
}


```



##    加载Jar包

```java
package com.hvisions.iot.drivers.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <p>Title: SimulatorExample</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/11/2</p>
 *
 * @author : xhjing
 * @version :1.0.0
 */
public class JarLoader {
    private static final Logger log = LoggerFactory.getLogger(JarLoader.class);

    public static void main(String[] args) throws Exception{
        Map<String, Class<?>> stringClassMap = loadAllJarFromAbsolute("C:\\Users\\H-VISIONS\\Desktop\\aaa\\jar\\iot-extension-1.0.1.jar");
        Set<String> strings = stringClassMap.keySet();
        for (String string : strings) {
            System.out.println(stringClassMap.get(string));
        }
    }
 
    public JarLoader() {
    }

    public static void loadJar(String directoryPath) {
        log.info(" ================================================");
        log.info("Load Jar package folder path: {}", directoryPath);
        log.info(" ================================================");
        log.info("");
        File directory = new File(directoryPath);
        // 判断是否为文件夹，如果是文件,直接用单个jar解析的方法去解析
        try {
            if (!directory.isDirectory()) {
                // 添加jar扫描路径
                addUrl(directory);
            }
            File[] jars = directory.listFiles();
            if (jars != null && jars.length > 0) {
                for (File file : jars) {
                    String fPath = file.getPath();
                    // 只加载jar
                    if (fPath.endsWith(".jar")) {
                        addUrl(file);
                    }
                }
            }
            log.info("");
            log.info(" ================================================");
            log.info("Jar package is loaded. EveryThing is OK !");
            log.info(" ================================================");
        } catch (Exception e) {
            log.error("Error loading jar package，{}", e);
        }
    }

    /**
     * 功能描述: 扫描一个文件夹下面的所有jar，不包含子文件夹和子jar
     *
     * @param directoryPath
     * @return:java.util.Map<java.lang.String,java.lang.Class<?>>
     */
    public static Map<String, Class<?>> loadAllJarFromAbsolute(String directoryPath) throws
            NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        log.info("loadAllJarFromAbsolute. directoryPath: {}", directoryPath);
        File directory = new File(directoryPath);
        // 判断是否为文件夹，如果是文件,直接用单个jar解析的方法去解析
        if (!directory.isDirectory()) {
            // 添加jar扫描路径
            addUrl(directory);
            return loadJarFromAbsolute(directoryPath);
        }
        // 如果是文件夹，则需要循环加载当前文件夹下面的所有jar
        Map<String, Class<?>> clazzMap = new HashMap<>(16);
        File[] jars = directory.listFiles();
        if (jars != null && jars.length > 0) {
            List<String> jarPath = new LinkedList<>();
            for (File file : jars) {
                String fPath = file.getPath();
                // 只加载jar
                if (fPath.endsWith(".jar")) {
                    addUrl(file);
                    jarPath.add(fPath);
                }
            }
            if (jarPath.size() > 0) {
                for (String path : jarPath) {
                    clazzMap.putAll(loadJarFromAbsolute(path));
                }
            }
        }
        return clazzMap;
    }
 
    /**
     * 功能描述: 从绝对路径中加载jar包中的类
     * 扫描指定jar包前需要将这个jar的地址加入了系统类加载器的扫描列表中
     * 注意，这里只支持单个jar的加载，如果这个jar还引入了其他jar依赖，会加载失败
     * 所以只能用来加载对其他jar包没有依赖的简单对象类信息
     *
     * @param path jar包路径加载地址
     * @return:java.util.Map<java.lang.String,java.lang.Class<?>>
     */
    public static Map<String, Class<?>> loadJarFromAbsolute(String path) throws IOException {
        log.info("loadJarFromAbsolute. path: {}", path);
        JarFile jar = new JarFile(path);
        Enumeration<JarEntry> entryEnumeration = jar.entries();
        Map<String, Class<?>> clazzMap = new HashMap<>(16);
        while (entryEnumeration.hasMoreElements()) {
            JarEntry entry = entryEnumeration.nextElement();
            // 先获取类的名称，符合条件之后再做处理，避免处理不符合条件的类
            String clazzName = entry.getName();
            if (clazzName.endsWith(".class")) {
                // 去掉文件名的后缀
                clazzName = clazzName.substring(0, clazzName.length() - 6);
                // 替换分隔符
                clazzName = clazzName.replace("/", ".");
                // 加载类,如果失败直接跳过
                try {
                    Class<?> clazz = Class.forName(clazzName);
                    // 将类名称作为键，类Class对象作为值存入mao
                    // 因为类名存在重复的可能，所以这里的类名是带包名的
                    clazzMap.put(clazzName, clazz);
                } catch (Throwable e) {
                    // 这里可能出现有些类是依赖不全的，直接跳过，不做处理，也没法做处理
                }
            }
        }
        return clazzMap;
    }
 
 
    /**
     * 功能描述: 添加需要扫描的jar包
     *
     * @param jarPath
     * @return:void
     */
    public static void addUrl(File jarPath) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, MalformedURLException {
        log.info("addUrl. jarPath: {}", jarPath);
        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        // 反射获取类加载器中的addURL方法，并将需要加载类的jar路径
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        URL url = jarPath.toURI().toURL();
        // 把当前jar的路径加入到类加载器需要扫描的路径
        method.invoke(classLoader, url);
    }
 
}
```





## 关于Static静态变量什么时候初始化

例子：

```java
package javaStatic;

public class OneObj {

    public OneObj() {
        System.out.println("init...");
    }
}
```



```java
package javaStatic;

public class TwoObj {
    private static OneObj oneObj = new OneObj();

    public TwoObj() {
        System.out.println("TwoObj.TwoObj");
    }
}
```



```java
package javaStatic;

public class StaticTest {
    

    public static void main(String[] args) {
        System.out.println("args = " + args);

        TwoObj twoObj = new TwoObj();

        System.out.println("StaticTest.main");

    }
}
```

执行结果：

```java
args = [Ljava.lang.String;@7f31245a
init...
TwoObj.TwoObj
StaticTest.main
```



观看上面3段代码逻辑。

使用StaticTest main 方法进行程序启动， 当程序启动成功后，先运行  **System.out.println("args = " + args);**

然后当用到TwoObj 时， 进行类加载，执行 private static OneObj oneObj = new OneObj();  静态变量初始化。

打印：   **System.out.println("init...");**  

然后进行构造函数的调用：     public TwoObj() ,  打印：  **System.out.println("TwoObj.TwoObj");**  

之后打印：  **System.out.println("StaticTest.main");**  

搞定。



因此， static静态变量并非项目启动便进行初始化，而是在用到的时候，在构造方法执行之前才进行初始化，不用不加载。
