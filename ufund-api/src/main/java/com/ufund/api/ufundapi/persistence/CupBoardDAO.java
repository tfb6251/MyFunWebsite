package com.ufund.api.ufundapi.persistence;

import com.ufund.api.ufundapi.model.Needs;

public interface CupBoardDAO {

    Needs[] getNeedsArray();
    Needs[] findNeeds();
    Needs[] createNeeds();
    Needs[] updatNeeds();
    Needs[] deleteNeeds();
}
