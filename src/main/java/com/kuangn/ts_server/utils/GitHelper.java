package com.kuangn.ts_server.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import sun.misc.IOUtils;

import java.io.*;

/**
 * Created by yhweng@acorn-net on 2016/6/17.
 */
public class GitHelper {

    private String localPath = "E:\\testcases_repo";
    private String remotePath = "git@172.17.3.60:ts-protocol/ts-testcases.git";
    private Repository localRepo;
    private Git git;



    public void initGitRepo() throws GitAPIException {
        Git git = Git.cloneRepository()
                .setURI(remotePath)
                .setDirectory(new File(localPath))
                .call();
    }


    public void pullGitRepo() throws IOException, GitAPIException {
        FileRepository localRepo = new FileRepository(localPath + "/.git");

        Git git = new Git(localRepo);
        PullCommand pullCmd = git.pull();
        pullCmd.call();
    }


    public String getTestCaseJsonString(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(localPath + path), "UTF-8"));

        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        while((line = bufferedReader.readLine())!=null){

            stringBuffer.append(line).append("\n");
        }

        return stringBuffer.toString();
    }
}
