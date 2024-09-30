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

//import com.heroes.api.heroesapi.persistence.HeroDAO;

//import com.heroes.api.heroesapi.model.Hero;

@RestController
@RequestMapping("Computer")
public class ComputerController {
<<<<<<< HEAD
    /**
     * Creates a {@linkplain Hero hero} with the provided hero object
     * 
     * @param hero - The {@link Hero hero} to create
     * 
     * @return ResponseEntity with created {@link Hero hero} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Hero hero} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    /* 
    @PostMapping("")
    public ResponseEntity<Needs> createNeeds(@RequestBody Needs hero) {
        LOG.info("POST /heroes " + hero);

        // Replace below with your implementation
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }*/
=======
    private static final Logger LOG = Logger.getLogger(ComputerController.class.getName());
    private CupBoardDAO ComputerDao;

    public ComputerController(CupBoardDAO ComputerDao) {
        this.ComputerDao = ComputerDao;
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
    public ResponseEntity<Needs[]> searchHeroes(@RequestParam String name) {
        LOG.info("GET /heroes/?name="+name);
        try {
            Needs [] needsArray = ComputerDao.findNeeds(name);
            return new ResponseEntity<Needs[]>(needsArray,HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

>>>>>>> 5a5faa6fc5f037e87fe9e72a810a7efb47035c45
}

