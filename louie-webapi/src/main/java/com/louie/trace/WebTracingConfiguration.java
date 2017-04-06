package com.louie.trace;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.kristofa.brave.spring.BraveClientHttpRequestInterceptor;

/**
 * This adds tracing configuration to any web mvc controllers or rest template clients. This should
 * be configured last.
 */
// import as the interceptors are annotation with javax.inject and not automatically wired
@Configuration
@Import({BraveClientHttpRequestInterceptor.class, CusHttpServletHandlerInterceptor.class})
public class WebTracingConfiguration extends WebMvcConfigurerAdapter {


  @Autowired
  //private ServletHandlerInterceptor serverInterceptor;
  private CusHttpServletHandlerInterceptor serverInterceptor;

  @Autowired
  private BraveClientHttpRequestInterceptor clientInterceptor;

  @Autowired
  private RestTemplate restTemplate;

  // adds tracing to the application-defined rest template
  @PostConstruct
  public void init() {
    List<ClientHttpRequestInterceptor> interceptors =
        new ArrayList<ClientHttpRequestInterceptor>(restTemplate.getInterceptors());
    interceptors.add(clientInterceptor);
    restTemplate.setInterceptors(interceptors);
  }

  // adds tracing to the application-defined web controllers
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(serverInterceptor);
  }
}
