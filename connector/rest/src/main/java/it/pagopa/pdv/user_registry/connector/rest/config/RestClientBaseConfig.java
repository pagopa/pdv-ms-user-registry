package it.pagopa.pdv.user_registry.connector.rest.config;

import com.amazonaws.http.apache.client.impl.ApacheHttpClientFactory;
import com.amazonaws.xray.proxies.apache.http.HttpClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Client;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.httpclient.ApacheHttpClient;
import it.pagopa.pdv.user_registry.connector.rest.interceptor.QueryParamsPlusEncoderInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Slf4j
@Configuration(proxyBeanMethods = false)
@PropertySource("classpath:config/default-rest-client.properties")
@ConditionalOnClass(ApacheHttpClient.class)
@ConditionalOnMissingClass("com.netflix.loadbalancer.ILoadBalancer")
@ConditionalOnProperty(value = "feign.httpclient.enabled", matchIfMissing = true)
public class RestClientBaseConfig {

    @Autowired
    private ObjectMapper objectMapper;


    @Bean
    public RequestInterceptor queryParamsPlusEncoderInterceptor() {
        return new QueryParamsPlusEncoderInterceptor();
    }


    @Bean
    public Decoder feignDecoder(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);

        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }


    @Bean
    public Encoder feignEncoder(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);

        return new SpringEncoder(objectFactory);
    }
    @Bean
    public ApacheHttpClientFactory httpClientFactory() {
        return new ApacheHttpClientFactory();
    }
    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        return new BasicHttpClientConnectionManager();
    }
    @Bean
    public Client client(ApacheHttpClientFactory httpClientFactory,
                         HttpClientConnectionManager httpClientConnectionManager,
                         FeignHttpClientProperties httpClientProperties) {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(httpClientProperties.getConnectionTimeout())
                .setRedirectsEnabled(httpClientProperties.isFollowRedirects())
                .build();
        return new ApacheHttpClient(
                HttpClientBuilder.create()
                        .setConnectionManager(httpClientConnectionManager)
                        .setDefaultRequestConfig(defaultRequestConfig)
                        .build()
        );
    }
}
