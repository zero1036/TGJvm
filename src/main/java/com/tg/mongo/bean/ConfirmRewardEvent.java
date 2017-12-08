package com.tg.mongo.bean;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by linzc on 2017/8/7.
 */
@Entity(value = "ConfirmRewardEvent", noClassnameStored = true)
public class ConfirmRewardEvent {
    /**
     * 发奖请求流水号
     */
    @Property
    private String reqNo;

    /**
     * 发奖成功
     */
    @Property
    private Boolean success;

    /**
     * 客户Id
     */
    @Property
    private Integer customerId;

    /**
     * CouponId
     */
    @Property
    private Integer couponId;
}
