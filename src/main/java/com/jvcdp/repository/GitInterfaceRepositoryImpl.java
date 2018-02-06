package com.jvcdp.repository;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jvcdp.model.RepoChangeSet;
import com.jvcdp.model.RepoChangeSetSsh;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.util.FS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

@Service
public class GitInterfaceRepositoryImpl implements  GitInterfaceRepository {

    @Value("${app.git.remote.repo.url}")
    private String remoteRepoURL;

    @Value("${app.git.localrepopath}")
    private String localRepoPath;

    @Value("${app.git.branchname}")
    private String gitRepoBranchName;

    @Value("${app.git.https.userid}")
    private String httpsUserId;

    @Value("${app.git.https.password}")
    private String httpsUserPassword;

    @Value("${app.git.ssh.sshprivatekeylocalpath}")
    private String sshPrivateKeyLocalPath;


    @Override
    public RepoChangeSet pushChanges(RepoChangeSet repoChangeSet) {

        String _remoteRepoURL=repoChangeSet.getRemoteRepoURL().isEmpty()?remoteRepoURL:repoChangeSet.getRemoteRepoURL();

        String _localRepoPath= repoChangeSet.getLocalRepoPath().isEmpty()?localRepoPath:repoChangeSet.getLocalRepoPath();

        String _gitRepoBranchName = repoChangeSet.getBranchName().isEmpty()?gitRepoBranchName:repoChangeSet.getBranchName();

        String _httpsUserId=repoChangeSet.getUserId().isEmpty()?httpsUserId:repoChangeSet.getUserId();

        String _httpsUserPassword=repoChangeSet.getUserPassword().isEmpty()?httpsUserPassword:repoChangeSet.getUserPassword();


        String _propertiesFilePath = repoChangeSet.getPropertiesFilePath();
        Properties _propertiesToUpdate = repoChangeSet.getPropertiesToUpdate();
        String _commitMessage = repoChangeSet.getCommitMessage();

        try {
            CloneCommand cloneCommand =Git.cloneRepository();
                cloneCommand.setBranch(String.format("refs/heads/{0}",_gitRepoBranchName))
                .setURI(_remoteRepoURL)
                .setDirectory(new File(_localRepoPath)).call();

        Git localGit = Git.open(new File(_localRepoPath));
        localGit.checkout().setName(String.format("origin/{0}",_gitRepoBranchName))
                .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK).call();

            performLocalChanges(_localRepoPath, _propertiesFilePath, _propertiesToUpdate);

            localGit.add().addFilepattern(".").call();
            localGit.commit().setMessage(_commitMessage).call();
            Iterable<PushResult> call;
            call = localGit.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(_httpsUserId, _httpsUserPassword)).call();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException ioexcp){
            ioexcp.printStackTrace();
        } catch (InvalidRemoteException e) {
            e.printStackTrace();
        } catch (InvalidRefNameException e) {
            e.printStackTrace();
        } catch (CheckoutConflictException e) {
            e.printStackTrace();
        } catch (RefAlreadyExistsException e) {
            e.printStackTrace();
        } catch (TransportException e) {
            e.printStackTrace();
        } catch (RefNotFoundException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        return repoChangeSet;
    }

    public RepoChangeSetSsh pushChanges (RepoChangeSetSsh repoChangeSetSsh){

        String _sshPrivateKeyLocalPath=repoChangeSetSsh.getSshPrivateKeyLocalPath().isEmpty()?sshPrivateKeyLocalPath:repoChangeSetSsh.getSshPrivateKeyLocalPath();
        String _remoteRepoURL= repoChangeSetSsh.getRemoteRepoURL().isEmpty()?remoteRepoURL:repoChangeSetSsh.getRemoteRepoURL();

        String _localRepoPath= repoChangeSetSsh.getLocalRepoPath().isEmpty()?localRepoPath:repoChangeSetSsh.getLocalRepoPath();

        String _gitRepoBranchName = repoChangeSetSsh.getBranchName().isEmpty()?gitRepoBranchName:repoChangeSetSsh.getBranchName();

        String _propertiesFilePath = repoChangeSetSsh.getPropertiesFilePath();
        Properties _propertiesToUpdate = repoChangeSetSsh.getPropertiesToUpdate();
        String _commitMessage = repoChangeSetSsh.getCommitMessage();

        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session ) {
                // Disable the StrictHostKeyChecking
                session.setConfig("StrictHostKeyChecking", "no");
            }

            @Override
            protected JSch createDefaultJSch(FS fs ) throws JSchException {
                JSch defaultJSch = super.createDefaultJSch( fs );
                defaultJSch.addIdentity( _sshPrivateKeyLocalPath );
                return defaultJSch;
            }
        };

        try {
        CloneCommand cloneCommand =Git.cloneRepository();
        cloneCommand.setBranch(String.format("refs/heads/{0}",_gitRepoBranchName))
        .setURI(_remoteRepoURL)
        .setDirectory(new File(_localRepoPath)).call();
        cloneCommand.setTransportConfigCallback(new TransportConfigCallback()  {
            @Override
            public void configure( Transport transport ) {
                SshTransport sshTransport = ( SshTransport )transport;
                sshTransport.setSshSessionFactory( sshSessionFactory );
            }
        });


            Git localGit = null;

            localGit = Git.open(new File(_localRepoPath));


        localGit.checkout().setName(String.format("origin/{0}",_gitRepoBranchName))
                .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK).call();

        performLocalChanges(_localRepoPath,_propertiesFilePath,_propertiesToUpdate);

        localGit.add().addFilepattern(".").call();
        localGit.commit().setMessage(_commitMessage).call();
        Iterable<PushResult> call;
        call = localGit.push().call();

    } catch (IOException e) {
        e.printStackTrace();
    } catch (InvalidRemoteException e) {
            e.printStackTrace();
        } catch (InvalidRefNameException e) {
            e.printStackTrace();
        } catch (CheckoutConflictException e) {
            e.printStackTrace();
        } catch (RefAlreadyExistsException e) {
            e.printStackTrace();
        } catch (TransportException e) {
            e.printStackTrace();
        } catch (RefNotFoundException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return repoChangeSetSsh;
    }

    private void performLocalChanges(String _localRepoPath, String _propertiesFilePath, Properties _propertiesToUpdate) throws IOException {
        FileInputStream propFile = null;
        propFile = new FileInputStream(_localRepoPath+_propertiesFilePath);
        Properties props = new Properties();
        props.load(propFile);

        Enumeration<Object> keysEnumeration = props.keys();
        while (keysEnumeration.hasMoreElements()) {
            //System.out.println(valueEnumeration.nextElement());
            if(_propertiesToUpdate.containsKey(keysEnumeration.nextElement().toString())){
                props.setProperty(keysEnumeration.nextElement().toString(),_propertiesToUpdate.getProperty(keysEnumeration.nextElement().toString()));
            }
        }

        props.store(new FileWriter(_propertiesFilePath),"updated properties as it is");
    }
}
