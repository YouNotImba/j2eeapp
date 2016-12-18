package com.j2ee.services.impl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.j2ee.domain.UserEntity;
import com.j2ee.services.UserAuthenticationProviderService;

/**
 * @author younotimba
 *
 */
public class UserAuthenticationProviderServiceImpl implements UserAuthenticationProviderService {

	private AuthenticationManager authenticationManager;

	@Override
	public boolean processUserAuthentication(UserEntity userEntity) {
		try {
			Authentication request = new UsernamePasswordAuthenticationToken(userEntity.getUserName(),
					userEntity.getPassword());
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			return true;
		} catch (AuthenticationException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Sorry!"));
			return false;
		}
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

}
