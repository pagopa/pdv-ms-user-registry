package it.pagopa.pdv.user_registry.connector.rest.config;

import com.amazonaws.xray.proxies.apache.http.HttpClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Client;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.httpclient.ApacheHttpClient;
import it.pagopa.pdv.user_registry.connector.rest.interceptor.QueryParamsPlusEncoderInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@PropertySource("classpath:config/default-rest-client.properties")
public class RestClientBaseConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${feign.client.config.default.connectTimeout}")
    private int connectTimeout;

    @Value("${feign.client.config.default.readTimeout}")
    private int readTimeout;

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
    public HttpClientBuilder xrayHttpClientBuilder() {

        return HttpClientBuilder.create();
    }
    @Configuration
    public class FeignConfiguration {
        @Bean
        public Client client(HttpClientBuilder httpClientBuilder, FeignHttpClientProperties httpClientProperties) {

            httpClientBuilder = httpClientBuilder != null ? httpClientBuilder : HttpClientBuilder.create();

            final PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
                    new PoolingHttpClientConnectionManager();
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(500);
            poolingHttpClientConnectionManager.setMaxTotal(500);

            final RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(connectTimeout)
                    .setConnectTimeout(httpClientProperties.getConnectionTimeout())
                    .setSocketTimeout(readTimeout)
                    .setRedirectsEnabled(httpClientProperties.isFollowRedirects())
                    .build();

            return new ApacheHttpClient(
                    httpClientBuilder
                            .setConnectionManager(poolingHttpClientConnectionManager)
                            .setDefaultRequestConfig(defaultRequestConfig)
                            .build()
            );
        }
    }



}
