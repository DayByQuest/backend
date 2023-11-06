package daybyquest.profile.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Collections;
import java.util.List;

@Converter
public class ProfileBadgeIdsConverter implements AttributeConverter<List<Long>, String> {

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        final List<String> attributeStrings = attribute.stream().map(String::valueOf).toList();
        return String.join(",", attributeStrings);
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Collections.emptyList();
        }
        final List<String> attributeStrings = List.of(dbData.split(","));
        return attributeStrings.stream().map(Long::valueOf).toList();
    }
}
