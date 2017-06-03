package com.example.hongcheng.common.db.manager;

import com.example.hongcheng.common.db.DBCore;
import com.example.hongcheng.common.db.dao.MessageInfoDao;
/**
 * @Project CommonProj
 * @Packate com.micky.commonproj.domain.db
 *
 * @Description
 *
 * @Author Micky Liu
 * @Email mickyliu@126.com
 * @Date 2016-01-29 14:29
 * @Version 1.0
 */
public class DaoManagerFactory {

    private static MessageInfoDaoManager sPlaceDaoManager;

    public static MessageInfoDaoManager getMessageDaoManager() {
        if (sPlaceDaoManager == null) {
            MessageInfoDao messageInfoDao = DBCore.getDaoSession().getMessageInfoDao();
            sPlaceDaoManager = new MessageInfoDaoManager(messageInfoDao);
        }
        return sPlaceDaoManager;
    }

}
