package com.sneaker.mall.api.model;

import java.util.List;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class AchievementInfo {

    /**
     * 业务员ID
     */
    private long uid;

    /**
     * 业务员名称
     */
    private String uname;

    /**
     * 业务员总客户数
     */
    private String all_customer_count;

    /**
     * 日新增订单数
     */
    private String day_order_count;

    /**
     * 日销售额
     */
    private String day_order_amount;

    /**
     * 日新增客户数
     */
    private String day_customer_count;

    /**
     * 月新增订单数
     */
    private String period_order_count;

    /**
     * 月销售额
     */
    private String period_order_amount;


    /**
     * 月新增客户数
     */
    private String period_customer_count;


    /**
     * 箱数任务完成率
     */
    private String complete_rate;

    /**
     * 月完成箱数
     */
    private String period_box_total;

    /**
     * 箱数任务指标
     */
    private String task_total;

    /**
     * 金额任务完成率
     */
    private String amount_complete_rate;

    /**
     * 金额任务指标
     */
    private String amount_task_total;

    /**
     * 排名
     */
    private int ranking;

    /**
     * 排名百分比，击败了百分之多少的人
     */
    private String ranking_percent;

    /**
     * 列表
     */
    private List<erpGoodList> goods_list;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAll_customer_count() {
        return all_customer_count;
    }

    public void setAll_customer_count(String all_customer_count) {
        this.all_customer_count = all_customer_count;
    }

    public String getDay_order_count() {
        return day_order_count;
    }

    public void setDay_order_count(String day_order_count) {
        this.day_order_count = day_order_count;
    }

    public String getDay_order_amount() {
        return day_order_amount;
    }

    public void setDay_order_amount(String day_order_amount) {
        this.day_order_amount = day_order_amount;
    }

    public String getDay_customer_count() {
        return day_customer_count;
    }

    public void setDay_customer_count(String day_customer_count) {
        this.day_customer_count = day_customer_count;
    }

    public String getPeriod_order_count() {
        return period_order_count;
    }

    public void setPeriod_order_count(String period_order_count) {
        this.period_order_count = period_order_count;
    }

    public String getPeriod_order_amount() {
        return period_order_amount;
    }

    public void setPeriod_order_amount(String period_order_amount) {
        this.period_order_amount = period_order_amount;
    }

    public String getPeriod_customer_count() {
        return period_customer_count;
    }

    public void setPeriod_customer_count(String period_customer_count) {
        this.period_customer_count = period_customer_count;
    }

    public String getComplete_rate() {
        return complete_rate;
    }

    public void setComplete_rate(String complete_rate) {
        this.complete_rate = complete_rate;
    }

    public String getPeriod_box_total() {
        return period_box_total;
    }

    public void setPeriod_box_total(String period_box_total) {
        this.period_box_total = period_box_total;
    }

    public String getTask_total() {
        return task_total;
    }

    public void setTask_total(String task_total) {
        this.task_total = task_total;
    }

    public String getAmount_complete_rate() {
        return amount_complete_rate;
    }

    public void setAmount_complete_rate(String amount_complete_rate) {
        this.amount_complete_rate = amount_complete_rate;
    }

    public String getAmount_task_total() {
        return amount_task_total;
    }

    public void setAmount_task_total(String amount_task_total) {
        this.amount_task_total = amount_task_total;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getRanking_percent() {
        return ranking_percent;
    }

    public void setRanking_percent(String ranking_percent) {
        this.ranking_percent = ranking_percent;
    }

    public List<erpGoodList> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<erpGoodList> goods_list) {
        this.goods_list = goods_list;
    }

    public static class erpGoodList {
        private long gid;
        private String gname;
        private String gspec;
        private String total;
        private String amount;
        private int box_total;

        public long getGid() {
            return gid;
        }

        public void setGid(long gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getGspec() {
            return gspec;
        }

        public void setGspec(String gspec) {
            this.gspec = gspec;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getBox_total() {
            return box_total;
        }

        public void setBox_total(int box_total) {
            this.box_total = box_total;
        }
    }
}
