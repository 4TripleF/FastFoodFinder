package com.iceteaviet.fastfoodfinder.utils;

/**
 * Use class instead of interface for constant class to avoid Constant Interface Antipattern
 *
 * @see https://stackoverflow.com/questions/2659593/what-is-the-use-of-interface-constants
 * <p>
 * Created by Genius Doan on 11/11/2016.
 */

final public class Constant {
    //Keys
    public static final String KEY_ROUTE_LIST = "route_list";
    public static final String KEY_DES_STORE = "des_store";
    public static final String KEY_USER_STORE_LIST = "store";
    public static final String KEY_USER_PHOTO_URL = "url";
    public static final String COMMENT = "comment";
    public static final String STORE = "ic_store";


    public static final String CHILD_STORES_LOCATION = "stores_location";
    public static final String CHILD_USERS = "users";
    public static final String CHILD_MARKERS_ADD = "markers_add";
    public static final String CHILD_USERS_STORE_LIST = "userStoreLists";
    //Type
    public static final int TYPE_CIRCLE_K = 0;
    public static final int TYPE_MINI_STOP = 1;
    public static final int TYPE_FAMILY_MART = 2;
    public static final int TYPE_BSMART = 3;
    public static final int TYPE_SHOP_N_GO = 4;
    //Map utils
    public static final long INTERVAL = 1000 * 10;
    public static final long FASTEST_INTERVAL = 1000 * 5;

    public static final String DOWNLOADER_BOT_EMAIL = "store_downloader@fastfoodfinder.com";
    public static final String DOWNLOADER_BOT_PWD = "123456789";

    public static final String NO_AVATAR_PLACEHOLDER_URL = "http://cdn.builtlean.com/wp-content/uploads/2015/11/all_noavatar.png.png";

    public static final String PARAM_DESTINATION = "destination";
    public static final String PARAM_ORIGIN = "origin";

    public static final float DEFAULT_ZOOM_LEVEL = 16;
    public static final float DETAILED_ZOOM_LEVEL = 18;

    private Constant() {

    }
}
