package com.jhcms.common.model;

/**
 * Created by wangyujie
 * Date 2017/6/27.
 * TODO 获取微信配置
 */

public class Data_Get_WeChat {


    /**
     * data : {"type":"1","appid":"wx5e54728877f7560d","appsecret":"de22eb3e8d8830852e7dd17de5945f40","wechat_token":"6350f7736abd59bb8e4b1b8f78aa55c7","wechat_aeskey":"SPy5s2ID4nAEbUq06Rr3wKo1dR9tE1aVUFwAAsql14i","app_appid":"wx616ee0fb2006ee4a","app_appsecret":"33481723e37721aca4f891381f234b1e","open_mp_appid":"wxf114c51797de2dd0","open_mp_appsecret":"d3ca8e8278312c5718c1fc4627a06bfc","open_mp_token":"9e5f1aa830ad215eff11eafd1015438b0f91a6f4","open_mp_aeskey":"9e5f1aa8Ine4GoNe30ein5K0M82eff1aH6f4sDFe5L2","wxapp_appid":"wxa8d3f599574cda8b","wxapp_secret":"161ee106fae7ea2532047db9955a66cb"}
     */

    public DataEntity data;

    public static class DataEntity {
        /**
         * type : 1
         * appid : wx5e54728877f7560d
         * appsecret : de22eb3e8d8830852e7dd17de5945f40
         * wechat_token : 6350f7736abd59bb8e4b1b8f78aa55c7
         * wechat_aeskey : SPy5s2ID4nAEbUq06Rr3wKo1dR9tE1aVUFwAAsql14i
         * app_appid : wx616ee0fb2006ee4a
         * app_appsecret : 33481723e37721aca4f891381f234b1e
         * open_mp_appid : wxf114c51797de2dd0
         * open_mp_appsecret : d3ca8e8278312c5718c1fc4627a06bfc
         * open_mp_token : 9e5f1aa830ad215eff11eafd1015438b0f91a6f4
         * open_mp_aeskey : 9e5f1aa8Ine4GoNe30ein5K0M82eff1aH6f4sDFe5L2
         * wxapp_appid : wxa8d3f599574cda8b
         * wxapp_secret : 161ee106fae7ea2532047db9955a66cb
         */

        public String type;
        public String appid;
        public String appsecret;
        public String wechat_token;
        public String wechat_aeskey;
        public String app_appid;
        public String app_appsecret;
        public String open_mp_appid;
        public String open_mp_appsecret;
        public String open_mp_token;
        public String open_mp_aeskey;
        public String wxapp_appid;
        public String wxapp_secret;
    }
}
