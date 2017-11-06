package com.jhcms.common.model;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/7/1.
 * TODO 城市选择
 */

public class Data_GetCity {

    /**
     * items : [{"city_id":"1","province_id":"1","city_name":"合肥","pinyin":"H","theme_id":"1","logo":"photo/201511/20151125_87BACD0EAE30C2AAFAF6E357064C06C4.png","phone":"400-009-8862","city_code":"0551","mobile":"13812365478","mail":"11@111.com","kfqq":"123456","orderby":"50","audit":"1","dateline":"1448415138","domain":"H.o2o.jhcms.cn","siteurl":"http://H.o2o.jhcms.cn","province_name":"安徽","py":"H"},{"city_id":"2","province_id":"1","city_name":"蚌埠","pinyin":"B","theme_id":"1","logo":"","phone":"","city_code":"0552","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496309","domain":"B.o2o.jhcms.cn","siteurl":"http://B.o2o.jhcms.cn","province_name":"安徽","py":"B"},{"city_id":"3","province_id":"1","city_name":"宿州","pinyin":"S","theme_id":"1","logo":"","phone":"","city_code":"0557","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496322","domain":"S.o2o.jhcms.cn","siteurl":"http://S.o2o.jhcms.cn","province_name":"安徽","py":"S"},{"city_id":"4","province_id":"1","city_name":"宣城","pinyin":"X","theme_id":"1","logo":"","phone":"","city_code":"0563","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496330","domain":"X.o2o.jhcms.cn","siteurl":"http://X.o2o.jhcms.cn","province_name":"安徽","py":"X"},{"city_id":"5","province_id":"1","city_name":"芜湖","pinyin":"W","theme_id":"1","logo":"","phone":"","city_code":"0553","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496337","domain":"W.o2o.jhcms.cn","siteurl":"http://W.o2o.jhcms.cn","province_name":"安徽","py":"W"},{"city_id":"6","province_id":"1","city_name":"淮南","pinyin":"H","theme_id":"1","logo":"","phone":"","city_code":"0554","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496469","domain":"H.o2o.jhcms.cn","siteurl":"http://H.o2o.jhcms.cn","province_name":"安徽","py":"H"},{"city_id":"7","province_id":"1","city_name":"淮北","pinyin":"H","theme_id":"1","logo":"","phone":"","city_code":"0561","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496545","domain":"H.o2o.jhcms.cn","siteurl":"http://H.o2o.jhcms.cn","province_name":"安徽","py":"H"},{"city_id":"8","province_id":"1","city_name":"安庆","pinyin":"A","theme_id":"1","logo":"","phone":"","city_code":"0556","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496569","domain":"A.o2o.jhcms.cn","siteurl":"http://A.o2o.jhcms.cn","province_name":"安徽","py":"A"},{"city_id":"9","province_id":"1","city_name":"马鞍山","pinyin":"M","theme_id":"1","logo":"","phone":"","city_code":"0555","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496613","domain":"M.o2o.jhcms.cn","siteurl":"http://M.o2o.jhcms.cn","province_name":"安徽","py":"M"},{"city_id":"10","province_id":"1","city_name":"铜陵","pinyin":"T","theme_id":"1","logo":"","phone":"","city_code":"0562","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496625","domain":"T.o2o.jhcms.cn","siteurl":"http://T.o2o.jhcms.cn","province_name":"安徽","py":"T"},{"city_id":"11","province_id":"1","city_name":"黄山","pinyin":"H","theme_id":"1","logo":"","phone":"","city_code":"0559","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496625","domain":"H.o2o.jhcms.cn","siteurl":"http://H.o2o.jhcms.cn","province_name":"安徽","py":"H"},{"city_id":"12","province_id":"1","city_name":"桐城","pinyin":"T","theme_id":"1","logo":"","phone":"","city_code":"0556","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496725","domain":"T.o2o.jhcms.cn","siteurl":"http://T.o2o.jhcms.cn","province_name":"安徽","py":"T"},{"city_id":"13","province_id":"1","city_name":"滁州","pinyin":"C","theme_id":"1","logo":"","phone":"","city_code":"0550","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496727","domain":"C.o2o.jhcms.cn","siteurl":"http://C.o2o.jhcms.cn","province_name":"安徽","py":"C"},{"city_id":"14","province_id":"1","city_name":"天长","pinyin":"T","theme_id":"1","logo":"","phone":"","city_code":"0550","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496888","domain":"T.o2o.jhcms.cn","siteurl":"http://T.o2o.jhcms.cn","province_name":"安徽","py":"T"},{"city_id":"15","province_id":"1","city_name":"阜阳","pinyin":"F","theme_id":"1","logo":"","phone":"","city_code":"0558","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496787","domain":"F.o2o.jhcms.cn","siteurl":"http://F.o2o.jhcms.cn","province_name":"安徽","py":"F"},{"city_id":"16","province_id":"1","city_name":"界首","pinyin":"J","theme_id":"1","logo":"","phone":"","city_code":"0558","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496802","domain":"J.o2o.jhcms.cn","siteurl":"http://J.o2o.jhcms.cn","province_name":"安徽","py":"J"},{"city_id":"17","province_id":"1","city_name":"巢湖","pinyin":"C","theme_id":"1","logo":"","phone":"","city_code":"0565","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496811","domain":"C.o2o.jhcms.cn","siteurl":"http://C.o2o.jhcms.cn","province_name":"安徽","py":"C"},{"city_id":"18","province_id":"1","city_name":"六安","pinyin":"L","theme_id":"1","logo":"","phone":"","city_code":"0564","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496859","domain":"L.o2o.jhcms.cn","siteurl":"http://L.o2o.jhcms.cn","province_name":"安徽","py":"L"},{"city_id":"19","province_id":"1","city_name":"亳州","pinyin":"B","theme_id":"1","logo":"","phone":"","city_code":"0558","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496870","domain":"B.o2o.jhcms.cn","siteurl":"http://B.o2o.jhcms.cn","province_name":"安徽","py":"B"},{"city_id":"20","province_id":"1","city_name":"池州","pinyin":"C","theme_id":"1","logo":"","phone":"","city_code":"0566","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496899","domain":"C.o2o.jhcms.cn","siteurl":"http://C.o2o.jhcms.cn","province_name":"安徽","py":"C"},{"city_id":"21","province_id":"1","city_name":"宁国","pinyin":"N","theme_id":"1","logo":"","phone":"","city_code":"0563","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1450496930","domain":"N.o2o.jhcms.cn","siteurl":"http://N.o2o.jhcms.cn","province_name":"安徽","py":"N"},{"city_id":"24","province_id":"2","city_name":"长沙","pinyin":"C","theme_id":"1","logo":"","phone":"","city_code":"","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1470471155","domain":"C.o2o.jhcms.cn","siteurl":"http://C.o2o.jhcms.cn","province_name":"湖南","py":"C"},{"city_id":"25","province_id":"3","city_name":"吉林动画学院","pinyin":"jl","theme_id":"1","logo":"","phone":"","city_code":"","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1496888511","domain":"jl.o2o.jhcms.cn","siteurl":"http://jl.o2o.jhcms.cn","province_name":"长春","py":"J"},{"city_id":"26","province_id":"4","city_name":"龙岩","pinyin":"ly","theme_id":"1","logo":"","phone":"","city_code":"","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1497251638","domain":"ly.o2o.jhcms.cn","siteurl":"http://ly.o2o.jhcms.cn","province_name":"福建","py":"L"},{"city_id":"27","province_id":"5","city_name":"济宁市","pinyin":"jn","theme_id":"1","logo":"","phone":"","city_code":"","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1497337612","domain":"jn.o2o.jhcms.cn","siteurl":"http://jn.o2o.jhcms.cn","province_name":"山东省","py":"J"},{"city_id":"28","province_id":"6","city_name":"焦作市","pinyin":"jz","theme_id":"1","logo":"","phone":"","city_code":"","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1498099560","domain":"jz.o2o.jhcms.cn","siteurl":"http://jz.o2o.jhcms.cn","province_name":"河南省","py":"J"},{"city_id":"29","province_id":"7","city_name":"上海","pinyin":"shanghai","theme_id":"1","logo":"","phone":"","city_code":"","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1498210987","domain":"shanghai.o2o.jhcms.cn","siteurl":"http://shanghai.o2o.jhcms.cn","province_name":"上海","py":"S"},{"city_id":"30","province_id":"8","city_name":"茂名市","pinyin":"mm","theme_id":"1","logo":"","phone":"","city_code":"","mobile":"","mail":"","kfqq":"","orderby":"50","audit":"1","dateline":"1498531206","domain":"mm.o2o.jhcms.cn","siteurl":"http://mm.o2o.jhcms.cn","province_name":"广东省","py":"M"}]
     */

    public List<ItemsEntity> items;

    public static class ItemsEntity {
        /**
         * city_id : 1
         * province_id : 1
         * city_name : 合肥
         * pinyin : H
         * theme_id : 1
         * logo : photo/201511/20151125_87BACD0EAE30C2AAFAF6E357064C06C4.png
         * phone : 400-009-8862
         * city_code : 0551
         * mobile : 13812365478
         * mail : 11@111.com
         * kfqq : 123456
         * orderby : 50
         * audit : 1
         * dateline : 1448415138
         * domain : H.o2o.jhcms.cn
         * siteurl : http://H.o2o.jhcms.cn
         * province_name : 安徽
         * py : H
         */

        public String city_id;
        public String province_id;
        public String city_name;
        public String pinyin;
        public String theme_id;
        public String logo;
        public String phone;
        public String city_code;
        public String mobile;
        public String mail;
        public String kfqq;
        public String orderby;
        public String audit;
        public String dateline;
        public String domain;
        public String siteurl;
        public String province_name;
        public String py;
    }
}
