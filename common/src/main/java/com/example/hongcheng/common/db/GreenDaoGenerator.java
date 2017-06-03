package com.example.hongcheng.common.db;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * @Project CommonProj
 * @Packate com.micky.commonproj.domain.db.manager
 * @Description
 * @Author Micky Liu
 * @Email mickyliu@126.com
 * @Date 2016-01-29 13:33
 * @Version 1.0
 */
public class GreenDaoGenerator {

    /**需要生成model的时候，请设置成true, 用于防止手贱点错了 */
    public static final boolean GENERATE_MODEL = false;

    /**
     * 数据库版本
     * 如果数据库需要升级,请在DaoMaster中的onUpgrade方法中加入:
     * MigrationHelper.getInstance().migrate(db, IpInfoDao.class, PlaceDao.class，...);
     */
    public static final int DB_VERSION = 1;

    public static void main(String[] args) throws Exception {
        if (GENERATE_MODEL) {
            Schema schema = new Schema(DB_VERSION, "com/example/hongcheng/common/db/model");
            schema.setDefaultJavaPackageDao("com/example/hongcheng/common/db/dao");
            schema.enableKeepSectionsByDefault();
            addMessageInfo(schema);
            new DaoGenerator().generateAll(schema, "./common/src/main/java/");
        }
    }

    private static void addMessageInfo(Schema schema) {
        Entity messageInfo = schema.addEntity("MessageInfo");
        messageInfo.addIdProperty().primaryKey();
        messageInfo.addStringProperty("msgId");
        messageInfo.addDoubleProperty("content");
        messageInfo.addDoubleProperty("title");
        messageInfo.addDoubleProperty("date");
        messageInfo.addDoubleProperty("params");
    }
}
