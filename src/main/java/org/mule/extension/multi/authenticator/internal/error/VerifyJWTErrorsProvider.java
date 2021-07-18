package org.mule.extension.multi.authenticator.internal.error;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * Custom Error provider for the module operation.
 */
public class VerifyJWTErrorsProvider implements ErrorTypeProvider {

    @Override
    public Set<ErrorTypeDefinition> getErrorTypes() {
        HashSet<ErrorTypeDefinition> errors = new HashSet<>();
        errors.add(MultiAuthHierarchalErrorEnum.MISSING_JWT);
        errors.add(MultiAuthHierarchalErrorEnum.MISSING_SECRET);
        errors.add(MultiAuthHierarchalErrorEnum.INVALID_TOKEN);
        errors.add(MultiAuthHierarchalErrorEnum.JWT_PAYLOAD_JSON_PARSING_ERROR);
        return errors;
    }
}