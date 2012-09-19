package com.subrosa.api.serialization.json;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import com.subrosa.api.serialization.DateSerialization;

/**
 * Deserializes Date objects from JSON using ISO8601 format.
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

    private static final DateSerialization DATE_FORMATTER = new DateSerialization();

    /**
     * Parses a date from the ISO8601 format.
     * 
     * @param parser the json parser
     * @param context the deserialization context
     * @throws JsonProcessingException when a JSON processing error occurs
     * @throws IOException when an IO error occurs
     * @return a date corresponding to the parsed string
     */
    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context) throws JsonProcessingException, IOException {
        try {
            String dateText = parser.getText();
            return DATE_FORMATTER.deserialize(dateText);
        } catch (IllegalArgumentException e) {
            throw new JsonParseException("Invalid date format.", parser.getCurrentLocation(), e);
        }
    }

}
