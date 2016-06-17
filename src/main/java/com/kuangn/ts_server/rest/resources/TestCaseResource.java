package com.kuangn.ts_server.rest.resources;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.kuangn.ts_server.JsonViews;
import com.kuangn.ts_server.dao.testcase.TestCaseDao;
import com.kuangn.ts_server.entity.Role;
import com.kuangn.ts_server.entity.TestCase;
import com.kuangn.ts_server.utils.GitHelper;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@Path("/testcase")
public class TestCaseResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestCaseDao testCaseDao;

    @Autowired
    private ObjectMapper mapper;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list() throws JsonGenerationException, JsonMappingException, IOException, GitAPIException {
        this.logger.info("list()");

//        this.logger.info("git pull started ....");
//
//        String testCaseString = new GitHelper().getTestCaseJsonString("\\testcase.json");
//
//        this.logger.info("git pull finished ....");
//
//        return testCaseString;

        ObjectWriter viewWriter;
        if (this.isAdmin()) {
            viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
        } else {
            viewWriter = this.mapper.writerWithView(JsonViews.User.class);
        }
        List<TestCase> allEntries = this.testCaseDao.findAll();

        return viewWriter.writeValueAsString(allEntries);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public TestCase read(@PathParam("id") Long id)
    {
        this.logger.info("read(id)");

        TestCase newsEntry = this.testCaseDao.find(id);
        if (newsEntry == null) {
            throw new WebApplicationException(404);
        }
        return newsEntry;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TestCase create(TestCase newsEntry)
    {
        this.logger.info("create(): " + newsEntry);

        return this.testCaseDao.save(newsEntry);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public TestCase update(@PathParam("id") Long id, TestCase testCase)
    {
        this.logger.info("update(): " + testCase);

        return this.testCaseDao.save(testCase);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void delete(@PathParam("id") Long id)
    {
        this.logger.info("delete(id)");

        this.testCaseDao.delete(id);
    }

    private boolean isAdmin()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;

        return userDetails.getAuthorities().contains(Role.ADMIN);
    }
}
