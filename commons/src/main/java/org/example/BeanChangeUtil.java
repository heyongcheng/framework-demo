package org.example;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author heyc
 * @version 1.0
 * @date 2023/5/12 15:33
 */
public class BeanChangeUtil {

    public static String contrast(Object obj1, Object obj2) {
        if (Objects.equals(obj1, obj2)) {
            return "";
        }
        try {
            if (obj1 == null || obj2 == null || obj1.getClass() != obj2.getClass()) {
                return contrast_1(obj1, obj2);
            }
            return contrast_2(obj1, obj2);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static String contrast_1(Object obj1, Object obj2) throws IntrospectionException {
        StringBuilder sb = new StringBuilder();
        if (obj1 != null) {
            sb.append(getBeanDesc(obj1));
        }
        sb.append(" => ");
        if (obj2 != null) {
            sb.append(getBeanDesc(obj2));
        }
        return sb.toString();
    }

    private static String contrast_2(Object obj1, Object obj2) throws IntrospectionException {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(obj1.getClass())
            .getPropertyDescriptors();
        Arrays.stream(propertyDescriptors).forEach(propertyDescriptor -> {
            try {
                if (!FieldResolver.isIgnore(obj1.getClass(), propertyDescriptor.getName())) {
                    Object value1 = propertyDescriptor.getReadMethod().invoke(obj1);
                    Object value2 = propertyDescriptor.getReadMethod().invoke(obj2);
                    if (!Objects.equals(value1, value2)) {
                        String name = FieldResolver
                            .getName(obj1.getClass(), propertyDescriptor.getName());
                        if (sb1.length() > 0) {
                            sb1.append(", ");
                            sb2.append(", ");
                        }
                        sb1.append(name);
                        sb1.append(":");
                        sb1.append(value1);

                        sb2.append(name);
                        sb2.append(":");
                        sb2.append(value2);
                    }
                }
            } catch (Exception e) {
            }
        });
        return sb1 + " => " + sb2;
    }

    private static String getBeanDesc(Object object) throws IntrospectionException {
        StringBuilder sb = new StringBuilder();
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(object.getClass())
            .getPropertyDescriptors();
        Arrays.stream(propertyDescriptors).forEach(propertyDescriptor -> {
            try {
                if (!FieldResolver.isIgnore(object.getClass(), propertyDescriptor.getName())) {
                    String name = FieldResolver
                        .getName(object.getClass(), propertyDescriptor.getName());
                    Object value = propertyDescriptor.getReadMethod().invoke(object);
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(name);
                    sb.append(":");
                    sb.append(value);
                }
            } catch (Exception e) {
            }
        });
        return sb.toString();
    }

    private static class FieldResolver {

        private static Map<String, String> FIELD_NAME_CACHE = new ConcurrentHashMap<>();

        private static Map<String, Boolean> FIELD_IGNORE_CACHE = new ConcurrentHashMap<>();

        public static String getName(Class<?> clazz, String fieldName) {
            if (clazz == null || fieldName == null) {
                return fieldName;
            }
            String key = clazz.getName() + ":" + fieldName;
            String value = FIELD_NAME_CACHE.get(key);
            if (value == null) {
                Field field = getField(clazz, fieldName);
                if (field.isAnnotationPresent(Name.class)) {
                    Name annotation = field.getDeclaredAnnotation(Name.class);
                    value = annotation.value();
                } else {
                    value = fieldName;
                }
                FIELD_NAME_CACHE.put(key, value);
            }
            return value;
        }

        public static boolean isIgnore(Class<?> clazz, String fieldName) {
            if (clazz == null || fieldName == null) {
                return false;
            }
            if ("class".equals(fieldName)) {
                return true;
            }
            String key = clazz.getName() + ":" + fieldName;
            Boolean value = FIELD_IGNORE_CACHE.get(key);
            if (value == null) {
                Field field = getField(clazz, fieldName);
                value = field.isAnnotationPresent(Ignore.class);
                FIELD_IGNORE_CACHE.put(key, value);
            }
            return value;
        }

        private static Field getField(Class<?> clazz, String fieldName) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Class<?> superclass = clazz.getSuperclass();
                if (superclass == Object.class) {
                    throw new RuntimeException(e);
                }
                return getField(superclass, fieldName);
            }
        }

    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Name {

        String value();
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Ignore {

    }

}
