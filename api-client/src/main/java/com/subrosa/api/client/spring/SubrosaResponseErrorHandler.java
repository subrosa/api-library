package com.subrosa.api.client.spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.http.converter.xml.XmlAwareFormHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestClientException;
import com.subrosa.api.client.exceptions.SubrosaRestException;
import com.subrosa.api.client.notification.NotificationList;


/**
 * Subrosa implementation for handling {@link org.springframework.web.client.RestTemplate} errors.
 */
public class SubrosaResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SubrosaResponseErrorHandler.class);

    // CHECKSTYLE-OFF: ConstantName
    private static final boolean jaxb2Present =
        ClassUtils.isPresent("javax.xml.bind.Binder", SubrosaResponseErrorHandler.class.getClassLoader());

    private static final boolean jacksonPresent =
        ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", SubrosaResponseErrorHandler.class.getClassLoader())
            && ClassUtils.isPresent("org.codehaus.jackson.JsonGenerator", SubrosaResponseErrorHandler.class.getClassLoader());

    private static boolean romePresent =
        ClassUtils.isPresent("com.sun.syndication.feed.WireFeed", SubrosaResponseErrorHandler.class.getClassLoader());
    // CHECKSTYLE-ON: ConstantName

    private List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

    private final HttpMessageConverterExtractor<NotificationList> messageConverterExtractor;

    /**
     *  Create a new instance of the {@link SubrosaResponseErrorHandler} using default settings.
     */
    public SubrosaResponseErrorHandler() {
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new SourceHttpMessageConverter());
        messageConverters.add(new XmlAwareFormHttpMessageConverter());
        if (jaxb2Present) {
            messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
        }
        if (jacksonPresent) {
            messageConverters.add(new MappingJacksonHttpMessageConverter());
        }
        if (romePresent) {
            messageConverters.add(new AtomFeedHttpMessageConverter());
            messageConverters.add(new RssChannelHttpMessageConverter());
        }
        messageConverterExtractor = new HttpMessageConverterExtractor<NotificationList>(NotificationList.class, messageConverters);
    }

    /**
     * This default implementation throws a {@link com.subrosa.api.client.exceptions.SubrosaRestException} if the response status code
     * is {@link org.springframework.http.HttpStatus.Series#CLIENT_ERROR} or {@link org.springframework.http.HttpStatus.Series#SERVER_ERROR},
     * and a {@link RestClientException} in other cases.
     * @param response HTTP response
     * @throws IOException on i/o error
     */
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        switch (statusCode.series()) {
            case CLIENT_ERROR:
            case SERVER_ERROR:
                NotificationList notificationList = getNotificationListFromResponse(response);
                throw new SubrosaRestException(statusCode, notificationList);
            default:
                throw new RestClientException("Unhandled status code [" + statusCode + "]");
        }
    }

    private NotificationList getNotificationListFromResponse(ClientHttpResponse response) throws IOException {
        NotificationList notificationList = null;
        try {
            notificationList = messageConverterExtractor.extractData(response);
        } catch (HttpMessageConversionException ex) {
            LOG.error("Failed to convert response into NotificationList: {}", IOUtils.toString(response.getBody()), ex);
        } catch (RestClientException ex) {
            LOG.error("Failed to convert response into NotificationList: {}", IOUtils.toString(response.getBody()), ex);
        }
        return notificationList;
    }

    /**
     * Override the default message converters.
     * @param messageConverters list of message converters
     */
    public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        Assert.notEmpty(messageConverters, "'messageConverters' must not be empty");
        this.messageConverters = messageConverters;
    }
}
