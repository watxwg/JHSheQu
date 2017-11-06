package com.jhcms.mall.model;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/13 11:36
 * 描述：店铺详情
 */

public class ShopDetailModel {
    private ShopInfoModel detail;
    private List<ImageInfoModel> banners;
    private List<CouponsInfoModel> coupons;
    private List<CategoryModel> cates;
    private List<ProductItemModel> tuijian;
    private ShareInfoModel share_info;
    private List<AdModel> advs;

    public List<AdModel> getAdvs() {
        return advs;
    }

    public void setAdvs(List<AdModel> advs) {
        this.advs = advs;
    }

    public ShareInfoModel getShare_info() {
        return share_info;
    }

    public void setShare_info(ShareInfoModel share_info) {
        this.share_info = share_info;
    }

    public ShopInfoModel getDetail() {
        return detail;
    }

    public void setDetail(ShopInfoModel detail) {
        this.detail = detail;
    }

    public List<ImageInfoModel> getBanners() {
        return banners;
    }

    public void setBanners(List<ImageInfoModel> banners) {
        this.banners = banners;
    }

    public List<CouponsInfoModel> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponsInfoModel> coupons) {
        this.coupons = coupons;
    }

    public List<CategoryModel> getCates() {
        return cates;
    }

    public void setCates(List<CategoryModel> cates) {
        this.cates = cates;
    }

    public List<ProductItemModel> getTuijian() {
        return tuijian;
    }

    public void setTuijian(List<ProductItemModel> tuijian) {
        this.tuijian = tuijian;
    }
}
