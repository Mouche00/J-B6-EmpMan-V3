package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JSONUtil {

    public String serializeMap(Map<String, Object> modifiedAttributes) {
        try {
            return ObjectMapperFactory.getObjectMapper().writeValueAsString(modifiedAttributes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    public List<Map<String, Object>> mergeJsonStringListToMap(List<String> jsonStrings) {
//        List<Map<String, Object>> mapList = jsonStrings.stream()
//                .map(this::convertJsonToMap)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toList());
//
//        return mapList;
//    }

    public Optional<Map<String, Object>> convertJsonToMap(String jsonString) {
        try {
            return Optional.of(ObjectMapperFactory.getObjectMapper().readValue(jsonString, new TypeReference<>() {}));
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
            return Optional.empty();
        }
    }

//    public Map<String, Object> mergeMaps(List<Map<String, Object>> maps) {
//        return maps.stream()
//                .flatMap(map -> map.entrySet().stream())
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue
//                ));
//    }
}
