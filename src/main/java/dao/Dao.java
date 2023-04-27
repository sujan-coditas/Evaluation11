package dao;

import java.io.IOException;

public interface Dao {
    void insertData() throws IOException;
    void updateData();
    void deleteData() throws IOException;
}
