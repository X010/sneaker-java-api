package com.sneaker.mall.api.dao.admin;

import com.sneaker.mall.api.model.Order;
import com.sneaker.mall.api.model.OrderItem;
import com.sneaker.mall.api.model.OrderOper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
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
public interface OrderDao {

    /**
     * 更新订单状态
     *
     * @param order_id
     * @param status
     */
    @Update("update db_order set status=#{status} where order_id=#{order_id} ")
    public void updateOrderByOrderId(@Param("order_id") String order_id, @Param("status") int status);

    /**
     * 根据ID修改状态
     *
     * @param id
     * @param status
     */
    @Update("update db_order set status=#{status} where id=#{id} ")
    public void updateOrderById(@Param("id") long id, @Param("status") int status);

    /**
     * 更新订单状态
     *
     * @param order_id
     * @param status
     */
    @Update("update db_order set status=#{status},update_time=#{time} where order_id=#{order_id} ")
    public void updateStatusOrderByOrderId(@Param("order_id") String order_id, @Param("status") int status, @Param("time") Date time);

    /**
     * 更新是否已支付
     *
     * @param order_id
     * @param ispay
     * @param time
     */
    @Update("update db_order set ispay=#{ispay},update_time=#{time} where order_id=#{order_id} ")
    public void updateIspayOrderByOrderId(@Param("order_id") String order_id, @Param("ispay") int ispay, @Param("time") Date time);


    /**
     * 更新是否已派车
     *
     * @param order_id
     * @param status
     */
    @Update("update db_order set sendcar=#{status},update_time=#{time} where order_id=#{order_id} ")
    public void updateSendCarOrderByOrderId(@Param("order_id") String order_id, @Param("status") int status, @Param("time") Date time);

    /**
     * 更新ERP订单号
     *
     * @param order_id
     * @param erp_order_id
     * @param time
     */
    @Update("update db_order set erp_order_id=#{erp_order_id},update_time=#{time} where order_id=#{order_id} ")
    public void updateErpOrderNo(@Param("order_id") String order_id, @Param("erp_order_id") String erp_order_id, @Param("time") Date time);

    /**
     * 分页面读取订单信息
     *
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from db_order where cid=#{cid} order by id desc limit #{start},#{limit}")
    public List<Order> findOrderByPage(@Param("cid") long cid, @Param("start") int start, @Param("limit") int limit);


    /**
     * 读取某个业务为该客户下的订单
     *
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from db_order where cid=#{cid} and suid=#{suid} order by id desc limit #{start},#{limit}")
    public List<Order> findOrderByPageAndUid(@Param("suid") long suid, @Param("cid") long cid, @Param("start") int start, @Param("limit") int limit);

    /**
     * 根据订单状态查询
     *
     * @param status
     * @return
     */
    @Select("select * from db_order where status=#{status}")
    public List<Order> findOrderByStatus(@Param("status") int status);


    /**
     * 统计条数
     *
     * @param cid
     * @return
     */
    @Select("select count(1) from db_order where cid=#{cid}")
    public int countOrderByCid(@Param("cid") long cid);

    /**
     * 统计该业务为某客户下的条数
     *
     * @param uid
     * @param cid
     * @return
     */
    @Select("select count(1) from db_order where cid=#{cid} and suid=#{suid}")
    public int countOrderByCidAndUid(@Param("suid") long suid, @Param("cid") long cid);

    /**
     * 分页面 按状态 读取订单信息
     *
     * @param status
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from db_order where  cid=#{cid} and status=#{status} order by id desc limit #{start},#{limit}")
    public List<Order> findOrderByPageAndStatus(@Param("cid") long cid, @Param("status") int status, @Param("start") int start, @Param("limit") int limit);

    /**
     * 分页面 按状态 用户ID 读取订单信息
     *
     * @param status
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from db_order where  cid=#{cid} and status=#{status} and suid=#{suid} order by id desc limit #{start},#{limit}")
    public List<Order> findOrderByPageAndStatusAndUid(@Param("suid") long suid, @Param("cid") long cid, @Param("status") int status, @Param("start") int start, @Param("limit") int limit);

    /**
     * 根据订单号查找
     *
     * @param id
     * @return
     */
    @Select("select * from db_order where id=#{id}")
    public Order findOrderById(@Param("id") long id);


    /**
     * 根据ERP订单号查找
     *
     * @param id
     * @return
     */
    @Select("select * from db_order where erp_order_id=#{erpid}")
    public Order findOrderByErpId(@Param("erpid") String id);

    /**
     * 根据商城订单号查找
     *
     * @param id
     * @return
     */
    @Select("select * from db_order where order_id=#{order_id}")
    public Order findOrderByOrderId(@Param("order_id") String id);


    /**
     * 统计条数
     *
     * @param cid
     * @param status
     * @return
     */
    @Select("select count(1) from db_order where cid=#{cid} and status=#{status}")
    public int countOrderByCidAndStatus(@Param("cid") long cid, @Param("status") int status);

    /**
     * 统计时间段内，根据公司与状态的订单数据
     *
     * @param status
     * @param start
     * @param end
     * @return
     */
    @Select("select count(1) from db_order where status=#{status} and update_time>=#{start} and update_time<#{end}")
    public int countByTimeAndCidAndStatus(@Param("status") int status, @Param("start") Date start, @Param("end") Date end);


