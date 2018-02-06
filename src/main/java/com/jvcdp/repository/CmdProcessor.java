package com.jvcdp.repository;
import com.jvcdp.model.CommandInfo;
import com.jvcdp.utility.OsHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CmdProcessor {

    @Value("${app.git.remote.repo.url}")
    private String gitRepoRemoteUrl;

    public CommandInfo RunCommand(CommandInfo cmdInfo) {

        CmdStreamReader is = null;
        CmdStreamReader es = null;
        File baseDir =OsHelper.getResource("./scripts");
        File scriptFile= (OsHelper.isWindows())?OsHelper.getResource("scripts/command.bat"):OsHelper.getResource("scripts/script.sh");

        Process process=null;
        Process helperprocess=null;
        try {


            File tmpfile = new File(OsHelper.getResource(".") +"/scripts/tmp.properties");
            FileOutputStream tmpFileO = new FileOutputStream(tmpfile);
            cmdInfo.getPropertiesToUpdate().store(tmpFileO,"update");
            tmpFileO.close();

            List<String> lstCommands = new ArrayList<>();
            lstCommands.add(scriptFile.getPath());  //

            lstCommands.add(gitRepoRemoteUrl);// Parameter 1



            if (!OsHelper.isWindows()){
                ProcessBuilder processBuilder1 = new ProcessBuilder();
                processBuilder1.command("/bin/sh","chmod","777",OsHelper.getResource("scripts/script.sh").getPath());
                helperprocess = (processBuilder1).start();
                helperprocess.waitFor();
            }

            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(lstCommands);
            processBuilder.directory(baseDir);
            process = (processBuilder).start();
            is = new CmdStreamReader("stdin", process.getInputStream ());
            es = new CmdStreamReader("stderr", process.getErrorStream ());
            is.start();
            es.start();
            int exitCode=process.waitFor();

            cmdInfo.setExitCode(exitCode);
            if (exitCode == 0)
            {cmdInfo.setStatusText("It Worked: " + is.output.toString());}
            else
            {cmdInfo.setStatusText("Something bad happend. Exit code: " + exitCode);
                cmdInfo.setErrorLog(es.output.toString());}
        } //try
        catch (Exception e) {
            cmdInfo.setStatusText("Something when wrong: " + e.getMessage());
            e.printStackTrace();
        } //catch
        finally {
            if(process != null)
                process.destroy();
            if(helperprocess!=null)
                helperprocess.destroy();

        }
        return  cmdInfo;
    }

}
