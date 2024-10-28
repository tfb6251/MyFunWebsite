package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Computer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


public class ComputerFileDAOTest {
    ComputerFileDAO cupBoardFileDAO;
    Computer[] testNeeds;
    ObjectMapper mockObjectMapper;


    @BeforeEach
    public void setupHeroFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testNeeds = new Computer[3];
        testNeeds[0] = new Computer(0, "Obiwan", 1000000, 1000, "Toshiba" );
        testNeeds[1] = new Computer(1, "QuiGon", 100000, 100, "Toyota" );
        testNeeds[2] = new Computer(2, "Yoda", 10000, 10, "Honda" );


        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Computer[].class))
                .thenReturn(testNeeds);
        cupBoardFileDAO = new ComputerFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    

    @Test //2
    public void testFindHeroes() {

        Computer[] heroes = cupBoardFileDAO.findComputers("n");


        assertEquals(heroes.length,2);
        assertEquals(heroes[0],testNeeds[0]);
        assertEquals(heroes[1],testNeeds[1]);
    }

    @Test //3
    public void testGetHero() {
    
        Computer hero = cupBoardFileDAO.getComputer(0);


        assertEquals(hero,testNeeds[0]);
    }

    @Test //4
    public void testDeleteHero() {

        boolean result = assertDoesNotThrow(() -> cupBoardFileDAO.deleteComputer(0),
                            "Unexpected exception thrown");


        assertEquals(result,true);

        assertEquals(cupBoardFileDAO.computerMap.size(),testNeeds.length-1);
    }

    @Test //5
    public void testCreateHero() {
        
        Computer computer = new Computer(3, "MaceWindu", 1000, 1, "Bugatti" );

        
        Computer result = assertDoesNotThrow(() -> cupBoardFileDAO.createComputer(computer),
                                "Unexpected exception thrown");

        
        assertNotNull(result);
        Computer actual = cupBoardFileDAO.getComputer(computer.getId());
        assertEquals(actual.getName(),computer.getName());
    }

    @Test //6
    public void testUpdateHero() {

        Computer computer = new Computer(0, "Obiwan", 10000000, 10000, "HelloThere" );;


        Computer result = assertDoesNotThrow(() -> cupBoardFileDAO.updateComputer(computer),
                                "Unexpected exception thrown");


        assertNotNull(result);
        Computer actual = cupBoardFileDAO.getComputer(computer.getId());
        assertEquals(actual,computer);
    }

    @Test //7
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Computer[].class));

        Computer computer = new Computer(3, "MaceWindu", 1000, 1, "Bugatti" );

        assertThrows(IOException.class,
                        () -> cupBoardFileDAO.createComputer(computer),
                        "IOException not thrown");
    }

    @Test //8
    public void testGetHeroNotFound() {

        Computer hero = cupBoardFileDAO.getComputer(-1);


        assertEquals(hero,null);
    }

    @Test //9
    public void testDeleteHeroNotFound() {

        boolean result = assertDoesNotThrow(() -> cupBoardFileDAO.deleteComputer(-1),
                                                "Unexpected exception thrown");


        assertEquals(result,false);
        assertEquals(cupBoardFileDAO.computerMap.size(),testNeeds.length);
    }

    @Test //10
    public void testUpdateHeroNotFound() {

        Computer computer = new Computer(3, "MaceWindu", 1000, 1, "Bugatti" );


        Computer result = assertDoesNotThrow(() -> cupBoardFileDAO.updateComputer(computer),
                                                "Unexpected exception thrown");


        assertNull(result);
    }

    @Test //11
    public void testConstructorException() throws IOException {

        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);

        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Computer[].class);

        assertThrows(IOException.class,
                        () -> new ComputerFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
