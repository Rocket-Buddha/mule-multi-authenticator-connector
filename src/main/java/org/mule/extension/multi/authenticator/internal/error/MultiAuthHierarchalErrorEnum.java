package org.mule.extension.multi.authenticator.internal.error;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;
import java.util.Optional;

/**
 * Mule Error type definition hierarchal enumerator.
 */
public enum MultiAuthHierarchalErrorEnum implements ErrorTypeDefinition<MultiAuthHierarchalErrorEnum> {
    INVALID_HTTP_AUTH_TYPE(MuleErrors.VALIDATION),
    BAD_BASE64_ENCODE(MuleErrors.VALIDATION),
    BAD_CREDENTIALS(MuleErrors.SERVER_SECURITY),
    HASH_ALGORITHM_NOT_SUPPORTED(MuleErrors.VALIDATION),
    MISSING_AUTH_HEADER(MuleErrors.VALIDATION),
    MISSING_HASH_ALGORITHM(MuleErrors.VALIDATION),
    MISSING_ACCESS_LIST(MuleErrors.VALIDATION),
    INVALID_TOKEN(MuleErrors.SERVER_SECURITY),
    MISSING_SECRET(MuleErrors.VALIDATION),
    MISSING_JWT(MuleErrors.VALIDATION),
    MISSING_TTL(MuleErrors.VALIDATION),
    JWT_PAYLOAD_JSON_PARSING_ERROR(MuleErrors.TRANSFORMATION);


    private ErrorTypeDefinition<? extends Enum<?>> parent;

    MultiAuthHierarchalErrorEnum(ErrorTypeDefinition<? extends Enum<?>> parent) {
        this.parent = parent;
    }

    MultiAuthHierarchalErrorEnum() {
    }

    @Override
    public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
        return Optional.ofNullable(parent);
    }
}
