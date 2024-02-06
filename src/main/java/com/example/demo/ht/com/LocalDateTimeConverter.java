package com.example.demo.ht.com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author heteng
 * @date 2024/1/22
 * @description
 */
@Configuration("aaaaaa")
public class LocalDateTimeConverter {
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(source)), ZoneId.systemDefault());
            }
        };
    }
}
