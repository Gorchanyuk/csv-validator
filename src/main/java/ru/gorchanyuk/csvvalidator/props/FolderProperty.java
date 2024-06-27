package ru.gorchanyuk.csvvalidator.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "props.folder")
public class FolderProperty {

    private String car;
    private String train;
}
