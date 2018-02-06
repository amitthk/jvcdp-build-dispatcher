package com.jvcdp.model;

import java.util.Properties;

public class CommandInfo {
    private String commands[];
    private String parameters[];
    private  int exitCode;
    private String successLog;
    private String errorLog;
    private String statusText;
    private Properties propertiesToUpdate;

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public String getSuccessLog() {
        return successLog;
    }

    public void setSuccessLog(String successLog) {
        this.successLog = successLog;
    }

    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }

    public String[] getCommands() {
        return commands;
    }

    public void setCommands(String[] commands) {
        this.commands = commands;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Properties getPropertiesToUpdate() {
        return propertiesToUpdate;
    }

    public void setPropertiesToUpdate(Properties propertiesToUpdate) {
        this.propertiesToUpdate = propertiesToUpdate;
    }
}
