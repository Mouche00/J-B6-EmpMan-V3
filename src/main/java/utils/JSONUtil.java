package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JSONUtil {

    public static String serializeMap(Map<String, Object> modifiedAttributes) {
        try {
            return ObjectMapperFactory.getObjectMapper().writeValueAsString(modifiedAttributes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Map<String, Object>> mergeJsonStringListToMap(List<String> jsonStrings) {
        List<Map<String, Object>> mapList = jsonStrings.stream()
                .map(JSONUtil::convertJsonToMap) // Convert each JSON string to a Map
                .filter(Optional::isPresent)          // Filter out any invalid JSON strings
                .map(Optional::get)                   // Get the actual Map from Optional
                .collect(Collectors.toList());

        // Merge all the maps into a single map
        return mapList;
    }

    public static Optional<Map<String, Object>> convertJsonToMap(String jsonString) {
        try {
            return Optional.of(ObjectMapperFactory.getObjectMapper().readValue(jsonString, new TypeReference<>() {}));
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
            return Optional.empty(); // Return empty Optional on error
        }
    }

    public static Map<String, Object> mergeMaps(List<Map<String, Object>> maps) {
        return maps.stream()
                .flatMap(map -> map.entrySet().stream())  // Flatten the maps into individual entries
                .collect(Collectors.toMap(
                        Map.Entry::getKey,   // Use the key as-is
                        Map.Entry::getValue
                ));
    }
}
