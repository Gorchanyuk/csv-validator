package ru.gorchanyuk.csvvalidator.util;

import ru.gorchanyuk.csvvalidator.model.Content;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ContentCreator {

    public Content createValidRegistry(){

        return Content.builder()
                .vehicleId("12345678901")
                .licensePlate("someCod1234456")
                .registrationDate(LocalDate.now())
                .expirationDate(LocalDate.now())
                .owner("some owner")
                .build();
    }

    public Content createRegistryWithInvalidSnils(){

        Content content = createValidRegistry();
        content.setVehicleId("123456789012");

        return content;
    }

    public Content createRegistryWithInvalidCategoryCod(){

        Content content = createValidRegistry();
        content.setLicensePlate("-category cod-");

        return content;
    }

    public Content createRegistryWithInvalidDates(){

        Content content = createValidRegistry();
        content.setRegistrationDate(null);
        content.setExpirationDate(null);

        return content;
    }

    public Content createRegistryWithInvalidOwner(){

        Content content = createValidRegistry();
        content.setOwner(RandomString.make(60));

        return content;
    }
}
