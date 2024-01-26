package com.maradamark99.thesis.storage;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "object-store.buckets")
@Getter
@Setter
public class Buckets {

    private String product;

}
