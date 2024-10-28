package com.ufund.api.ufundapi.persistence;
import java.io.IOException;
import com.ufund.api.ufundapi.model.Computer;

public interface ComputerDAO {
    Computer[] getComputers() throws IOException;
    Computer getComputer(int id) throws IOException;
    Computer[] findComputers(String name) throws IOException;
    Computer createComputer(Computer computer) throws IOException;
    Computer updateComputer(Computer computer)throws IOException;
    boolean deleteComputer(int id) throws IOException;
    
}
