package com.kuangn.ts_server.dao;

import java.util.List;

import com.kuangn.ts_server.entity.Entity;


public interface Dao<T extends Entity, I>
{

	List<T> findAll();


	T find(I id);


	T save(T entry);


	void delete(I id);

}