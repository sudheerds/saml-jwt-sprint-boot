package com.zemoso;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Mock annotation pour un contexte de sécurité saml
 *
 * @author sudheerds
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockSamlSecurityContextFactory.class)
public @interface WithMockSaml {

    String samlAssertFile();
}
