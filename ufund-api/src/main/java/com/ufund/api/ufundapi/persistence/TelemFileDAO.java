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
import com.ufund.api.ufundapi.model.Telem;

@Component
public class TelemFileDAO implements TelemDAO {
    Map<Integer, Telem> telemMap;
    private ObjectMapper objectMapper;
    private String filename;
    private static int nextId;  
    
    public TelemFileDAO(@Value("${telem.file}") String filename, ObjectMapper objectMapper)
        throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        if (!new File(filename).exists() || new File(filename).length() == 0) {
            new File(filename).getParentFile().mkdirs();  // Create parent directories if they don’t exist
            new File(filename).createNewFile();           // Create the new file
            try (FileWriter writer = new FileWriter(filename)) {
                writer.write("[]");             // Initialize with an empty JSON array
            }
        }
        load();
    }
 
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private boolean save() throws IOException {
        Telem[] telemsArray = getTelems();
        objectMapper.writeValue(new File(filename), telemsArray);
        return true;
    }
    
    private boolean load() throws IOException {
        telemMap = new TreeMap<>();
        nextId = 0;
        
        Telem[] telemsArray = objectMapper.readValue(new File(filename), Telem[].class);
        
        for (Telem telem : telemsArray) {
            telemMap.put(telem.getId(), telem);
            if (telem.getId() > nextId)
                nextId = telem.getId();
        }
        ++nextId;
        return true;
    }

    @Override
    public Telem getTelem(int id) {
        synchronized (telemMap) {
            if (telemMap.containsKey(id))
                return telemMap.get(id);
            else
                return null;
        }
    }
    
    @Override
    public Telem[] getTelems() {
        return findTelems(null, 0);
    }

    public Telem[] findTelems(String type, float term) {
        synchronized (telemMap) {
            ArrayList<Telem> telemList = new ArrayList<>();
            for (Telem telem : telemMap.values()) {
                boolean match = false;
                if (type == null) {
                    telemList.add(telem);
                    continue;
                }

                switch (type.toLowerCase()) {
                    case "id":
                        match = telem.getId() == (int) term;
                        break;
                
                    case "temperature":
                        match = telem.getTemp() == term;
                        break;
                
                    case "pressure":
                        match = telem.getPres() == term;
                        break;
                
                    case "humidity":
                        match = telem.getHumi() == term;
                        break;
                
                    case "altitude":
                        match = telem.getAlti() == term;
                        break;                    
                
                
                    case "dcm":    
                        match = telem.getDcm() == term;
                        break;
                
                    case "din":    
                        match = telem.getDin() == term;
                        break;
                
                    case "dt":    
                        match = telem.getDt() == term;
                        break;
                
                    default:
                        // unknown type → skip or handle error
                        break;
                }
                if (match) {
                    telemList.add(telem);
                }
            }
    
            Telem[] telemsarray = new Telem[telemList.size()];
            telemList.toArray(telemsarray);
            return telemsarray;
        }
    }

    public Telem createTelem(Telem telem) throws IOException {
        synchronized (telemMap) {
            int newId = nextId();
            Telem newTelem = new Telem(newId, telem.getTemp(),telem.getPres(), 
            telem.getHumi(), telem.getAlti(), telem.getDcm(), telem.getDin(),
            telem.getDt());

            telemMap.put(newId,newTelem);
            try {
                save(); 
                return newTelem;
            } catch (IOException e) { 
                telemMap.remove(newId);
                --nextId; 
                throw e;               
            }


        }
    }

    public Telem updateTelem(Telem telem) throws IOException {
        synchronized (telemMap) {

            int key = telem.getId();
            if (telemMap.containsKey(key) == false) {
                return null;
            }
            Telem upda = telemMap.get(key);
            telemMap.put(key, telem);
            try {
                save();
                return telem;
            } catch (IOException e) {
                telemMap.put(key, upda);
                throw e;
            }
        }

    }

    public boolean deleteTelem(int id) throws IOException {
        synchronized (telemMap) {
            if (telemMap.containsKey(id)) {
                Telem remo = telemMap.remove(id);
                try {
                    if (id+1 == nextId) {
                        --nextId;
                    }
                    return save();
                } catch (IOException e) {
                    if (id == nextId) {
                        nextId();
                    }
                    telemMap.put(id, remo);
                    throw e;   
                }
            } else {
                return false;
            }

        }
    }
}
