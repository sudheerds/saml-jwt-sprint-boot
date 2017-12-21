package com.zemoso.controllers;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.zemoso.config.SecurityConstant;
import com.zemoso.model.SamlUserDetails;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sudheerds
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request,HttpServletResponse response) throws JOSEException {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {

            SamlUserDetails samlUserDetails = (SamlUserDetails) authentication.getPrincipal();
            final DateTime dateTime = DateTime.now();

            //build claims
            JWTClaimsSet.Builder jwtClaimsSetBuilder = new JWTClaimsSet.Builder();
            jwtClaimsSetBuilder.expirationTime(dateTime.plusMinutes(120).toDate());
            jwtClaimsSetBuilder.claim("APP", "SAMPLE");
            jwtClaimsSetBuilder.claim("userEmail", samlUserDetails.getUsername());

            //signature
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtClaimsSetBuilder.build());
            signedJWT.sign(new MACSigner(SecurityConstant.JWT_SECRET));

            Cookie accessToken = new Cookie("jwt",signedJWT.serialize());
            accessToken.setPath("/");
            response.addCookie(accessToken);

            return new ResponseEntity<>(signedJWT.serialize(), HttpStatus.OK);
        }
    }
}
