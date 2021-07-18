package org.mule.extension.multi.authenticator.internal;

import org.mule.extension.multi.authenticator.internal.error.MultiAuthHierarchalErrorEnum;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;

/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "multi-authenticator")
@Extension(name = "Multi Method Authenticator Connector")
@Operations(MultiAuthenticatorOperations.class)
@ErrorTypes(MultiAuthHierarchalErrorEnum.class)
public class MultiAuthenticatorExtension {

}
