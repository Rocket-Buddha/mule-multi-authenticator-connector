package org.mule.extension.multi.authenticator.internal.error;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import java.util.HashSet;
import java.util.Set;

/**
 * Custom Error provider for the module operation.
 */
public class DoApiKeyAuthErrorsProvider implements ErrorTypeProvider {

    @Override
    public Set<ErrorTypeDefinition> getErrorTypes() {
        HashSet<ErrorTypeDefinition> errors = new HashSet<>();
        errors.add(MultiAuthHierarchalErrorEnum.BAD_CREDENTIALS);
        errors.add(MultiAuthHierarchalErrorEnum.MISSING_AUTH_HEADER);
        errors.add(MultiAuthHierarchalErrorEnum.MISSING_HASH_ALGORITHM);
        errors.add(MultiAuthHierarchalErrorEnum.MISSING_ACCESS_LIST);
        errors.add(MultiAuthHierarchalErrorEnum.HASH_ALGORITHM_NOT_SUPPORTED);
        return errors;
    }
}