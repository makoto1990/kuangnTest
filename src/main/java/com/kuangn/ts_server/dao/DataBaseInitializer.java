package com.kuangn.ts_server.dao;

import java.util.Date;

import com.kuangn.ts_server.dao.newsentry.NewsEntryDao;
import com.kuangn.ts_server.entity.NewsEntry;
import com.kuangn.ts_server.entity.Role;
import com.kuangn.ts_server.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kuangn.ts_server.dao.user.UserDao;


/**
 * Initialize the database with some test entries.
 *
 * @author Yanghui.Weng <yhweng@acorn-net.com>
 */
public class DataBaseInitializer
{

	private NewsEntryDao newsEntryDao;

	private UserDao userDao;

	private PasswordEncoder passwordEncoder;


	protected DataBaseInitializer()
	{
		/* Default constructor for reflection instantiation */
	}

	public DataBaseInitializer(UserDao userDao, NewsEntryDao newsEntryDao, PasswordEncoder passwordEncoder)
	{
		this.userDao = userDao;
		this.newsEntryDao = newsEntryDao;
		this.passwordEncoder = passwordEncoder;
	}

	public void initDataBase()
	{
		// check if database is exist
        User findUser = this.userDao.findByName("admin");
        if(findUser != null)return;

        // create the test data
		User userUser = new User("user", this.passwordEncoder.encode("user"));
		userUser.addRole(Role.USER);
		this.userDao.save(userUser);

		User adminUser = new User("admin", this.passwordEncoder.encode("admin"));
		adminUser.addRole(Role.USER);
		adminUser.addRole(Role.ADMIN);
		this.userDao.save(adminUser);

		long timestamp = System.currentTimeMillis() - (1000 * 60 * 60 * 24);
		for (int i = 0; i < 10; i++) {
			NewsEntry newsEntry = new NewsEntry();
			newsEntry.setContent("This is example content " + i);
			newsEntry.setDate(new Date(timestamp));
			this.newsEntryDao.save(newsEntry);
			timestamp += 1000 * 60 * 60;
		}
	}

}