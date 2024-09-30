package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.Needs;

public interface CupBoardDAO {

    Needs[] getNeedsArray();
    Needs[] findNeeds(String containsText) throws IOException;
    Needs[] createNeeds();
    Needs[] updatNeeds();
    Needs[] deleteNeeds();
}
