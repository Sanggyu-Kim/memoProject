package com.example.s_kim.memoproject;

import android.provider.BaseColumns;

public final class DataBases {

    public static final class CreateDB implements BaseColumns {
        public static final String MEMONUMBER = "memoNumber";
        public static final String TITLE = "title";
        public static final String MESSAGE = "message";
        public static final String _TABLENAME0 = "memoTable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +MEMONUMBER+" integer primary key, "
                +TITLE+" text not null , "
                +MESSAGE+" text not null);";
    }

}
