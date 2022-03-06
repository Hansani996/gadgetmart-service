package com.swlc.gadgetmart.backend.main.database;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;


public class DBConnection {

    private static DBConnection dbConnection;
    private final BasicDataSource ds;

    private DBConnection() {
        ds = new BasicDataSource();
        ds.setDriverClassName(com.swlc.gadgetmart.backend.main.database.DBConfiguration.DRIVER);
        ds.setUsername(com.swlc.gadgetmart.backend.main.database.DBConfiguration.USERNAME);
        ds.setPassword(com.swlc.gadgetmart.backend.main.database.DBConfiguration.PASSWORD);
        ds.setUrl(com.swlc.gadgetmart.backend.main.database.DBConfiguration.DB_URL);

    }

    public static synchronized DBConnection getDBConnection() throws Exception {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
            return dbConnection;
        } else {
            return dbConnection;
        }
    }

    public Connection getConnection() throws Exception {
        return this.ds.getConnection();
    }
}
