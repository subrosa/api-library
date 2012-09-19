package com.subrosa.api.client.spring;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import com.google.common.collect.Iterators;
import com.subrosa.api.client.exceptions.SubrosaRestException;
import com.subrosa.api.notification.Severity;


/**
 * Test class for SubrosaResponseErrorHandler.
 */
public class SubrosaResponseErrorHandlerTest {
    /**
     * Test handling a Subrosa.com response.
     * @throws Exception Exception
     */
    @Test
    public void testOkResponse() throws Exception {
        ClientHttpResponse mockResponse = Mockito.mock(ClientHttpResponse.class);
        Mockito.when(mockResponse.getStatusCode()).thenReturn(HttpStatus.OK);

        SubrosaResponseErrorHandler errorHandler = new SubrosaResponseErrorHandler();
        Assert.assertFalse(errorHandler.hasError(mockResponse));

        Mockito.verify(mockResponse, Mockito.times(1)).getStatusCode();
        Mockito.verifyNoMoreInteractions(mockResponse);
    }

    /**
     * Test handling a Subrosa.com error.
     * @throws Exception Exception
     */
    @Test
    public void testErrorResponseJson() throws Exception {
        InputStream mockBody = new ByteArrayInputStream(
                "{\"notifications\":[{\"text\":\"email address is already in use.\",\"severity\":\"ERROR\",\"code\":1300030002}]}".getBytes()
        );

        HttpHeaders mockHeaders = Mockito.mock(HttpHeaders.class);
        Mockito.when(mockHeaders.getContentType()).thenReturn(MediaType.APPLICATION_JSON);
        Mockito.when(mockHeaders.getContentLength()).thenReturn(1L);

        ClientHttpResponse mockResponse = Mockito.mock(ClientHttpResponse.class);
        Mockito.when(mockResponse.getStatusCode()).thenReturn(HttpStatus.FORBIDDEN);
        Mockito.when(mockResponse.getHeaders()).thenReturn(mockHeaders);
        Mockito.when(mockResponse.getBody()).thenReturn(mockBody);

        SubrosaResponseErrorHandler errorHandler = new SubrosaResponseErrorHandler();
        Assert.assertTrue(errorHandler.hasError(mockResponse));
        try {
            errorHandler.handleError(mockResponse);
            Assert.fail("Expected SubrosaRestException");
        } catch (SubrosaRestException ex) {
            Assert.assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
            Assert.assertEquals(1, ex.getNotifications().size());
            Assert.assertEquals(Integer.valueOf(1300030002), Iterators.get(ex.getNotifications().iterator(), 0).getCode());
            Assert.assertEquals(Severity.ERROR, Iterators.get(ex.getNotifications().iterator(), 0).getSeverity());
            Assert.assertEquals("email address is already in use.", Iterators.get(ex.getNotifications().iterator(), 0).getText());
        }

        Mockito.verify(mockResponse, Mockito.atLeast(1)).getStatusCode();
        Mockito.verify(mockResponse, Mockito.times(1)).getBody();
        Mockito.verify(mockResponse, Mockito.atLeast(1)).getHeaders();
        Mockito.verifyNoMoreInteractions(mockResponse);
        Mockito.verify(mockHeaders, Mockito.atLeast(1)).getContentType();
        Mockito.verify(mockHeaders, Mockito.atLeast(1)).getContentLength();
        Mockito.verifyNoMoreInteractions(mockHeaders);
    }

    /**
     * Test handling a Subrosa.com error.
     * @throws Exception Exception
     */
    @Test
    public void testErrorResponseXml() throws Exception {
        InputStream mockBody = new ByteArrayInputStream(
                ("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                        + "<notifications>"
                        + "<notification><code>1300030002</code><severity>ERROR</severity><text>email address is already in use.</text></notification>"
                        + "</notifications>").getBytes()
        );

        HttpHeaders mockHeaders = Mockito.mock(HttpHeaders.class);
        Mockito.when(mockHeaders.getContentType()).thenReturn(MediaType.APPLICATION_XML);
        Mockito.when(mockHeaders.getContentLength()).thenReturn(1L);

        ClientHttpResponse mockResponse = Mockito.mock(ClientHttpResponse.class);
        Mockito.when(mockResponse.getStatusCode()).thenReturn(HttpStatus.FORBIDDEN);
        Mockito.when(mockResponse.getHeaders()).thenReturn(mockHeaders);
        Mockito.when(mockResponse.getBody()).thenReturn(mockBody);

        SubrosaResponseErrorHandler errorHandler = new SubrosaResponseErrorHandler();
        Assert.assertTrue(errorHandler.hasError(mockResponse));
        try {
            errorHandler.handleError(mockResponse);
            Assert.fail("Expected SubrosaRestException");
        } catch (SubrosaRestException ex) {
            Assert.assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
            Assert.assertEquals(1, ex.getNotifications().size());
            Assert.assertEquals(Integer.valueOf(1300030002), Iterators.get(ex.getNotifications().iterator(), 0).getCode());
            Assert.assertEquals(Severity.ERROR, Iterators.get(ex.getNotifications().iterator(), 0).getSeverity());
            Assert.assertEquals("email address is already in use.", Iterators.get(ex.getNotifications().iterator(), 0).getText());
        }

        Mockito.verify(mockResponse, Mockito.atLeast(1)).getStatusCode();
        Mockito.verify(mockResponse, Mockito.times(1)).getBody();
        Mockito.verify(mockResponse, Mockito.atLeast(1)).getHeaders();
        Mockito.verifyNoMoreInteractions(mockResponse);
        Mockito.verify(mockHeaders, Mockito.atLeast(1)).getContentType();
        Mockito.verify(mockHeaders, Mockito.atLeast(1)).getContentLength();
        Mockito.verifyNoMoreInteractions(mockHeaders);
    }

