package com.ufund.api.ufundapi.persistence;
import java.io.IOException;
import com.ufund.api.ufundapi.model.Telem;

public interface TelemDAO {
    Telem getTelem(int id) throws IOException;    
    Telem[] getTelems() throws IOException;
    Telem[] findTelems(String type, float term) throws IOException;
    Telem createTelem(Telem telem) throws IOException;
    Telem updateTelem(Telem telem)throws IOException;
    boolean deleteTelem(int id) throws IOException;
}
