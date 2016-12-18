package com.j2ee.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.inputtext.InputText;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.j2ee.dao.UserDao;
import com.j2ee.domain.UserEntity;
import com.j2ee.services.UserService;

/**
 * @author younotimba
 *
 */
public class UserServiceImpl implements UserService, UserDetailsService {

	private UserDao userDao;

	public boolean createUser(UserEntity userEntity) {
		if (!userDao.checkAvailable(userEntity.getUserName())) {
			FacesMessage message = constructErrorMessage(
					String.format("User name %s is not available", userEntity.getUserName()), null);
			getFacesContext().addMessage(null, message);
			return false;
		}

		try {
			userDao.save(userEntity);
		} catch (Exception e) {
			FacesMessage message = constructFatalMessage(e.getMessage(), null);
			getFacesContext().addMessage(null, message);
			return false;
		}
		return true;
	}

	public boolean checkAvailable(AjaxBehaviorEvent event) {

		InputText inputText = (InputText) event.getSource();
		String value = (String) inputText.getValue();

		boolean available = userDao.checkAvailable(value);

		if (!available) {
			FacesMessage message = constructErrorMessage(null, String.format("User name %s is not available", value));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		} else {
			FacesMessage message = constructInfoMessage(null, String.format("User name %s is available", value));
			getFacesContext().addMessage(event.getComponent().getClientId(), message);
		}

		return available;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserEntity user = userDao.loadUSerByUserName(userName);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("No such user %s ", userName));
		}

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		User userDetails = new User(user.getUserName(), user.getPassword(), authorities);

		return userDetails;
	}

	public UserEntity loadUserEntityByUsername(String userName) {
		return userDao.loadUSerByUserName(userName);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	protected FacesMessage constructErrorMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail);
	}

	protected FacesMessage constructInfoMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_INFO, message, detail);
	}

	protected FacesMessage constructFatalMessage(String message, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_FATAL, message, detail);
	}

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}
