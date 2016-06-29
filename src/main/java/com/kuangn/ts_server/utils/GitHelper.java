package com.kuangn.ts_server.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.ResetCommand;
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
    private String existedTestCaseFile = "testcase.json";
    private String generatedTestCaseFile = "testcase2.json";
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

        // reset git before pull the new code
        // because generate python script has bug which will modify the current testcase json file
        ResetCommand resetCmd = git.reset();
        resetCmd.setMode(ResetCommand.ResetType.HARD);
        resetCmd.call();

        // pull the latest testcase json file
        PullCommand pullCmd = git.pull();
        pullCmd.call();
    }

    public void generateTestCases() throws IOException, InterruptedException {
        //String command = "cd " + localPath + "&&python utils/make_testsuite.py --output " + localPath +"/testcase2.json";
        String command = "python utils/make_testsuite.py --output " + localPath +"/" + generatedTestCaseFile;
        String workDir = localPath;

        try {
            Process process = Runtime.getRuntime().exec(command, null, new File(workDir));
            process.waitFor();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        String[] command = {"python", "utils/make_testsuite.py --output " + localPath +"/testcase2.json"};
//        ProcessBuilder builder = new ProcessBuilder(command);
//        builder.directory(new File(localPath));
//        Process p = builder.start();
//        p.waitFor();
//        p.destroy();
    }

    public String getTestCaseJsonString(String path) throws IOException {

        if (!new File(localPath + path).exists()){
            return null;
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(localPath + path), "UTF-8"));

        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        while((line = bufferedReader.readLine())!=null){
            stringBuffer.append(line).append("\n");
        }

        return stringBuffer.toString();
    }

    public String getCombinedTestCaseJsonString() throws IOException {
        String content1 = getTestCaseJsonString("\\" + existedTestCaseFile);
        String content2 = getTestCaseJsonString("\\" + generatedTestCaseFile);

        if(content1 == null && content2 == null){
            return "[]";
        }else if(content1 == null){
            return content2;
        }else if(content2 == null){
            return content1;
        }else{
            return content1.substring(0, content1.length() - 3) + "," + content2.substring(2, content2.length());
        }
    }
}
