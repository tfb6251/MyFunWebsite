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
import com.ufund.api.ufundapi.model.Needs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


public class CupBoardFileDAOTest {
    CupBoardFileDAO cupBoardFileDAO;
    Needs[] testNeeds;
    ObjectMapper mockObjectMapper;


    @BeforeEach
    public void setupHeroFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testNeeds = new Needs[3];
        testNeeds[0] = new Needs("Obiwan", 1000000, 1000, "Toshiba" );
        testNeeds[1] = new Needs("QuiGon", 100000, 100, "Toyota" );
        testNeeds[2] = new Needs("Yoda", 10000, 10, "Honda" );


        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Needs[].class))
                .thenReturn(testNeeds);
        cupBoardFileDAO = new CupBoardFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetHeroes() {

        Needs[] heroes = cupBoardFileDAO.getNeedsArray();


        assertEquals(heroes.length,testNeeds.length);
        for (int i = 0; i < testNeeds.length;++i)
            assertEquals(heroes[i],testNeeds[i]);
    }

    @Test
    public void testFindHeroes() {

        Needs[] heroes = cupBoardFileDAO.findNeeds("la");


        assertEquals(heroes.length,2);
        assertEquals(heroes[0],testNeeds[1]);
        assertEquals(heroes[1],testNeeds[2]);
    }

    @Test
    public void testGetHero() {
    
        Needs hero = cupBoardFileDAO.getNeed("Obiwan");


        assertEquals(hero,testNeeds[0]);
    }

    @Test
    public void testDeleteHero() {

        boolean result = assertDoesNotThrow(() -> cupBoardFileDAO.deleteNeeds("Obiwan"),
                            "Unexpected exception thrown");


        assertEquals(result,true);

        assertEquals(cupBoardFileDAO.computer.size(),testNeeds.length-1);
    }

    @Test
    public void testCreateHero() {
        
        Needs need = new Needs("MaceWindu", 1000, 1, "Bugatti" );

        
        Needs result = assertDoesNotThrow(() -> cupBoardFileDAO.createNeeds(need),
                                "Unexpected exception thrown");

        
        assertNotNull(result);
        Needs actual = cupBoardFileDAO.getNeed(need.getName());
        assertEquals(actual.getName(),need.getName());
    }

    @Test
    public void testUpdateHero() {

        Needs need = new Needs("Obiwan", 10000000, 10000, "HelloThere" );;


        Needs result = assertDoesNotThrow(() -> cupBoardFileDAO.updateNeeds(need),
                                "Unexpected exception thrown");


        assertNotNull(result);
        Needs actual = cupBoardFileDAO.getNeed(need.getName());
        assertEquals(actual,need);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Needs[].class));

        Needs need = new Needs("MaceWindu", 1000, 1, "Bugatti" );

        assertThrows(IOException.class,
                        () -> cupBoardFileDAO.createNeeds(need),
                        "IOException not thrown");
    }

    @Test
    public void testGetHeroNotFound() {

        Needs hero = cupBoardFileDAO.getNeed("any");


        assertEquals(hero,null);
    }

    @Test
    public void testDeleteHeroNotFound() {

        boolean result = assertDoesNotThrow(() -> cupBoardFileDAO.deleteNeeds("any"),
                                                "Unexpected exception thrown");


        assertEquals(result,false);
        assertEquals(cupBoardFileDAO.computer.size(),testNeeds.length);
    }

    @Test
    public void testUpdateHeroNotFound() {

        Needs need = new Needs("MaceWindu", 1000, 1, "Bugatti" );


        Needs result = assertDoesNotThrow(() -> cupBoardFileDAO.updateNeeds(need),
                                                "Unexpected exception thrown");


        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {

        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);

        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Needs[].class);

        assertThrows(IOException.class,
                        () -> new CupBoardFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
