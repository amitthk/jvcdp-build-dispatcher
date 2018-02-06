package com.jvcdp.controller;

import com.jvcdp.model.Packageinfo;
import com.jvcdp.model.RepoChangeSet;
import com.jvcdp.repository.PackageinfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("package")
public class PackageinfoController {
    @Autowired
    private PackageinfoRepository packageinfoRepo;

    @GetMapping(value = "")
    public List<Packageinfo> list() {
        return packageinfoRepo.findAll();
    }


    @PostMapping(value = "")
    public Packageinfo create(@RequestBody Packageinfo Packageinfo) {
        return packageinfoRepo.saveAndFlush(Packageinfo);
    }

    @GetMapping(value = "/{id}")
    public Packageinfo get(@PathVariable Long id) {
        return packageinfoRepo.findOne(id);
    }

    @PutMapping(value = "/{id}")
    public Packageinfo update(@PathVariable Long id, @RequestBody Packageinfo Packageinfo) {
        Packageinfo existingPackageinfo;
        existingPackageinfo = packageinfoRepo.findOne(id);
        BeanUtils.copyProperties(Packageinfo, existingPackageinfo);
        return packageinfoRepo.saveAndFlush(existingPackageinfo);
    }

    @DeleteMapping(value = "/{id}")
    public Packageinfo delete(@PathVariable Long id) {
        Packageinfo existingPackageinfo;
        existingPackageinfo = packageinfoRepo.findOne(id);
        packageinfoRepo.delete(existingPackageinfo);
        return existingPackageinfo;
    }

}
