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

import com.ufund.api.ufundapi.model.Telem;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("telem")

public class TelemController {
    private static final Logger LOG = Logger.getLogger(TelemController.class.getName());
    private TelemService Service;

    public TelemController(TelemService Service) {
        this.Service = Service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Telem> getTelem(@PathVariable int id) {
        LOG.info("GET /Telem/" + id);
        try {
            Telem Telem = Service.getTelem(id);
            if (Telem != null)
                return new ResponseEntity<Telem>(Telem, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Telem[]> searchTelems(@RequestParam String type, @PathVariable float term) {
        LOG.info("GET /Telem/?query=" + type + term);
        try {
            Telem[] needs = Service.findTelems(type, term);
            if (needs != null)
                return new ResponseEntity<Telem[]>(needs, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Telem> createTelem(@RequestBody Telem Telem) {
        LOG.info("POST /Telem/" + Telem);

        try {
            Telem newNeed = Service.createTelem(Telem);
            if (newNeed != null)
                return new ResponseEntity<>(newNeed, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Telem> deleteTelem(@PathVariable int id) {
        LOG.info("DELETE /Telem/" + id);
        try {
            boolean isDeleted = Service.deleteTelem(id);
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
    public ResponseEntity<Telem[]> findTelems() {
        LOG.info("GET /Telems/");

        try {
            Telem[] needs = Service.getTelems();
            if (needs != null)
                return new ResponseEntity<Telem[]>(needs, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Telem> updateTelem(@RequestBody Telem Telem) {
        LOG.info("PUT /Telem/ " + Telem);

        try {
            Telem updateNeed = Service.updateTelem(Telem);
            if (updateNeed != null) {
                return new ResponseEntity<Telem>(updateNeed, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
