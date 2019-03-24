package com.dh.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

    @Bean("transportClient")
    public TransportClient transportClient() throws UnknownHostException {
        return new PreBuiltXPackTransportClient(Settings.builder()
                .put("cluster.name", "my-application-1")
                .put("xpack.security.user", "elastic:elastic")
                .build())
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }


}
