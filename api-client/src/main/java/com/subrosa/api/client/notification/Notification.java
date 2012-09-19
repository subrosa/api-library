package com.subrosa.api.client.notification;

import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.subrosa.api.notification.DetailAdapter;
import com.subrosa.api.notification.Severity;


/**
 * Represents a notification message related to a request.
 */
@XmlRootElement(name = "notification")
public class Notification {

    private Integer code;
    private Severity severity;
    private String text;
    private Map<String, String> details;

    /**
     * Constructs an empty notification.
     */
    public Notification() {
    }

    /**
     * Constructs a Notification with the specified code, severity, and text.
     *
     * @param code the notification code
     * @param severity the severity of the message (error/warn/info)
     * @param text the text of the notification
     */
    public Notification(Integer code, Severity severity, String text) {
        this.code = code;
        this.severity = severity;
        this.text = text;
    }

    /**
     * Retrieves the notification code.
     *
     * @return the notification code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Sets the notification code.
     *
     * @param code the notification code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Retrieves the severity of the notification.
     *
     * @return the severity
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * Sets the severity of the notification.
     *
     * @param severity the severity
     */
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    /**
     * Retrieves the text for the notification.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text for the notification.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Retrieves the collection of details related to this notification.
     *
     * @return the collection of notification details
     */
    @XmlJavaTypeAdapter(DetailAdapter.class)
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Map<String, String> getDetails() {
        return details;
    }

    /**
     * Sets the collection of notification details for this notification.
     *
     * @param details the collection of notification details
     */
    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
}
