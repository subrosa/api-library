package com.subrosa.api.actions.list.serialization.json;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.subrosa.api.actions.list.Filter;

/**
 * Handles JSON serialization of filter lists.
 */
public class FilterListJsonSerializer extends JsonSerializer<List<Filter>> {

    @Override
    public void serialize(List<Filter> value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartArray();
        for (Filter filter : value) {
            jgen.writeStartObject();
            jgen.writeObjectField(filter.getFilterKey(), filter.getValue());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
    }

}
