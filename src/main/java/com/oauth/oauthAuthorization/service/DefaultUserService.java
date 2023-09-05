package com.oauth.oauthAuthorization.service;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.oauth.oauthAuthorization.dto.UserRegisteredDTO;
import com.oauth.oauthAuthorization.model.User;


public interface DefaultUserService extends UserDetailsService{

    User save(UserRegisteredDTO userRegisteredDTO);

}