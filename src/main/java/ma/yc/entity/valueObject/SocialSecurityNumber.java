package ma.yc.entity.valueObject;

import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record SocialSecurityNumber(
        String ssn
) {

    private static final String SSN_PATTERN = "^(\\d{3}-\\d{2}-\\d{4})$";
    private static final Pattern PATTERN = Pattern.compile(SSN_PATTERN);

    public SocialSecurityNumber {
        if ( ssn == null || !PATTERN.matcher(ssn).matches()) {
            throw new IllegalArgumentException("Invalid Social Security Number");
        }
    }
}
