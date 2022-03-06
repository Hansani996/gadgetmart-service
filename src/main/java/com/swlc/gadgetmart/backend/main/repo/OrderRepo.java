package com.swlc.gadgetmart.backend.main.repo;



import com.swlc.gadgetmart.backend.main.dto.OrderDto;

import java.util.List;

public interface OrderRepo {
    boolean placeOrder(OrderDto orderDTO) throws Exception;

    boolean updateOrder(OrderDto orderDTO) throws Exception;

    List<OrderDto> getAllOrder(String userId) throws Exception;

    List<OrderDto> getAllOrder() throws Exception;
}
