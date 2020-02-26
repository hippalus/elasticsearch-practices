package com.elasticsearch.practices.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application")
public class ConfigProperties {

    @NestedConfigurationProperty
    private Index index = new Index();

    @NestedConfigurationProperty
    private Clients clients = new Clients();


}
