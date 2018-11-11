package com.example.linhongyu.smarter;

import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by linhongyu on 29/4/18.
 */

public class DBStructure {
    public static abstract class  tableEntry implements BaseColumns {
        public static final String TABLE_NAME = "ELECUSAGE";
        public static final String COLUMN_USAGEID = "USAGEID";
        public static final String COLUMN_RESID = "RESID";
        public static final String COLUMN_USEDATE = "USEDATE";
        public static final String COLUMN_USEHOUR = "USEHOUR";
        public static final String COLUMN_FRIDGEUSAGE = "FRIDGEUSAGE";
        public static final String COLUMN_CONDITINERUSAGE = "CONDITINERUSAGE";
        public static final String COLUMN_WASHINGUSAGE = "WASHINGUSAGE";
        public static final String COLUMN_TEMPERATURE = "TEMPERATURE";

    }
}
