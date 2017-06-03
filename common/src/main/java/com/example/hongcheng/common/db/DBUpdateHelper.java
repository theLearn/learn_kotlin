package com.example.hongcheng.common.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * 数据库升级工具类
 * Created by hongcheng on 16/3/27.
 */
public class DBUpdateHelper {
    private static final String CONVERSION_CLASS_NOT_FOUND_EXCEPTION = "CLASS DOESN'T MATCH WITH THE CURRENT PARAMETERS";

    private static DBUpdateHelper instance;

    public static DBUpdateHelper getInstance() {
        if (instance == null) {
            instance = new DBUpdateHelper();
        }
        return instance;
    }

    public void update(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        generateTempTables(db, daoClasses);
        dropAllTables(db, true, daoClasses);
        createAllTables(db, false, daoClasses);
        restoreData(db, daoClasses);
    }

    private void generateTempTables(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String divider = "";
            String tableName = daoConfig.tablename;
            String tempTableName = tableName.concat("_TEMP");
            List<String> properties = new ArrayList<String>();
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("CREATE TABLE ").append(tempTableName).append(" (");
            for(int j = 0; j<daoConfig.properties.length;j++){
                String columnName = daoConfig.properties[j].columnName;

                if(getColumns(db, tableName).contains(columnName)){
                    properties.add(columnName);
                    String type = null;
                    try {
                        type = getTypeByClass(daoConfig.properties[j].type);
                    } catch (Exception e) {
                        Log.e("Exception",e.getMessage(),e);
                    }
                    if(type != null){
                        strBuilder.append(divider).append(columnName).append(" ").append(type);
                        if(daoConfig.properties[j].primaryKey) {
                            strBuilder.append(" PRIMARY KEY");
                        }
                        divider = ",";
                    }
                }
            }

            strBuilder.append(");");
            if(strBuilder.toString().contains(" ();")){
                continue;
            }
            db.execSQL(strBuilder.toString());

            StringBuilder insertTableStringBuilder = new StringBuilder();

            insertTableStringBuilder.append("INSERT INTO ").append(tempTableName).append(" (");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(") SELECT ");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(" FROM ").append(tableName).append(";");

            db.execSQL(insertTableStringBuilder.toString());

        }
    }

    private void dropAllTables(SQLiteDatabase db, boolean ifExists, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for(int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\""+daoConfig.tablename+"\"";
            db.execSQL(sql);
        }
    }

    private void createAllTables(SQLiteDatabase db, boolean ifNotExists, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for(int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);

            String divider = "";
            String tableName = daoConfig.tablename;

            StringBuilder strBuilder = new StringBuilder();

            String constraint = ifNotExists? "IF NOT EXISTS ": "";

            strBuilder.append("CREATE TABLE ").append(constraint).append(tableName).append(" (");

            for(int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;
                String type = null;

                try {
                    type = getTypeByClass(daoConfig.properties[j].type);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(type != null){
                    strBuilder.append(divider).append(columnName).append(" ").append(type);

                    if(daoConfig.properties[j].primaryKey) {
                        strBuilder.append(" PRIMARY KEY");
                    }
                    divider = ",";
                }
            }
            strBuilder.append(");");
            db.execSQL(strBuilder.toString());
        }
    }

    private void restoreData(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for(int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);

            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            ArrayList<String> properties = new ArrayList();

            for (int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;

                if(getColumns(db, tempTableName).contains(columnName)) {
                    properties.add(columnName);
                }
            }

            StringBuilder insertTableStringBuilder = new StringBuilder();

            insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(") SELECT ");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");

            StringBuilder dropTableStringBuilder = new StringBuilder();

            dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);


            if(insertTableStringBuilder.toString().contains("()")){
                continue;
            }

            db.execSQL(insertTableStringBuilder.toString());
            db.execSQL(dropTableStringBuilder.toString());
        }
    }

    private String getTypeByClass(Class<?> type) throws Exception {
        if(type.equals(String.class)) {
            return "TEXT";
        }
        if(type.equals(Long.class) || type.equals(Integer.class) || type.equals(long.class) || type.equals(Date.class)) {
            return "INTEGER";
        }
        if(type.equals(Boolean.class)) {
            return "BOOLEAN";
        }

        Exception exception = new Exception(CONVERSION_CLASS_NOT_FOUND_EXCEPTION.concat(" - Class: ").concat(type.toString()));
        throw exception;
    }

    private static List<String> getColumns(SQLiteDatabase db, String tableName) {
        List<String> columns = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);
            if (cursor != null) {
                columns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return columns;
    }

}