    /**
     * Test handling a Subrosa.com error.
     * @throws Exception Exception
     */
    @Test
    public void testErrorResponseInvalidContentType() throws Exception {
        InputStream mockBody = new ByteArrayInputStream(
                ("SOMETHING BAD HAS HAPPENED").getBytes()
        );

        HttpHeaders mockHeaders = Mockito.mock(HttpHeaders.class);
        Mockito.when(mockHeaders.getContentType()).thenReturn(null);
        Mockito.when(mockHeaders.getContentLength()).thenReturn(1L);

        ClientHttpResponse mockResponse = Mockito.mock(ClientHttpResponse.class);
        Mockito.when(mockResponse.getStatusCode()).thenReturn(HttpStatus.FORBIDDEN);
        Mockito.when(mockResponse.getHeaders()).thenReturn(mockHeaders);
        Mockito.when(mockResponse.getBody()).thenReturn(mockBody);

        SubrosaResponseErrorHandler errorHandler = new SubrosaResponseErrorHandler();
        Assert.assertTrue(errorHandler.hasError(mockResponse));
        try {
            errorHandler.handleError(mockResponse);
            Assert.fail("Expected SubrosaRestException");
        } catch (SubrosaRestException ex) {
            Assert.assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
            Assert.assertEquals(0, ex.getNotifications().size());
        }

        Mockito.verify(mockResponse, Mockito.atLeast(1)).getStatusCode();
        Mockito.verify(mockResponse, Mockito.times(1)).getBody();
        Mockito.verify(mockResponse, Mockito.atLeast(1)).getHeaders();
        Mockito.verifyNoMoreInteractions(mockResponse);
        Mockito.verify(mockHeaders, Mockito.atLeast(1)).getContentType();
        Mockito.verify(mockHeaders, Mockito.atLeast(1)).getContentLength();
        Mockito.verifyNoMoreInteractions(mockHeaders);
    }

    /**
     * Test handling a Subrosa.com error.
     * @throws Exception Exception
     */
    @Test
    public void testErrorResponseInvalidJson() throws Exception {
        InputStream mockBody = new ByteArrayInputStream(
                ("INVALID JSON").getBytes()
        );

        HttpHeaders mockHeaders = Mockito.mock(HttpHeaders.class);
        Mockito.when(mockHeaders.getContentType()).thenReturn(MediaType.APPLICATION_JSON);
        Mockito.when(mockHeaders.getContentLength()).thenReturn(1L);

        ClientHttpResponse mockResponse = Mockito.mock(ClientHttpResponse.class);
        Mockito.when(mockResponse.getStatusCode()).thenReturn(HttpStatus.FORBIDDEN);
        Mockito.when(mockResponse.getHeaders()).thenReturn(mockHeaders);
        Mockito.when(mockResponse.getBody()).thenReturn(mockBody);

        SubrosaResponseErrorHandler errorHandler = new SubrosaResponseErrorHandler();
        Assert.assertTrue(errorHandler.hasError(mockResponse));
        try {
            errorHandler.handleError(mockResponse);
            Assert.fail("Expected SubrosaRestException");
        } catch (SubrosaRestException ex) {
            Assert.assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
            Assert.assertEquals(0, ex.getNotifications().size());
        }

        Mockito.verify(mockResponse, Mockito.atLeast(1)).getStatusCode();
        Mockito.verify(mockResponse, Mockito.times(2)).getBody();
        Mockito.verify(mockResponse, Mockito.atLeast(1)).getHeaders();
        Mockito.verifyNoMoreInteractions(mockResponse);
        Mockito.verify(mockHeaders, Mockito.atLeast(1)).getContentType();
        Mockito.verify(mockHeaders, Mockito.atLeast(1)).getContentLength();
        Mockito.verifyNoMoreInteractions(mockHeaders);
    }
}
