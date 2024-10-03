package com.ufund.api.ufundapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Needs;
import com.ufund.api.ufundapi.persistence.CupBoardDAO;


import java.io.IOException;
/*
 * Computer Service class for data transfer to and from the model/persistance.
 * 
 */



@RestController
public class ComputerService  {
    private CupBoardDAO ComputerDao;

    public ComputerService(CupBoardDAO ComputerDao) {
        this.ComputerDao = ComputerDao;
    }
    Needs[] getNeedsArray(String containText) throws IOException {
        return ComputerDao.getNeedsArray(containText);
    }
    Needs[] getNeedsArray() throws IOException {
        return ComputerDao.getNeedsArray();
    }
    Needs getNeed(String name) throws IOException {
        return ComputerDao.getNeed(name);
    }
    Needs[] findNeeds(String name) throws IOException {
        return ComputerDao.findNeeds(name);
    }
    Needs createNeeds(Needs need) throws IOException {
        return ComputerDao.createNeeds(need);
    }
    Needs updateNeeds(Needs need)throws IOException {
        return ComputerDao.updateNeeds(need);
    }
    boolean deleteNeeds(String name) throws IOException {
        return ComputerDao.deleteNeeds(name);
    }  
}
