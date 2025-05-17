package org.example.chap1.testdoubles.stub;

import java.sql.SQLException;

/*
This class should be created under the test source folder since the class is
only used in tests
*/

public class ConnectionTimedOutStudentDAOStub implements StudentDAO{

    @Override
    public String create(String name, String className) throws SQLException {
        throw new SQLException("DB Connection timed out");
    }
}
