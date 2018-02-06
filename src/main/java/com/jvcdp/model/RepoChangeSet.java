package com.jvcdp.model;

import java.util.Properties;

public class RepoChangeSet {
    private String RemoteRepoURL;
    private String LocalRepoPath;
    private String userId;
    private String userPassword;
    private String propertiesFilePath;
    private Properties propertiesToUpdate;
    private String commitMessage;
    private String branchName;

    public String getRemoteRepoURL() {
        return RemoteRepoURL;
    }

    public void setRemoteRepoURL(String remoteRepoURL) {
        RemoteRepoURL = remoteRepoURL;
    }

    public String getLocalRepoPath() {
        return LocalRepoPath;
    }

    public void setLocalRepoPath(String localRepoPath) {
        LocalRepoPath = localRepoPath;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getPropertiesFilePath() {
        return propertiesFilePath;
    }

    public void setPropertiesFilePath(String propertiesFilePath) {
        this.propertiesFilePath = propertiesFilePath;
    }

    public Properties getPropertiesToUpdate() {
        return propertiesToUpdate;
    }

    public void setPropertiesToUpdate(Properties propertiesToUpdate) {
        this.propertiesToUpdate = propertiesToUpdate;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
