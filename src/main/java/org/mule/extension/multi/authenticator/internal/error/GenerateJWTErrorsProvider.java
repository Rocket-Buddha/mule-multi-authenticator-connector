package org.mule.extension.multi.authenticator.internal.error;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * Custom Error provider for the module operation.
 */
public class GenerateJWTErrorsProvider implements ErrorTypeProvider {

    @Override
    public Set<ErrorTypeDefinition> getErrorTypes() {
        HashSet<ErrorTypeDefinition> errors = new HashSet<>();
        errors.add(MultiAuthHierarchalErrorEnum.MISSING_TTL);
        errors.add(MultiAuthHierarchalErrorEnum.MISSING_SECRET);
        return errors;
    }
}