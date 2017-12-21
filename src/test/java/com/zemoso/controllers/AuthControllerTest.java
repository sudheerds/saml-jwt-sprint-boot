package com.zemoso.controllers;

import com.nimbusds.jose.JOSEException;
import com.zemoso.WithMockSaml;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author sudheerds
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {

    @WithMockSaml(samlAssertFile = "/saml-auth-assert.xml")
    @Test
    public void testAuthController() throws JOSEException {

        final AuthController authController = new AuthController();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        final ResponseEntity<String> responseEntity = authController.login(mockHttpServletRequest,mockHttpServletResponse);

        Assert.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertTrue(responseEntity.getBody().length() > 0);
    }
}
