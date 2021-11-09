package xjt.spi;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class TDriver implements Driver {
    static {
        try {
            DriverManager.registerDriver(new TDriver());
        } catch (SQLException var1) {
            throw new RuntimeException("Can't register driver!");
        }
    }
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        System.out.println("test connect");
        if(info==null){
            throw new TError();
        }
        return new TConnection();
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return false;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
