package com.comdosoft.financial.timing.mapper.zhangfu;

import java.util.List;
import java.util.Map;

public interface OrderMapper {

    void cleanOrder();

    List<Map<String, Object>>  findPersonGoodsQuantity();

    void update_goods_stock(String good_id, String quantity);

}
