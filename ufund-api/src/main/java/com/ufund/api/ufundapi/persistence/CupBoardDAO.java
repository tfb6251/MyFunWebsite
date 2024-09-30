package com.ufund.api.ufundapi.persistence;
import java.io.IOException;
import com.ufund.api.ufundapi.model.Needs;

public interface CupBoardDAO {

    Needs[] getNeedsArray() throws IOException;
    Needs getNeed(String name) throws IOException;
    Needs[] findNeeds(String name) throws IOException;
    Needs[] createNeeds(Needs need) throws IOException;
    Needs[] updatNeeds(Needs need)throws IOException;
    Needs[] deleteNeeds(String name) throws IOException;
}
