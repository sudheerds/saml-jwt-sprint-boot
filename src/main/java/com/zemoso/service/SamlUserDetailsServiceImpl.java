package com.zemoso.service;

import com.zemoso.model.SamlUserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

/**
 * @author sudheerds
 */
public class SamlUserDetailsServiceImpl implements SAMLUserDetailsService {

    @Override
    public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
        return new SamlUserDetails();
    }
}