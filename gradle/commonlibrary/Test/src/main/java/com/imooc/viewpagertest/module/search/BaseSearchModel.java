package com.imooc.viewpagertest.module.search;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * @author: vision
 * @function:
 * @date: 16/8/12
 */
@RealmClass
public class BaseSearchModel implements RealmModel {

    public String ecode;
    public String emsg;
    public SearchModel data;
}
