package com.itheima.constants;

public interface Global {
    /**
     * 返回结果  失败
     */
     int RESULT_CODE_FAIL=0;
    /**
     * 返回结果  成功
     */
     int RESULT_CODE_SUCCESS=1;
    /**
     * 返回结果  必须登录
     */
     int RESULT_CODE_NEED_LOGIN=2;
    /**
     * 返回结果  权限不足
     */
     int RESULT_CODE_NO_AUTH=3;
    /**
     * 热门
     */
     int PRODUCT_IS_HOT=1;
    /**
     * 非热门
     */
     int PRODUCT_IS_NOT_HOT=0;
    /**
     * 未付款
     */
    int ORDER_STATE_WEIFUKUAN=0;
    /**
     * 已付款
     */
     int ORDER_STATE_YIFUKUAN=1;
    /**
     * 已发货
     */
    int ORDER_STATE_YIFAHUO=2;
    /**
     * 已完成
     */
     int ORDER_STATE_YIWANCHENG=3;


}
