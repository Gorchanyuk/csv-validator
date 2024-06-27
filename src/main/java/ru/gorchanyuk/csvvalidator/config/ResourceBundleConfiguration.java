package ru.gorchanyuk.csvvalidator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.ResourceBundle;

@Configuration
public class ResourceBundleConfiguration {

    @Bean
    public ResourceBundle getResourceBundle() {
        Locale locale =
                new Locale.Builder()
                        .setLanguage("ru")
                        .setRegion("RU")
                        .build();

        return ResourceBundle.getBundle(
                "message_resource",
                locale
        );
    }
}
