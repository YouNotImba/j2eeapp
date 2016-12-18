package com.j2ee.services;

import com.j2ee.domain.UserEntity;

/**
 * @author younotimba
 *
 */
public interface UserAuthenticationProviderService {

	boolean processUserAuthentication(UserEntity userEntity);
}
