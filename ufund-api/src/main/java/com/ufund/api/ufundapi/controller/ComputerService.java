package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.persistence.CupBoardDAO;
import com.ufund.api.ufundapi.model.Needs;

import java.io.IOException;

public class ComputerService {
    private CupBoardDAO ComputerDao;

    public ComputerService(CupBoardDAO ComputerDao) {
        this.ComputerDao = ComputerDao;
    }
    Needs[] getNeedsArray(String containText) throws IOException {
        return getNeedsArray(containText);
    }
    Needs[] getNeedsArray() throws IOException {
        return getNeedsArray();
    }
    Needs getNeed(String name) throws IOException {
        return ComputerDao.getNeed(name);
    }
    Needs[] findNeeds(String name) throws IOException {
        return findNeeds(name);
    }
    Needs createNeeds(Needs need) throws IOException {
        return createNeeds(need);
    }
    Needs updateNeeds(Needs need)throws IOException {
        return updateNeeds(need);
    }
    boolean deleteNeeds(String name) throws IOException {
        return deleteNeeds(name);
    }  
}
