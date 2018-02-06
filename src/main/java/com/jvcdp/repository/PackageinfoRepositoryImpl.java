package com.jvcdp.repository;

import com.jvcdp.model.Packageinfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageinfoRepositoryImpl implements  PackageinfoRepository {

    private  List<Packageinfo> lstPckInfo = new ArrayList<Packageinfo>();

    @Override
    public List<Packageinfo> findAll() {
        return lstPckInfo;
    }

    @Override
    public Packageinfo saveAndFlush(Packageinfo packageinfo) {



        for (Packageinfo pif:lstPckInfo){
            if(pif.getId().equals(packageinfo.getId())){
                return (pif);
            }
        }
        return packageinfo;
    }

    @Override
    public Packageinfo findOne(Long id) {
        for (Packageinfo pif:lstPckInfo){
            if(pif.getId().equals(id)){
                return (pif);
            }
        }
        return null;
    }

    @Override
    public void delete(Packageinfo existingPackageinfo) {
        for (Packageinfo pif:lstPckInfo){
            if(pif.getId().equals(existingPackageinfo.getId())){
                lstPckInfo.remove(pif);
            }
        }
    }
}
