package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Needs;

@Component
public class CupBoardFileDAO implements CupBoardDAO {
    Map<String, Needs> computer;
    private ObjectMapper objectMapper;
    private String filename;

    public CupBoardFileDAO(@Value("${computer.file}") String filename, ObjectMapper objectMapper)
            throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        
        load();
    }

    @Override
    public Needs getNeed(String name) {
        synchronized (computer) {
            if (computer.containsKey(name))
                return computer.get(name);
            else
                return null;
        }
    }

    @Override
    public Needs[] getNeedsArray() {
        return getNeedsArray(null);
    }

    @Override
    public Needs[] getNeedsArray(String containText) {
        ArrayList<Needs> needsArrayList = new ArrayList<>();
        for (Needs computer : computer.values()) {
            if (containText == null || computer.getName().contains(containText)) {
                needsArrayList.add(computer);
            }
        }

        Needs[] needsarray = new Needs[needsArrayList.size()];
        needsArrayList.toArray(needsarray);
        return needsarray;
    }

    private boolean save() throws IOException {
        Needs[] needsArray = getNeedsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), needsArray);
        return true;
    }

    private boolean load() throws IOException {
        computer = new TreeMap<>();

        Needs[] needsArray = objectMapper.readValue(new File(filename), Needs[].class);

        for (Needs needs : needsArray) {
            computer.put(needs.getName(), needs);
        }
        return true;
    }

    public Needs[] findNeeds(String containsText) {
        synchronized (computer) {
            return getNeedsArray(containsText);
        }
    }

    public Needs createNeeds(Needs need) throws IOException {
        synchronized (computer) {
            // We create a new hero object because the id field is immutable
            // and we need to assign the next unique id
            String key = need.getName();
            if (!(this.computer.containsKey(key))) {
                this.computer.put(key, need);
                save();
                return need;
            }
            return null;
        }
    }

    public Needs updateNeeds(Needs need) throws IOException {
        synchronized (computer) {

            String key = need.getName();
            if (computer.containsKey(key) == false) {
                return null;
            }
            computer.put(key, need);
            save();
            return need;
        }

    }

    public boolean deleteNeeds(String name) throws IOException {
        synchronized (computer) {
            if (computer.containsKey(name)) {
                computer.remove(name);
                return save();
            } else {
                return false;
            }

        }
    }

}
