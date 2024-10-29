package com.ufund.api.ufundapi.persistence;
import java.io.IOException;
import com.ufund.api.ufundapi.model.User;

public interface UserDAO {
    User[] getUsers() throws IOException;
    User getUser(int id) throws IOException;
    User getUserN(String name) throws IOException;
    User[] findUsers(String name) throws IOException;
    User createUser(User user) throws IOException;
    User updateUser(User user)throws IOException;
    boolean deleteUser(int id) throws IOException;
    
}
