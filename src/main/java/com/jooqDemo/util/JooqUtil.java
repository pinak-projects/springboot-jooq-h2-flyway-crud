package com.jooqDemo.util;

import static com.jooq.Tables.USER_MASTER;

import org.jooq.Record;

import com.jooqDemo.entity.User;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JooqUtil {

	public static final User userRecordMapper(Record userRecord) {
		return User.builder().userId(userRecord.get(USER_MASTER.USER_ID)).name(userRecord.get(USER_MASTER.NAME))
				.build();
	}

}
