package com.ufund.api.ufundapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Computer;
import com.ufund.api.ufundapi.persistence.ComputerDAO;


import java.io.IOException;
/*
 * Computer Service class for data transfer to and from the model/persistance.
 * 
 */



@RestController
public class ComputerService  {
    private ComputerDAO ComputerDao;

    public ComputerService(ComputerDAO ComputerDao) {
        this.ComputerDao = ComputerDao;
    }
    Computer[] getComputers() throws IOException {
        return ComputerDao.getComputers();
    }
    Computer getComputer(int id) throws IOException {
        return ComputerDao.getComputer(id);
    }
    Computer[] findComputers(String name) throws IOException {
        return ComputerDao.findComputers(name);
    }
    Computer createComputer(Computer computer) throws IOException {
        return ComputerDao.createComputer(computer);
    }
    Computer updateComputer(Computer computer)throws IOException {
        return ComputerDao.updateComputer(computer);
    }
    boolean deleteComputer(int id) throws IOException {
        return ComputerDao.deleteComputer(id);
    }  
}
