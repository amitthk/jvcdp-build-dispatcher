package com.jvcdp.controller;

import com.jvcdp.model.CommandInfo;
import com.jvcdp.model.Packageinfo;
import com.jvcdp.model.RepoChangeSet;
import com.jvcdp.model.RepoChangeSetSsh;
import com.jvcdp.repository.CmdProcessor;
import com.jvcdp.repository.GitInterfaceRepository;
import com.jvcdp.repository.PackageinfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("home")
public class HomeController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home() {
		return "Command Line/Git Job Dispatcher Service!";
	}


	@Autowired
	private GitInterfaceRepository gitInterfaceRepository;

	@Autowired
	private  CmdProcessor cmdProcessor;

	@PostMapping(value = "")
	public CommandInfo create(@RequestBody CommandInfo cmdInfo) { ;
		CommandInfo rsl = cmdProcessor.RunCommand(cmdInfo);
		return rsl;
	}

	@PutMapping(value = "")
	public RepoChangeSet puschanges(@RequestBody RepoChangeSet repoChangeSet){
		return gitInterfaceRepository.pushChanges(repoChangeSet);
	}

	@PutMapping(value = "/ssh")
	public RepoChangeSetSsh puschanges(@RequestBody RepoChangeSetSsh repoChangeSetSsh){
		return gitInterfaceRepository.pushChanges(repoChangeSetSsh);
	}
}
