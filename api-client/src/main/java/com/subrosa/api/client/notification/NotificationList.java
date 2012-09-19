package com.subrosa.api.client.notification;

import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Class for sending error notifications to the client. This should only be used with non 2XX HTTP status codes
 */
@XmlRootElement(name = "notifications")
public class NotificationList {

    private Collection<Notification> notifications;

    /**
     * Gets the collection of notifications in the response.
     *
     * @return a collection of notifications
     */
    @XmlElement(name = "notification")
    public Collection<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Sets the collection of notifications in the response.
     *
     * @param notifications the notifications to send to the client
     */
    public void setNotifications(Collection<Notification> notifications) {
        this.notifications = notifications;
    }

}
