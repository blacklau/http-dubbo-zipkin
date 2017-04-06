package com.louie.trace;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ServerRequestInterceptor;
import com.github.kristofa.brave.ServerResponseInterceptor;
import com.github.kristofa.brave.ServerSpan;
import com.github.kristofa.brave.ServerSpanThreadBinder;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.HttpResponse;
import com.github.kristofa.brave.http.HttpServerRequest;
import com.github.kristofa.brave.http.HttpServerResponseAdapter;
import com.github.kristofa.brave.http.SpanNameProvider;

import zipkin.reporter.AsyncReporter;
import zipkin.reporter.Reporter;
import zipkin.reporter.Sender;
import zipkin.reporter.okhttp3.OkHttpSender;

public class CusHttpServletHandlerInterceptor  extends HandlerInterceptorAdapter {


    static final String HTTP_SERVER_SPAN_ATTRIBUTE = CusHttpServletHandlerInterceptor.class.getName() + ".server-span";

    private final ServerRequestInterceptor requestInterceptor;
    private final ServerResponseInterceptor responseInterceptor;
    private final ServerSpanThreadBinder serverThreadBinder;
    private final SpanNameProvider spanNameProvider;

    CusHttpServletHandlerInterceptor() {
    	Sender sender = OkHttpSender.create("http://127.0.0.1:9411/api/v1/spans");
    	Reporter<zipkin.Span> reporter = AsyncReporter.builder(sender).build();
    	Brave brave = new Brave.Builder("webapi").reporter(reporter).build();
    	
        this.requestInterceptor = brave.serverRequestInterceptor();
        this.responseInterceptor = brave.serverResponseInterceptor();
        this.serverThreadBinder = brave.serverSpanThreadBinder();
        this.spanNameProvider = new DefaultSpanNameProvider();
    }


    /**
     * @deprecated please use {@link #create(Brave)} or {@link #builder(Brave)}
     */
    @Deprecated
    public CusHttpServletHandlerInterceptor(ServerRequestInterceptor requestInterceptor, ServerResponseInterceptor responseInterceptor, SpanNameProvider spanNameProvider, final ServerSpanThreadBinder serverThreadBinder) {
        this.requestInterceptor = requestInterceptor;
        this.spanNameProvider = spanNameProvider;
        this.responseInterceptor = responseInterceptor;
        this.serverThreadBinder = serverThreadBinder;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
    	String service = request.getParameter("service");
    	service = service == null || service == ""?"unkown":service;
    	String data = request.getParameter("data");
    	data = data == null || data == ""?"{}":data;
    	
    	requestInterceptor.handle(new CusHttpServerRequestAdapter(new HttpServerRequest() {
            
            public String getHttpHeaderValue(String headerName) {
                return request.getHeader(headerName);
            }

            
            public URI getUri() {
                try {
                    return new URI(request.getRequestURI());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }

           
            public String getHttpMethod() {
            	String service = request.getParameter("service");
                service = service == null || service == ""?"unkown":service;
                return service;
            } 
        },service,data, spanNameProvider));

        return true;
    }

    @Override
    public void afterConcurrentHandlingStarted(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        request.setAttribute(HTTP_SERVER_SPAN_ATTRIBUTE, serverThreadBinder.getCurrentServerSpan());
        serverThreadBinder.setCurrentSpan(null);
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {

        final ServerSpan span = (ServerSpan) request.getAttribute(HTTP_SERVER_SPAN_ATTRIBUTE);

        if (span != null) {
            serverThreadBinder.setCurrentSpan(span);
        }

       responseInterceptor.handle(new HttpServerResponseAdapter(new HttpResponse() {
          
           public int getHttpStatusCode() {
               return response.getStatus();
           }
       }));
    }

}
