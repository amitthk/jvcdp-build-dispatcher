package com.jvcdp.repository;


import com.jvcdp.model.Packageinfo;

import java.util.List;

public interface PackageinfoRepository {

    List<Packageinfo> findAll();

    Packageinfo saveAndFlush(Packageinfo packageinfo);

    Packageinfo findOne(Long id);

    void delete(Packageinfo existingPackageinfo);
}
