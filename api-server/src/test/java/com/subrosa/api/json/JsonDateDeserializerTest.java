package com.subrosa.api.json;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.junit.Test;

import com.subrosa.api.serialization.json.JsonDateDeserializer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test {@link JsonDateDeserializer}.
 */
public class JsonDateDeserializerTest {
    
    /**
     * Test that a bad date format results in a {@link JsonParseException}.
     */
    @Test(expected = JsonParseException.class)
    public void testBadDate() throws Exception {
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext context = mock(DeserializationContext.class);
        
        when(parser.getText()).thenReturn("LUNCHTIME LAST WEDNESDAY");
        
        JsonDateDeserializer deserializer = new JsonDateDeserializer();
        deserializer.deserialize(parser, context);
    }
    
}
