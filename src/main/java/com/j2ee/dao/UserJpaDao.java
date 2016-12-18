package com.j2ee.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.Assert;

import com.j2ee.commons.dao.GenericJpaDao;
import com.j2ee.domain.UserEntity;

/**
 * @author younotimba
 *
 */
public class UserJpaDao extends GenericJpaDao<UserEntity, Long> implements UserDao {

	public UserJpaDao() {
		super(UserEntity.class);

	}

	@Override
	public boolean checkAvailable(String userName) {
		Assert.notNull(userName);

		Query query = getEntityManager().createQuery(
				"select count(*) from " + getPersistentClass().getSimpleName() + " u where u.userName = :userName")
				.setParameter("userName", userName);

		Long count = (Long) query.getSingleResult();

		return count < 1;
	}

	@Override
	public UserEntity loadUSerByUserName(String userName) {
		Assert.notNull(userName);

		UserEntity user = null;

		Query query = getEntityManager()
				.createQuery(
						"select u from " + getPersistentClass().getSimpleName() + " u where u.userName = :userName")
				.setParameter("userName", userName);

		try {
			user = (UserEntity) query.getSingleResult();
		} catch (NoResultException e) {

		}

		return user;
	}

}
