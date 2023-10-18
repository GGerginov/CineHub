package com.example.cinehub.config;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    private final ModelMapper modelMapper;

    public ModelMapperConfig() {
        this.modelMapper = new ModelMapper();
        setGlobalMapperConfiguration();
    }

    private void setGlobalMapperConfiguration() {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Bean
    public ModelMapper modelMapper() {
        return this.modelMapper;
    }
}

