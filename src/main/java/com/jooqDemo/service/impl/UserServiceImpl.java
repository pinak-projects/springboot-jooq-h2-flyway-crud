package com.jooqDemo.service.impl;

import static com.jooq.Tables.USER_MASTER;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jooqDemo.entity.User;
import com.jooqDemo.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private DSLContext dslContext;

	@Override
	public int createUser(final User user) {
		return dslContext.insertInto(USER_MASTER).set(USER_MASTER.NAME, user.getName()).returning(USER_MASTER.USER_ID)
				.fetchOne().getUserId();
	}
	
	@Override
	public User fetchUser(final int userId) {
		return dslContext.select().from(USER_MASTER).where(USER_MASTER.USER_ID.eq(userId)).fetchOne().into(User.class);
	}

	@Override
	public List<User> fetchUsersList() {
		return dslContext.select().from(USER_MASTER).fetch().into(User.class);
	}

	@Override
	public int deleteUser(final int userId) {
		return dslContext.deleteFrom(USER_MASTER).where(USER_MASTER.USER_ID.eq(userId)).execute();
	}

	@Override
	public boolean isUserExistsById(final int userId) {
		return dslContext.fetchExists(dslContext.selectOne().from(USER_MASTER).where(USER_MASTER.USER_ID.eq(userId)));
	}

	@Override
	public int updateUserDetails(final User user) {
		return dslContext.update(USER_MASTER)
				.set(USER_MASTER.NAME, user.getName())
				.where(USER_MASTER.USER_ID.eq(user.getUserId()))
				.execute();
	}

}
