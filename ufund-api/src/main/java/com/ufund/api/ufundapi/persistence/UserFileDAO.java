package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;

@Component
public class UserFileDAO implements UserDAO {
    Map<Integer, User> userMap;
    private ObjectMapper objectMapper;
    private String filename;
    private static int nextId;

    public UserFileDAO(@Value("${user.file}") String filename, ObjectMapper objectMapper)
        throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        if (!new File(filename).exists()) {
            new File(filename).getParentFile().mkdirs();  // Create parent directories if they don’t exist
            new File(filename).createNewFile();           // Create the new file
            try (FileWriter writer = new FileWriter(filename)) {
                writer.write("[]");             // Initialize with an empty JSON array
            }
        }
        load();
    }
 
    /**
     * Generates the next id for a new {@linkplain Hero hero}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private boolean save() throws IOException {
        User[] needsArray = getUsers();
        
        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), needsArray);
        return true;
    }
    
    private boolean load() throws IOException {
        userMap = new TreeMap<>();
        nextId = 0;
        
        User[] needsArray = objectMapper.readValue(new File(filename), User[].class);
        
        for (User need : needsArray) {
            userMap.put(need.getId(), need);
            if (need.getId() > nextId)
                nextId = need.getId();
        }
        ++nextId;
        return true;
    }

    @Override
    public User getUser(int id) {
        synchronized (userMap) {
            if (userMap.containsKey(id))
                return userMap.get(id);
            else
                return null;
        }
    }
    
    @Override
    public User[] getUsers() {
        return findUsers(null);
    }

    public User[] findUsers(String containsText) {
        synchronized (userMap) {
            ArrayList<User> userList = new ArrayList<>();
            for (User userMap : userMap.values()) {
                if (containsText == null || userMap.getName().contains(containsText)) {
                    userList.add(userMap);
                }
            }
    
            User[] needsarray = new User[userList.size()];
            userList.toArray(needsarray);
            return needsarray;
        }
    }

    public User createUser(User user) throws IOException {
        synchronized (userMap) {
            User newUser = new User(nextId(), user.getName(),
             user.getPassword(), user.getBasket());
            
            userMap.put(newUser.getId(),newUser);
            save(); 

            return newUser;
        }
    }

    public User updateUser(User user) throws IOException {
        synchronized (userMap) {

            int key = user.getId();
            if (userMap.containsKey(key) == false) {
                return null;
            }
            userMap.put(key, user);
            save();
            return user;
        }

    }

    public boolean deleteUser(int id) throws IOException {
        synchronized (userMap) {
            if (userMap.containsKey(id)) {
                userMap.remove(id);
                if (id+1 == nextId) {
                    nextId--;
                }
                return save();
            } else {
                return false;
            }

        }
    }
}
