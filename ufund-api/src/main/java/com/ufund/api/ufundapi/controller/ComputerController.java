package com.ufund.api.ufundapi.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Needs;
import com.ufund.api.ufundapi.persistence.CupBoardDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.model.Needs;

@RestController
@RequestMapping("Computer")
public class ComputerController {
    private static final Logger LOG = Logger.getLogger(ComputerController.class.getName());
    private CupBoardDAO ComputerDao;

    public ComputerController(CupBoardDAO ComputerDao) {
        this.ComputerDao = ComputerDao;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Needs> getNeed(@PathVariable String name) {
        LOG.info("GET /Computer/" + name);
        try {
        Needs need = ComputerDao.getNeed(name);
            if (need != null)
                return new ResponseEntity<Needs>(need,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
