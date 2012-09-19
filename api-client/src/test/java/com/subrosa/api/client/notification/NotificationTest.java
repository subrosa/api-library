package com.subrosa.api.client.notification;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Test;
import com.subrosa.api.notification.Severity;


/**
 * Test the notification object.
 */
public class NotificationTest {

    /**
     * Test marshalling data.
     * @throws Exception On error.
     */
    @Test
    public void testMarshal() throws Exception {
        Map<String, String> details = new HashMap<String, String>();
        details.put("KEY_1", "VALUE_1");
        details.put("KEY_2", "VALUE_2");

        Notification notification = new Notification();
        notification.setText("Testing");
        notification.setCode(12345);
        notification.setSeverity(Severity.ERROR);
        notification.setDetails(details);

        StringWriter stringWriter = new StringWriter();

        Marshaller marshaller = JAXBContext.newInstance(Notification.class).createMarshaller();
        marshaller.marshal(notification, stringWriter);

        String xmlRepresentation = stringWriter.toString();
        Assert.assertTrue(xmlRepresentation.contains("<details><detail key=\"KEY_2\">VALUE_2</detail><detail key=\"KEY_1\">VALUE_1</detail></details>"));
    }

    /**
     * Test unmarshalling data.
     * @throws Exception On error.
     */
    @Test
    public void testUnmarshal() throws Exception {
        String xmlRepresentation = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<notification>"
                + "<code>12345</code>"
                + "<details>"
                + "<detail key=\"KEY_2\">VALUE_2</detail>"
                + "<detail key=\"KEY_1\">VALUE_1</detail>"
                + "</details>"
                + "<severity>ERROR</severity>"
                + "<text>Testing</text>"
                + "</notification>";

        Map<String, String> expectedDetails = new HashMap<String, String>();
        expectedDetails.put("KEY_1", "VALUE_1");
        expectedDetails.put("KEY_2", "VALUE_2");

        Notification expectedNotification = new Notification();
        expectedNotification.setText("Testing");
        expectedNotification.setCode(12345);
        expectedNotification.setSeverity(Severity.ERROR);
        expectedNotification.setDetails(expectedDetails);

        StringReader stringReader = new StringReader(xmlRepresentation);

        Unmarshaller unmarshaller = JAXBContext.newInstance(Notification.class).createUnmarshaller();
        Notification actualNotification = (Notification) unmarshaller.unmarshal(stringReader);

        Assert.assertEquals(expectedNotification.getCode(), actualNotification.getCode());
        Assert.assertEquals(expectedNotification.getSeverity(), actualNotification.getSeverity());
        Assert.assertEquals(expectedNotification.getCode(), actualNotification.getCode());
        Assert.assertEquals(expectedNotification.getDetails(), actualNotification.getDetails());
    }
}
