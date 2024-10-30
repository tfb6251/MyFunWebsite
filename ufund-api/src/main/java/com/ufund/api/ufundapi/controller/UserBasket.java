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

import com.ufund.api.ufundapi.model.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("user")
public class UserBasket{
    private static final Logger LOG = Logger.getLogger(UserBasket.class.getName());
    private UserService Service;

    public UserBasket(UserService Service) {
        this.Service = Service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        LOG.info("GET /user/" + id);
        try {
            User user = Service.getUser(id);
            if (user != null)
                return new ResponseEntity<User>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain User needs} whose name
     * contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the
     *             {@link User needs}
     * 
     * @return ResponseEntity with array of {@link User needs} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all needs that contain the text "ma"
     *         GET http://localhost:8080/user/?name=
     */
    @GetMapping("/")
    public ResponseEntity<User[]> searchNeeds(@RequestParam String name) {
        LOG.info("GET /user/?name=" + name);
        try {
            User[] needs = Service.findUsers(name);
            if (needs != null)
                return new ResponseEntity<User[]>(needs, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain User user} with the provided user object
     * 
     * @param user - The {@link User needs} to create
     * 
     * @return ResponseEntity with created {@link User user} object and HTTP status
     *         of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link User needs}
     *         object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /user/" + user);

        try {
            User newNeed = Service.createUser(user);
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
     * Deletes a {@linkplain User user} with the given id
     * 
     * @param name The id of the {@link User user} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        LOG.info("DELETE /user/" + id);
        try {
            boolean isDeleted = Service.deleteUser(id);
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
    public ResponseEntity<User[]> findUsers() {
        LOG.info("GET /users/");

        // Replace below with your implementation
        try {
            User[] needs = Service.getUsers();
            if (needs != null)
                return new ResponseEntity<User[]>(needs, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        LOG.info("PUT /user/ " + user);

        // Replace below with your implementation
        try {
            User updateNeed = Service.updateUser(user);
            if (updateNeed != null) {
                return new ResponseEntity<User>(updateNeed, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
