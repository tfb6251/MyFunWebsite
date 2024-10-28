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
    Map<Integer, Computer> computerMap;
    private ObjectMapper objectMapper;
    private String filename;
    private static int nextId;

    public ComputerFileDAO(@Value("${computer.file}") String filename, ObjectMapper objectMapper)
        throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        
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
        Computer[] needsArray = getComputers();
        
        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), needsArray);
        return true;
    }
    
    private boolean load() throws IOException {
        computerMap = new TreeMap<>();
        nextId = 0;
        
        Computer[] needsArray = objectMapper.readValue(new File(filename), Computer[].class);
        
        for (Computer need : needsArray) {
            computerMap.put(need.getId(), need);
            if (need.getId() > nextId)
                nextId = need.getId();
        }
        ++nextId;
        return true;
    }

    @Override
    public Computer getComputer(int id) {
        synchronized (computerMap) {
            if (computerMap.containsKey(id))
                return computerMap.get(id);
            else
                return null;
        }
    }
    
    @Override
    public Computer[] getComputers() {
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
            Computer newComputer = new Computer(nextId(), computer.getName(), computer.getCost(),
            computer.getQuantity(), computer.getBrand());
            
            computerMap.put(newComputer.getId(),newComputer);
            save(); 

            return newComputer;
        }
    }

    public Computer updateComputer(Computer computer) throws IOException {
        synchronized (computerMap) {

            int key = computer.getId();
            if (computerMap.containsKey(key) == false) {
                return null;
            }
            computerMap.put(key, computer);
            save();
            return computer;
        }

    }

    public boolean deleteComputer(int id) throws IOException {
        synchronized (computerMap) {
            if (computerMap.containsKey(id)) {
                computerMap.remove(id);
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
