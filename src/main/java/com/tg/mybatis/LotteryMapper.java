package com.tg.mybatis;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by linzc on 2017/7/13.
 */
public interface LotteryMapper {
    @Select("select * from lottery_activity4")
    List<LotteryActivity> selectAll();
}
