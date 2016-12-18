package com.j2ee.dao;

import com.j2ee.commons.dao.GenericDao;
import com.j2ee.domain.UserEntity;

/**
 * @author younotimba
 *
 */
public interface UserDao extends GenericDao<UserEntity, Long> {

	boolean checkAvailable(String userName);

	UserEntity loadUSerByUserName(String userName);
}