    /**
     * 按业务员 状态 分页读取数据
     *
     * @param suid
     * @param status
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from db_order where suid=#{suid} and status=#{status} order by id desc limit #{start},#{limit}")
    public List<Order> findOrderByPageAndStatusAndSuid(@Param("suid") long suid, @Param("status") int status, @Param("start") int start, @Param("limit") int limit);

    /**
     * 按业务员 已确认状态 分页读取数据
     * 已确认 状态包括 3,4
     * @param suid
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from db_order where suid=#{suid} and status in (3,4) order by id desc limit #{start},#{limit}")
    public List<Order> findSureOrderByPageAndSuid(@Param("suid") long suid, @Param("start") int start, @Param("limit") int limit);

    /**
     * 按业务员  分页读取数据
     *
     * @param suid
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from db_order where suid=#{suid}  order by id desc limit #{start},#{limit}")
    public List<Order> findOrderByPageAndSuid(@Param("suid") long suid, @Param("start") int start, @Param("limit") int limit);

    /**
     * 根据时间根据状态查询订单列表
     *
     * @param startTime
     * @param endTime
     * @param status
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from db_order where status=#{status} and update_time>=#{startTime} and update_time<#{endTime} order by id desc limit #{start},#{limit}")
    public List<Order> findOrderByTimeAndStatus(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("status") int status, @Param("start") int start, @Param("limit") int limit);

    /**
     * 根据超级订单号查询订单列表
     *
     * @param superOrderId
     * @return
     */
    @Select("select * from db_order where super_order_id=#{superOrderId}")
    public List<Order> findOrderBySuperOrderId(@Param("superOrderId") String superOrderId);

    /**
     * 根据OrderID查询 OrderItem信息
     *
     * @param orderid
     * @return
     */
    @Select("select * from db_order_item where order_id=#{orderid} order by giveaway asc")
    public List<OrderItem> findOrderItemByOrderId(@Param("orderid") String orderid);


    /**
     * 添加订单项
     *
     * @param orderItem
     */
    @Insert("insert into db_order_item(total,mgid,market_id,market_name,price,gbarcode,gname,gcode,gid,scid,order_id,memo,gphoto,giveaway,bindId,total_amount,bindTotal)values(#{total},#{mgid}," +
            "#{market_id},#{market_name},#{price},#{gbarcode},#{gname},#{gcode},#{gid},#{scid},#{order_id},#{memo},#{gphoto},#{giveaway},#{bindId},#{total_amount},#{bindTotal})")
    public void addOrderItem(OrderItem orderItem);

    /**
     * 添加订单
     *
     * @param order
     */
    @Insert("insert into db_order(super_order_id,erp_order_id,order_id,create_time,total_amount,receipt,province,city,county,status,cid,company_name,scid,supplier_company_name,uid,suid,contacts,phone,memo,delivery,favorable,pt)" +
            "values(#{super_order_id},#{erp_order_id},#{order_id},#{create_time},#{total_amount},#{receipt},#{province},#{city},#{county},#{status},#{cid},#{company_name},#{scid},#{supplier_company_name}," +
            "#{uid},#{suid},#{contacts},#{phone},#{memo},#{delivery},#{favorable},#{pt})")
    public void addOrder(Order order);


    /**
     * 统计页面员订单条数
     *
     * @param suid
     * @param status
     * @return
     */
    @Select("select count(1) from db_order where suid=#{suid} and status=#{status}")
    public int countOrderBySalemanAndStatusAndPage(@Param("suid") long suid, @Param("status") int status);

    /**
     * 统计业务员 已确认状态 订单条数
     * 已确认状态为 3,4
     * @param suid
     * @return
     */
    @Select("select count(1) from db_order where suid=#{suid} and status in (3,4)")
    public int countSureOrderBySalemanPage(@Param("suid") long suid);

    /**
     * 统计业务为该客户下单数据
     *
     * @param cid
     * @param suid
     * @param status
     * @return
     */
    @Select("select count(1) from db_order where cid=#{cid} and suid=#{suid} and status=#{status}")
    public int countOrderByUidAndStatusAndPage(@Param("cid") long cid, @Param("suid") long suid, @Param("status") int status);


    /**
     * 统计页面员订单条数
     *
     * @param suid
     * @return
     */
    @Select("select count(1) from db_order where suid=#{suid}")
    public int countOrderBySaleman(@Param("suid") long suid);


    /**
     * 添加订单操作信息
     *
     * @param orderOper
     */
    @Insert("insert into db_order_oper(order_id,oper_time,status,action,order_no)values(#{order_id},#{oper_time},#{status},#{action},#{order_no})")
    public void insertOrderOper(OrderOper orderOper);

    /**
     * 获取订单跟踪信息
     *
     * @param id
     * @return
     */
    @Select("select * from db_order_oper where order_id=#{id}")
    public List<OrderOper> findOrderOperInfoByOrderId(@Param("id") long id);

    /**
     * 获取订单跟踪信息
     *
     * @param orderNo
     * @return
     */
    @Select("select * from db_order_oper where order_no=#{orderNo}")
    public List<OrderOper> findOrderOperInfoByOrderNo(@Param("orderNo") String orderNo);
}
