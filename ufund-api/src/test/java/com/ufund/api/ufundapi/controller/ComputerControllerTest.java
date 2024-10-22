package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.CupBoardDAO;
import com.ufund.api.ufundapi.model.Needs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ComputerControllerTest {
    private ComputerController ComputerController;
    private ComputerService mockComputerService;

    /**
     * Before each test, create a new ComputerController object and inject
     * a mock ComputerService
     */
    @BeforeEach
    public void setupComputerController() {
        mockComputerService = mock(ComputerService.class);
        ComputerController = new ComputerController(mockComputerService);
    }

    @Test
    public void testGetNeed() throws IOException {  // getNeed may throw IOException
        // Setup
        Needs needs = new Needs("Galactic Agent", 2, 99,"Galacticorsomeshitidontfuckingknowfuckyou");
        // When the same id is passed in, our mock Computer Service will return the Needs object
        when(mockComputerService.getNeed(needs.getName())).thenReturn(needs);

        // Invoke
        ResponseEntity<Needs> response = ComputerController.getNeed(needs.getName());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testGetNeedNotFound() throws Exception { // createNeed may throw IOException
        // Setup
        String needName = "Galactic Agent";
        // When the same id is passed in, our mock Hero DAO will return null, simulating
        // no hero found
        when(mockComputerService.getNeed(needName)).thenReturn(null);

        // Invoke
        ResponseEntity<Needs> response = ComputerController.getNeed(needName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetNeedHandleException() throws Exception { // createNeed may throw IOException
        // Setup
        String needName = "Galactic Agent";
        // When getNeed is called on the Mock Computer Service, throw an IOException
        doThrow(new IOException()).when(mockComputerService).getNeed(needName);

        // Invoke
        ResponseEntity<Needs> response = ComputerController.getNeed(needName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all HeroController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateNeeds() throws IOException {  // createNeeds may throw IOException
        // Setup
        Needs need = new Needs("OUI", 3, 99,"Wi-Fire");
        // when createNeed is called, return true simulating successful
        // creation and save
        when(mockComputerService.createNeeds(need)).thenReturn(need);

        // Invoke
        ResponseEntity<Needs> response = ComputerController.createNeeds(need);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testCreateNeedsFailed() throws IOException {  // createNeeds may throw IOException
        // Setup
        Needs need = new Needs("fuck", 99, 99,"Bolt");
        // when createNeeds is called, return false simulating failed
        // creation and save
        when(mockComputerService.createNeeds(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Needs> response = ComputerController.createNeeds(need);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateNeedsHandleException() throws IOException {  // createHero may throw IOException
        // Setup
        Needs need = new Needs("AAAGGHH", 0, 99,"Ice Gladiator");

        // When createNeeds is called on the Mock ComputerService, throw an IOException
        doThrow(new IOException()).when(mockComputerService).createNeeds(need);

        // Invoke
        ResponseEntity<Needs> response = ComputerController.createNeeds(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateNeeds() throws IOException { // updateNeeds may throw IOException
        // Setup
        Needs need = new Needs("pspspsps", 1, 99,"Wi-Fire");
        // when updateNeeds is called, return true simulating successful
        // update and save
        when(mockComputerService.updateNeeds(need)).thenReturn(need);
        ResponseEntity<Needs> response = ComputerController.updateNeeds(need);
        need.setName("Bolt");

        // Invoke
        response = ComputerController.updateNeeds(need);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testUpdateNeedsFailed() throws IOException { // updateNeeds may throw IOException
        // Setup
        Needs need = new Needs("e", 2010101010, 99,"Galactic Agent");
        // when updateNeeds is called, return true simulating successful
        // update and save
        when(mockComputerService.updateNeeds(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Needs> response = ComputerController.updateNeeds(need);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateNeedsHandleException() throws IOException { // updateNeeds may throw IOException
        // Setup
        Needs need = new Needs("whytf", 10, 99,"Galactic Agent");
        // When updateNeeds is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockComputerService).updateNeeds(need);

        // Invoke
        ResponseEntity<Needs> response = ComputerController.updateNeeds(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetNeeds() throws IOException { // getNeeds may throw IOException
        // Setup
        Needs[] needs = new Needs[2];
        needs[0] = new Needs("nuts1", 21, 99,"Bolt");
        needs[1] = new Needs("nuts2", 12, 100,"The Great Iguana");
        // When getNeeds is called return the heroes created above
        when(mockComputerService.getNeedsArray()).thenReturn(needs);

        // Invoke
        ResponseEntity<Needs[]> response = ComputerController.getNeedsArray();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testGetNeedsHandleException() throws IOException { // getHeroes may throw IOException
        // Setup
        // When getNeeds is called on the Mock Computer Service, throw an IOException
        doThrow(new IOException()).when(mockComputerService).getNeedsArray();

        // Invoke
        ResponseEntity<Needs[]> response = ComputerController.getNeedsArray();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearcNeeds() throws IOException { // findNeeds may throw IOException
        // Setup
        String searchString = "la";
        Needs[] needs = new Needs[2];
        needs[0] = new Needs("naalalallalalalala", 5, 99,"Galactic Agent");
        needs[1] = new Needs("nononononononolaALALALLallalaal", 21, 100,"Ice Gladiator");
        // When findNeeds is called with the search string, return the two
        /// needs above
        when(mockComputerService.findNeeds(searchString)).thenReturn(needs);

        // Invoke
        ResponseEntity<Needs[]> response = ComputerController.searchNeeds(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testSearchNeedsHandleException() throws IOException { // findNeeds may throw IOException
        // Setup
        String searchString = "an";
        // When createNeed is called on the Mock Computer Service, throw an IOException
        doThrow(new IOException()).when(mockComputerService).findNeeds(searchString);

        // Invoke
        ResponseEntity<Needs[]> response = ComputerController.searchNeeds(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteNeeds() throws IOException { // deleteNeeds may throw IOException
        // Setup
        String needName = "bitchass";
        // when deleteNeeds is called return true, simulating successful deletion
        when(mockComputerService.deleteNeeds(needName)).thenReturn(true);

        // Invoke
        ResponseEntity<Needs> response = ComputerController.deleteNeeds(needName);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedsNotFound() throws IOException { // deleteNeeds may throw IOException
        // Setup
        String needName = "bitchass";
        // when deleteNeeds is called return false, simulating failed deletion
        when(mockComputerService.deleteNeeds(needName)).thenReturn(false);

        // Invoke
        ResponseEntity<Needs> response = ComputerController.deleteNeeds(needName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedsHandleException() throws IOException { // deleteNeeds may throw IOException
        // Setup
        String needName = "bitchass";
        // When deleteNeeds is called on the Mock ComputerController, throw an IOException
        doThrow(new IOException()).when(mockComputerService).deleteNeeds(needName);

        // Invoke
        ResponseEntity<Needs> response = ComputerController.deleteNeeds(needName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
