package com.kuangn.ts_server.dao.testcase;

import com.kuangn.ts_server.dao.JpaDao;
import com.kuangn.ts_server.dao.testcase.TestCaseDao;
import com.kuangn.ts_server.entity.TestCase;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


/**
 * JPA Implementation of a {@link TestCaseDao}.
 * 
 * @author Yanghui.Weng <yhweng@acorn-net.com>
 */
public class JpaTestCaseDao extends JpaDao<TestCase, Long> implements TestCaseDao
{

	public JpaTestCaseDao()
	{
		super(TestCase.class);
	}


	@Override
	@Transactional(readOnly = true)
	public List<TestCase> findAll()
	{
        List<TestCase> listResult = new ArrayList<TestCase>();
		return listResult;
	}

}
