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

import com.ufund.api.ufundapi.model.Computer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("computer")
public class ComputerController {
    private static final Logger LOG = Logger.getLogger(ComputerController.class.getName());
    private ComputerService Service;

    public ComputerController(ComputerService Service) {
        this.Service = Service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Computer> getComputer(@PathVariable int id) {
        LOG.info("GET /computer/" + id);
        try {
            Computer computer = Service.getComputer(id);
            if (computer != null)
                return new ResponseEntity<Computer>(computer, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Computer needs} whose name
     * contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the
     *             {@link Computer needs}
     * 
     * @return ResponseEntity with array of {@link Computer needs} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all needs that contain the text "ma"
     *         GET http://localhost:8080/computer/?name=
     */
    @GetMapping("/")
    public ResponseEntity<Computer[]> searchNeeds(@RequestParam String name) {
        LOG.info("GET /computer/?name=" + name);
        try {
            Computer[] needs = Service.findComputers(name);
            if (needs != null)
                return new ResponseEntity<Computer[]>(needs, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Computer computer} with the provided computer object
     * 
     * @param computer - The {@link Computer needs} to create
     * 
     * @return ResponseEntity with created {@link Computer computer} object and HTTP status
     *         of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Computer needs}
     *         object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Computer> createComputer(@RequestBody Computer computer) {
        LOG.info("POST /computer/" + computer);

        try {
            Computer newNeed = Service.createComputer(computer);
            if (newNeed != null)
                return new ResponseEntity<>(newNeed, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Computer computer} with the given id
     * 
     * @param name The id of the {@link Computer computer} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Computer> deleteComputer(@PathVariable int id) {
        LOG.info("DELETE /computer/" + id);
        try {
            boolean isDeleted = Service.deleteComputer(id);
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
    public ResponseEntity<Computer[]> findComputers() {
        LOG.info("GET /computers/");

        // Replace below with your implementation
        try {
            Computer[] needs = Service.getComputers();
            if (needs != null)
                return new ResponseEntity<Computer[]>(needs, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Computer> updateComputer(@RequestBody Computer computer) {
        LOG.info("PUT /computer/ " + computer);

        // Replace below with your implementation
        try {
            Computer updateNeed = Service.updateComputer(computer);
            if (updateNeed != null) {
                return new ResponseEntity<Computer>(updateNeed, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
