package com.youdu.module.andfix;

import com.youdu.module.BaseModel;

/**
 * Created by renzhiqiang on 17/1/14.
 */

public class PatchInfo extends BaseModel {

    public String downloadUrl; //不为空则表明有更新

    public String versionName; //本次patch包的版本号

    public String patchMessage; //本次patch包含的相关信息，例如：主要做了那些改动
}
