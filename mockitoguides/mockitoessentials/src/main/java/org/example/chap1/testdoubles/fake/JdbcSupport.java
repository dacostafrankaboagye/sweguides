package org.example.chap1.testdoubles.fake;

import java.util.List;
import java.util.Map;

/*
This class is responsible for database access, such as acquiring a
connection, building a statement object, querying the database, updating the
table, and so on.

We'll hide the JDBC code and just expose a method for the
batch update
 */
public class JdbcSupport {

    public int[] batchUpdate(String sql, List<Map<String, Object>> params){
        //original db access code is hidden
        return null;
    }
}

/*
Check whether the batchUpdate method takes an SQL string and a list
of objects to be persisted. It returns an array of integers. Each array index
contains either 0 or 1. If the value returned is 1, it means that the database
update is successful, and 0 means there is no update. So, if we pass only
one Student object to update and if the update succeeds, then the array
will contain only one integer as 1; however, if it fails, then the array will
contain 0.
 */
