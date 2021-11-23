关于拉活部署步骤
1.修改Manifest中相关配置
    <provider
        android:name="com.lianjia.pullalive.accountsync.SyncProvider"
        android:authorities="com.lianjia.accountsync.syncprovider"
        android:exported="false"
        android:label="@string/app_name_pullalive"
        android:syncable="true"
        >
    </provider>

    这个provider中的android:authorities设置为:app包名 + accountsync.syncprovider

2.修改res下xml中相关配置(包括authenticator.xml和syncadapter.xml)

  1) authenticator.xml
  <account-authenticator xmlns:android="http://schemas.android.com/apk/res/android"
      android:accountType="com.lianjia.accountsync"
      android:icon="@drawable/link_logo"
      android:label="@string/app_name_pullalive"
      android:smallIcon="@drawable/link_logo"
      />
   android:accountType设置为:app包名 + accountsync
   android:icon和android:smallIcon设置为自己app的icon
   android:label设置为自己app需要在账号列表中显示的名字(一般为自己应用的名字,修改string/app_name_pullalive为自己app的应用名即可)

   2) syncadapter.xml
   <sync-adapter xmlns:android="http://schemas.android.com/apk/res/android"
       android:accountType="com.lianjia.accountsync"
       android:allowParallelSyncs="false"
       android:contentAuthority="com.lianjia.accountsync.syncprovider"
       android:isAlwaysSyncable="true"
       android:supportsUploading="false"
       android:userVisible="true"/>
   android:accountType同上设置为:app包名 + accountsync
   android:contentAuthority设置为1中provider的android:authorities(app包名 + accountsync.syncprovider)

3. 修改AccountHelper.java中常量的定义
   public static final String AUTHORITY = app包名 + accountsync.syncprovider
   public static final String ACCOUNT_TYPE = app包名 + accountsync

4. 修改string.xml
  设置<string name="app_name_pullalive">把我修改为app的应用名</string>

5. 默认拉活app的UI进程,如果想拉活别的进程,将Manifest中的组件设置在别的进程即可

