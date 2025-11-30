package com.purityvanilla.pvlib.database.migration;

public interface Migration {

    int getVersion();

    void migrate();

}
