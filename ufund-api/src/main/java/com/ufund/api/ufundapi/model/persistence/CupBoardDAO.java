package com.ufund.api.ufundapi.model.persistence;
import java.io.IOException;
import com.ufund.api.ufundapi.model.Needs;

public interface CupBoardDAO {

    Needs[] getNeedsArray(String containText) throws IOException;
    Needs[] getNeedsArray() throws IOException;
    Needs getNeed(String name) throws IOException;
    Needs[] findNeeds(String name) throws IOException;
    Needs createNeeds(Needs need) throws IOException;
    Needs updateNeeds(Needs need)throws IOException;
    boolean deleteNeeds(String name) throws IOException;
    
}
