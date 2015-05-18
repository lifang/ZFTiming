package com.comdosoft.financial.timing.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.comdosoft.financial.timing.mapper.zhangfu.OrderMapper;


@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class); 

    @Transactional("transactionManager-trades")
    public void cleanOrder() {
    	logger.debug("进入订单清理");
        List<Map<String, Object>>  m = orderMapper.findPersonGoodsQuantity();
        if(m.size()<1){
        	logger.debug("没有找到需要清理的订单");
        }else{
        	logger.debug("清理订单开始");
            orderMapper.cleanOrder();
        }
        for(Map<String, Object> mm :m){
            String good_id = mm.get("good_id")==null?"":mm.get("good_id").toString();
            String quantity = mm.get("quantity")==null?"":mm.get("quantity").toString();
            logger.debug("订单清理start==id=>>>"+good_id+"==库存==>>>"+quantity);
            if(good_id !="" && quantity!=""){
                orderMapper.update_goods_stock(good_id,quantity);
            }
        }
       
    }
    
}
