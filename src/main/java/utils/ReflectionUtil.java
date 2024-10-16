package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.ModHistory;
import models.User;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtil {

    public static List<Field> getAllDeclaredFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                fields.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    public static Map<String, Object> trackModifiedAttributes(Object existingObject, Object updatedObject) {
        Map<String, Object> modifiedAttributes = new HashMap<>();

        List<Field> fields = ReflectionUtil.getAllDeclaredFields(User.class);
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object oldValue = field.get(existingObject);
                Object newValue = field.get(updatedObject);

                if ((oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null)) {
                    modifiedAttributes.put(field.getName(), oldValue);
                }

            } catch (IllegalAccessException e) {
                System.out.println("Failed to access field: " + field.getName() + " due to: " + e.getMessage());
            }
        }

        return modifiedAttributes;
    }

}