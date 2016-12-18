package com.j2ee.services;

import javax.faces.event.AjaxBehaviorEvent;

import com.j2ee.domain.UserEntity;

/**
 * @author younotimba
 *
 */
public interface UserService {

	boolean createUser(UserEntity userEntity);

	boolean checkAvailable(AjaxBehaviorEvent ajaxEvent);

	/**
	 * Testing docs descriprion
	 * 
	 * Method loading UserEntity by user name
	 * 
	 */
	UserEntity loadUserEntityByUsername(String userName);
}
