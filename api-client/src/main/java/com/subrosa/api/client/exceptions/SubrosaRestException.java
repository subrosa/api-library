package com.subrosa.api.client.exceptions;

import java.util.Collection;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import com.subrosa.api.client.notification.Notification;
import com.subrosa.api.client.notification.NotificationList;


/**
 * Exception thrown when a 4xx or 5xx error is received from a Subrosa.com API.
 */
public class SubrosaRestException extends RestClientException {

    private static final long serialVersionUID = -107484244654211L;

    private HttpStatus statusCode;
    private NotificationList notificationList;

    /**
     * Create a new exception with the provided notification list.
     * @param statusCode HTTP status code
     * @param notificationList Notification list with errors
     */
    public SubrosaRestException(HttpStatus statusCode, NotificationList notificationList) {
        super("Received an error response code [" + statusCode + "] from remote server.");
        this.statusCode = statusCode;
        this.notificationList = notificationList;
    }

    /**
     * Create a new exception with the provided notification list and cause.
     * @param statusCode HTTP status code
     * @param notificationList Notification list with errors
     * @param cause the cause of the exception
     */
    public SubrosaRestException(HttpStatus statusCode, NotificationList notificationList, Throwable cause) {
        super("Received an error response code [" + statusCode + "] from remote server.", cause);
        this.statusCode = statusCode;
        this.notificationList = notificationList;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    /**
     * Return notifications from the notification list.
     * @return collection of {@link Notification} objects
     */
    public Collection<Notification> getNotifications() {
        if (notificationList == null) {
            return Collections.emptyList();
        }
        return notificationList.getNotifications();
    }
}
