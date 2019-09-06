package com.nick.weixx.cloud.az.provider.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class RestTemplateConfig {
    static boolean ignoreSSL = true;

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {


        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(5000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        if (ignoreSSL) {
            CloseableHttpClient httpClient = getHttpsClient();
            factory.setHttpClient(httpClient);
        }
        return factory;
    }


    public static CloseableHttpClient getHttpsClient() {

        CloseableHttpClient httpClient;
        if (ignoreSSL) {//ignoreSSL为true时，绕过证书
            SSLContext sslContext = null;
            try {
                sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                    @Override
                    public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                        return true;
                    }

                }).build();
            } catch (NoSuchAlgorithmException e) {
                e.getStackTrace();
            } catch (KeyManagementException e) {
                e.getStackTrace();
            } catch (KeyStoreException e) {
                e.getStackTrace();
            }
            httpClient = HttpClients.custom().setSSLContext(sslContext).
                    setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }

}
