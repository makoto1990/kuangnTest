package com.kuangn.ts_server.dao.user;

import com.kuangn.ts_server.dao.Dao;
import com.kuangn.ts_server.entity.User;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserDao extends Dao<User, Long>, UserDetailsService
{

	User findByName(String name);

}