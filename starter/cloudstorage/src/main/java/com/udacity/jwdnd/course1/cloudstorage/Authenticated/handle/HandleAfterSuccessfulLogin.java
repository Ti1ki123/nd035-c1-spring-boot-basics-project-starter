package com.udacity.jwdnd.course1.cloudstorage.Authenticated.handle;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HandleAfterSuccessfulLogin extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String username = (String) authentication.getPrincipal();

        User user = userMapper.getUser(username);

        httpServletRequest.getSession().setAttribute("informationUser",user);
        super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
    }
}
