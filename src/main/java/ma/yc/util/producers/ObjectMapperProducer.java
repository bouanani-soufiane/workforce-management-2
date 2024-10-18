package ma.yc.util.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class ObjectMapperProducer {

    @Produces
    public ObjectMapper produceObjectMapper () {
        return new ObjectMapper();
    }
}
