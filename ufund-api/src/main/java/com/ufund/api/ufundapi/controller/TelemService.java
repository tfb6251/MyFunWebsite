package com.ufund.api.ufundapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Telem;
import com.ufund.api.ufundapi.persistence.TelemDAO;

import java.io.IOException;

@RestController
public class TelemService {
    private TelemDAO TelemDao;

    public TelemService(TelemDAO TelemDao) {
        this.TelemDao = TelemDao;
    }
    Telem[] getTelems() throws IOException {
        return TelemDao.getTelems();
    }
    Telem getTelem(int id) throws IOException {
        return TelemDao.getTelem(id);
    }
    Telem[] findTelems(String type, float term) throws IOException {
        return TelemDao.findTelems(type, term);
    }
    Telem createTelem(Telem Telem) throws IOException {
        return TelemDao.createTelem(Telem);
    }
    Telem updateTelem(Telem Telem)throws IOException {
        return TelemDao.updateTelem(Telem);
    }
    boolean deleteTelem(int id) throws IOException {
        return TelemDao.deleteTelem(id);
    }  
}
