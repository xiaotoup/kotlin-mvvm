package com.zh.kotlin_mvvm.utils;

import com.google.gson.annotations.SerializedName;
import com.zh.common.base.bean.BaseResponse;

/**
 * Created by Tanhongmu
 * Created Date 2020-09-28 11:44
 */
public class AliOrderInfo extends BaseResponse<AliOrderInfo> {

    /**
     * body : method=alipay.trade.app.pay&app_id=2021000119690678&timestamp=2020-09-28+11%3A43%3A04&format=json&version=1.0&alipay_sdk=alipay-easysdk-php-2.0.0&charset=UTF-8&sign_type=RSA2&app_cert_sn=283f000c55f56a87db6c0597944e00cc&alipay_root_cert_sn=687b59193f3f462dd5336e5abf83c5d8_02941eef3187dddf3d3b83462e1dfcf6&biz_content=%7B%22subject%22%3A%22%E6%B5%8B%E8%AF%95%E5%95%86%E5%93%81%22%2C%22out_trade_no%22%3A1601264584%2C%22total_amount%22%3A%220.01%22%7D&notify_url=https%3A%2F%2Fapi.mdweilai.cn%2Fapi%2Fv1%2Fpayment%2Fpaycallbackforali&sign=S%2FjbvgM71kVjlT2cIMbubeBUfN0kX21k%2ByNseO1%2F1huTOIU8tUdQOzRgCD2G68V2pveOUXGG2NeLXGhjbpXyrE86F9Wzn17NxVBJEUn4C92L8HG%2F8a%2B1J6XGcNcjHDRW%2Ft93PgBrW0cSxZip2TrA3aFbsUMCAsWwg3zNiSaYyl%2BwG%2FuiUyTAWYy8ExBbP3iv0y%2BG49Asd2WzotSo6zhmX89KPCjD1zMl26WCyv35WuT0knW1AD9OcUFYUmdFGqXj5IGXiX1ERJLlbPmbcl5FNRilrrKSVRBAqlTkEAJLCsoHNGTdbDwqTT58uPLisvzTdaF7jKwAVFGbhwc7xwsJBQ%3D%3D
     */

    private String body;

    //微信
    private String appid;
    private String partnerid;
    private String prepayid;
    @SerializedName("package")
    private String packageX;
    private String noncestr;
    private String timestamp;
    private String sign;


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
