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
    public void testGetNeed() throws IOException {
        
        Needs needs = new Needs("Galactic Agent", 2, 99,"Galacticorsomeshitidontfuckingknowfuckyou");
        when(mockComputerService.getNeed(needs.getName())).thenReturn(needs);

        ResponseEntity<Needs> response = ComputerController.getNeed(needs.getName());

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testGetNeedNotFound() throws Exception {
        String needName = "Galactic Agent";
        when(mockComputerService.getNeed(needName)).thenReturn(null);

        ResponseEntity<Needs> response = ComputerController.getNeed(needName);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetNeedHandleException() throws Exception {
        String needName = "Galactic Agent";
        doThrow(new IOException()).when(mockComputerService).getNeed(needName);

        ResponseEntity<Needs> response = ComputerController.getNeed(needName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    @Test
    public void testCreateNeeds() throws IOException { 
        Needs need = new Needs("OUI", 3, 99,"Wi-Fire");
        when(mockComputerService.createNeeds(need)).thenReturn(need);

        ResponseEntity<Needs> response = ComputerController.createNeeds(need);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testCreateNeedsFailed() throws IOException {
        Needs need = new Needs("fuck", 99, 99,"Bolt");
        when(mockComputerService.createNeeds(need)).thenReturn(null);

        ResponseEntity<Needs> response = ComputerController.createNeeds(need);

        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateNeedsHandleException() throws IOException { 
        Needs need = new Needs("AAAGGHH", 0, 99,"Ice Gladiator");

        doThrow(new IOException()).when(mockComputerService).createNeeds(need);

        ResponseEntity<Needs> response = ComputerController.createNeeds(need);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateNeeds() throws IOException {
        Needs need = new Needs("pspspsps", 1, 99,"Wi-Fire");
        when(mockComputerService.updateNeeds(need)).thenReturn(need);
        ResponseEntity<Needs> response = ComputerController.updateNeeds(need);
        need.setName("Bolt");

        response = ComputerController.updateNeeds(need);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testUpdateNeedsFailed() throws IOException {
        Needs need = new Needs("e", 2010101010, 99,"Galactic Agent");
        when(mockComputerService.updateNeeds(need)).thenReturn(null);

        ResponseEntity<Needs> response = ComputerController.updateNeeds(need);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateNeedsHandleException() throws IOException { 
        Needs need = new Needs("whytf", 10, 99,"Galactic Agent");
        doThrow(new IOException()).when(mockComputerService).updateNeeds(need);

        ResponseEntity<Needs> response = ComputerController.updateNeeds(need);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetNeeds() throws IOException { 
        Needs[] needs = new Needs[2];
        needs[0] = new Needs("nuts1", 21, 99,"Bolt");
        needs[1] = new Needs("nuts2", 12, 100,"The Great Iguana");
        when(mockComputerService.getNeedsArray()).thenReturn(needs);

        ResponseEntity<Needs[]> response = ComputerController.getNeedsArray();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testGetNeedsHandleException() throws IOException {
        doThrow(new IOException()).when(mockComputerService).getNeedsArray();

        ResponseEntity<Needs[]> response = ComputerController.getNeedsArray();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearcNeeds() throws IOException { 
        String searchString = "la";
        Needs[] needs = new Needs[2];
        needs[0] = new Needs("naalalallalalalala", 5, 99,"Galactic Agent");
        needs[1] = new Needs("nononononononolaALALALLallalaal", 21, 100,"Ice Gladiator");
        
        when(mockComputerService.findNeeds(searchString)).thenReturn(needs);

        ResponseEntity<Needs[]> response = ComputerController.searchNeeds(searchString);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testSearchNeedsHandleException() throws IOException { 
        String searchString = "an";
        doThrow(new IOException()).when(mockComputerService).findNeeds(searchString);

        ResponseEntity<Needs[]> response = ComputerController.searchNeeds(searchString);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteNeeds() throws IOException { 
        String needName = "bitchass";
        when(mockComputerService.deleteNeeds(needName)).thenReturn(true);


        ResponseEntity<Needs> response = ComputerController.deleteNeeds(needName);

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedsNotFound() throws IOException { 
        String needName = "bitchass";
        when(mockComputerService.deleteNeeds(needName)).thenReturn(false);

        ResponseEntity<Needs> response = ComputerController.deleteNeeds(needName);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedsHandleException() throws IOException { 
        String needName = "bitchass";
        doThrow(new IOException()).when(mockComputerService).deleteNeeds(needName);

        ResponseEntity<Needs> response = ComputerController.deleteNeeds(needName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
