package ma.yc.util.producers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@ApplicationScoped
public class ValidatorProducer {

    @Produces
    public Validator produceValidator () {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
