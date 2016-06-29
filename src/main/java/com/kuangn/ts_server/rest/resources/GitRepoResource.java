package com.kuangn.ts_server.rest.resources;

import com.kuangn.ts_server.rest.resources.types.FancyBoolean;
import com.kuangn.ts_server.utils.GitHelper;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


@Component
@Path("/git-repo")
public class GitRepoResource
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String create() throws JsonGenerationException, JsonMappingException, IOException
    {
        this.logger.info("list()");
        return null;
    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list(@QueryParam("pull") FancyBoolean fancyBoolean) throws JsonGenerationException, JsonMappingException, IOException, GitAPIException, InterruptedException {

        //this.logger.info("rest get @ git-repo, pull = " + fancyBoolean.getValue());

        GitHelper gitHelper = new GitHelper();

        //if(fancyBoolean.getValue()){
            this.logger.info("start pulling files ...");
            gitHelper.pullGitRepo();
            this.logger.info("finish pulling files ...");

            gitHelper.generateTestCases();
        //}

		return gitHelper.getCombinedTestCaseJsonString();
	}

}