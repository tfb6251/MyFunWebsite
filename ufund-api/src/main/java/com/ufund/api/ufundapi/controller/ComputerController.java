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
                return new ResponseEntity<Needs>(need, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Responds to the GET request for all {@linkplain Needs needs} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Needs needs}
     * 
     * @return ResponseEntity with array of {@link Needs needs} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all needs that contain the text "ma"
     * GET http://localhost:8080/needs/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Needs[]> searchNeeds(@RequestParam String name) {
        LOG.info("GET /needs/?name="+name);
        try {
        Needs[] needs = ComputerDao.findNeeds(name);
            if (needs != null)
                return new ResponseEntity<Needs[]>(needs,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Needs need} with the provided need object
     * 
     * @param need - The {@link Needs needs} to create
     * 
     * @return ResponseEntity with created {@link Needs need} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Needs needs} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Needs> createNeeds(@RequestBody Needs needs) {
        LOG.info("POST /Computer " + needs);

        try {
            Needs need = new Needs(null, 0, 0, null);
            Needs createdNeed = ComputerDao.createNeeds(need);

            if (createdNeed != null)
                return new ResponseEntity<>(createdNeed, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Deletes a {@linkplain Needs need} with the given id
     * 
     * @param name The id of the {@link Needs need} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Needs> deleteHero(@PathVariable String name) {
        LOG.info("DELETE /Computer" + name);
        try {
            boolean isDeleted = ComputerDao.deleteNeeds(name);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("")
    public ResponseEntity<Needs[]> getNeedsArray() {
        LOG.info("GET /Computers");

        // Replace below with your implementation
        try {
            Needs[] needs = ComputerDao.getNeedsArray();
            if (needs != null)
                return new ResponseEntity<Needs[]>(needs, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    

}
