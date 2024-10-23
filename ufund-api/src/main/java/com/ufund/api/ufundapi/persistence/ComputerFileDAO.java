package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Computer;

@Component
public class ComputerFileDAO implements ComputerDAO {
    Map<String, Computer> computerMap;
    private ObjectMapper objectMapper;
    private String filename;

    public ComputerFileDAO(@Value("${computerMap.file}") String filename, ObjectMapper objectMapper)
            throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        
        load();
    }
 
    private boolean save() throws IOException {
        Computer[] needsArray = findComputers();
        
        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), needsArray);
        return true;
    }
    
    private boolean load() throws IOException {
        computerMap = new TreeMap<>();
        
        Computer[] needsArray = objectMapper.readValue(new File(filename), Computer[].class);
        
        for (Computer needs : needsArray) {
            computerMap.put(needs.getName(), needs);
        }
        return true;
    }

    @Override
    public Computer getComputer(String name) {
        synchronized (computerMap) {
            if (computerMap.containsKey(name))
                return computerMap.get(name);
            else
                return null;
        }
    }
    
    @Override
    public Computer[] findComputers() {
        return findComputers(null);
    }

    public Computer[] findComputers(String containsText) {
        synchronized (computerMap) {
            ArrayList<Computer> computerList = new ArrayList<>();
            for (Computer computerMap : computerMap.values()) {
                if (containsText == null || computerMap.getName().contains(containsText)) {
                    computerList.add(computerMap);
                }
            }
    
            Computer[] needsarray = new Computer[computerList.size()];
            computerList.toArray(needsarray);
            return needsarray;
        }
    }

    public Computer createComputer(Computer computer) throws IOException {
        synchronized (computerMap) {
            // We create a new hero object because the id field is immutable
            // and we need to assign the next unique id
            String key = computer.getName();
            if (!(this.computerMap.containsKey(key))) {
                this.computerMap.put(key, computer);
                save();
                return computer;
            }
            return null;
        }
    }

    public Computer updateComputer(Computer computer) throws IOException {
        synchronized (computerMap) {

            String key = computer.getName();
            if (computerMap.containsKey(key) == false) {
                return null;
            }
            computerMap.put(key, computer);
            save();
            return computer;
        }

    }

    public boolean deleteComputer(String name) throws IOException {
        synchronized (computerMap) {
            if (computerMap.containsKey(name)) {
                computerMap.remove(name);
                return save();
            } else {
                return false;
            }

        }
    }
}
