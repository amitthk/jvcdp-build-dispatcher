package com.jvcdp.repository;

import com.jvcdp.model.RepoChangeSet;
import com.jvcdp.model.RepoChangeSetSsh;

public interface GitInterfaceRepository {
    RepoChangeSet pushChanges(RepoChangeSet repoChangeSet);

    RepoChangeSetSsh pushChanges(RepoChangeSetSsh repoChangeSetSsh);
}
