package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.ComputerDAO;
import com.ufund.api.ufundapi.model.Computer;

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
        
        Computer needs = new Computer(0, "Galactic Agent", 2, 99,"Galacticorsomeshitidontfuckingknowfuckyou");
        when(mockComputerService.getComputer(needs.getId())).thenReturn(needs);

        ResponseEntity<Computer> response = ComputerController.getComputer(needs.getId());

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testGetNeedNotFound() throws Exception {
        int needNum = 0;
        when(mockComputerService.getComputer(needNum)).thenReturn(null);

        ResponseEntity<Computer> response = ComputerController.getComputer(needNum);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetNeedHandleException() throws Exception {
        int needNum = 0;
        doThrow(new IOException()).when(mockComputerService).getComputer(needNum);

        ResponseEntity<Computer> response = ComputerController.getComputer(needNum);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    @Test
    public void testCreateNeeds() throws IOException { 
        Computer computer = new Computer(2, "OUI", 3, 99,"Wi-Fire");
        when(mockComputerService.createComputer(computer)).thenReturn(computer);

        ResponseEntity<Computer> response = ComputerController.createComputer(computer);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(computer,response.getBody());
    }

    @Test
    public void testCreateNeedsFailed() throws IOException {
        Computer computer = new Computer(3, "fuck", 99, 99,"Bolt");
        when(mockComputerService.createComputer(computer)).thenReturn(null);

        ResponseEntity<Computer> response = ComputerController.createComputer(computer);

        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateNeedsHandleException() throws IOException { 
        Computer computer = new Computer(4, "AAAGGHH", 0, 99,"Ice Gladiator");

        doThrow(new IOException()).when(mockComputerService).createComputer(computer);

        ResponseEntity<Computer> response = ComputerController.createComputer(computer);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateNeeds() throws IOException {
        Computer computer = new Computer(5, "pspspsps", 1, 99,"Wi-Fire");
        when(mockComputerService.updateComputer(computer)).thenReturn(computer);
        ResponseEntity<Computer> response = ComputerController.updateComputer(computer);
        computer.setName("Bolt");

        response = ComputerController.updateComputer(computer);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(computer,response.getBody());
    }

    @Test
    public void testUpdateNeedsFailed() throws IOException {
        Computer computer = new Computer(6, "e", 2010101010, 99,"Galactic Agent");
        when(mockComputerService.updateComputer(computer)).thenReturn(null);

        ResponseEntity<Computer> response = ComputerController.updateComputer(computer);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateNeedsHandleException() throws IOException { 
        Computer computer = new Computer(7, "whytf", 10, 99,"Galactic Agent");
        doThrow(new IOException()).when(mockComputerService).updateComputer(computer);

        ResponseEntity<Computer> response = ComputerController.updateComputer(computer);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetNeeds() throws IOException { 
        Computer[] needs = new Computer[2];
        needs[0] = new Computer(8, "nuts1", 21, 99,"Bolt");
        needs[1] = new Computer(9, "nuts2", 12, 100,"The Great Iguana");
        when(mockComputerService.findComputers("")).thenReturn(needs);

        ResponseEntity<Computer[]> response = ComputerController.findComputers();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testGetNeedsHandleException() throws IOException {
        doThrow(new IOException()).when(mockComputerService).findComputers("");

        ResponseEntity<Computer[]> response = ComputerController.findComputers();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearcNeeds() throws IOException { 
        String searchString = "la";
        Computer[] needs = new Computer[2];
        needs[0] = new Computer(1, "naalalallalalalala", 5, 99,"Galactic Agent");
        needs[1] = new Computer(2, "nononononononolaALALALLallalaal", 21, 100,"Ice Gladiator");
        
        when(mockComputerService.findComputers(searchString)).thenReturn(needs);

        ResponseEntity<Computer[]> response = ComputerController.searchNeeds(searchString);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testSearchNeedsHandleException() throws IOException { 
        String searchString = "an";
        doThrow(new IOException()).when(mockComputerService).findComputers(searchString);

        ResponseEntity<Computer[]> response = ComputerController.searchNeeds(searchString);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteNeeds() throws IOException { 
        int needNum = 0;
        when(mockComputerService.deleteComputer(needNum)).thenReturn(true);


        ResponseEntity<Computer> response = ComputerController.deleteComputer(needNum);

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedsNotFound() throws IOException { 
        int needNum = 0;
        when(mockComputerService.deleteComputer(needNum)).thenReturn(false);

        ResponseEntity<Computer> response = ComputerController.deleteComputer(needNum);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedsHandleException() throws IOException { 
        int needNum = 0;
        doThrow(new IOException()).when(mockComputerService).deleteComputer(needNum);

        ResponseEntity<Computer> response = ComputerController.deleteComputer(needNum);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
