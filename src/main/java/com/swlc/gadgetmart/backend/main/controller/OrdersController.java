package com.swlc.gadgetmart.backend.main.controller;


import com.swlc.gadgetmart.backend.main.dto.OrderDto;
import com.swlc.gadgetmart.backend.main.repo.OrderRepo;
import com.swlc.gadgetmart.backend.main.repo.UserRepo;
import com.swlc.gadgetmart.backend.main.util.TokenValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@CrossOrigin
@RequestMapping("/order")
public class OrdersController {

    private final OrderRepo orderRepo;

    private final UserRepo userRepo;


    public OrdersController(OrderRepo orderRepo1, UserRepo userRepo) {
        this.orderRepo = orderRepo1;
        this.userRepo = userRepo;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity placeOrder(@RequestHeader("Authorization") String auth, @RequestBody OrderDto orderDto) {
        boolean isValid = new TokenValidator(userRepo).validatePublicToken(auth);
        if (!isValid) {
            return new ResponseEntity<>("Unauthorized request", HttpStatus.UNAUTHORIZED);
        }
        try {
            boolean b = orderRepo.placeOrder(orderDto);
            return new ResponseEntity<>(b, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOrder(@RequestHeader("Authorization") String auth, @RequestBody OrderDto orderDTO) {
        boolean isValid = new TokenValidator(userRepo).validatePublicToken(auth);
        if (!isValid) {
            return new ResponseEntity<>("Unauthorized request", HttpStatus.UNAUTHORIZED);
        }
        try {
            boolean b = orderRepo.updateOrder(orderDTO);
            return new ResponseEntity<>(b, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOrders(@RequestHeader("Authorization") String auth, @RequestParam("userId") String userId) {
        boolean isValid = new TokenValidator(userRepo).validatePublicToken(auth);
        if (!isValid) {
            return new ResponseEntity<>("Unauthorized request", HttpStatus.UNAUTHORIZED);
        }
        try {
            List<OrderDto> allOrder = orderRepo.getAllOrder(userId);
            return new ResponseEntity<>(allOrder, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllOrders(@RequestHeader("Authorization") String auth) {
        boolean isValid = new TokenValidator(userRepo).validateAdminToken(auth);
        if (!isValid) {
            return new ResponseEntity<>("Unauthorized request", HttpStatus.UNAUTHORIZED);
        }
        try {
            List<OrderDto> allOrder = orderRepo.getAllOrder();
            return new ResponseEntity<>(allOrder, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
