package com.swlc.gadgetmart.backend.main.repo.impl;


import com.swlc.gadgetmart.backend.main.dto.OrderDto;
import com.swlc.gadgetmart.backend.main.dto.ProductDto;
import com.swlc.gadgetmart.backend.main.repo.OrderRepo;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepoImpl implements OrderRepo {


    @Override
    public ArrayList<ProductDto> findProducts() {

        ArrayList<ProductDto> products = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();


        // Getting abans items
//        ItemsDTO abansItems = restTemplate.getForObject(
//                "http://localhost:8080/abans/items",
//                ItemsDTO.class
//        );

//        System.out.println(abansItems);
//
//        if (abansItems != null){
//            items.addAll(abansItems.getItems());
//        }else{
//            System.out.println("Abans items not found!!");
//        }

        // Getting singer items
//        ItemsDTO singerItems = restTemplate.getForObject(
//                "http://localhost:8080/singer/items",
//                ItemsDTO.class
//        );

//        if (singerItems != null){
//            items.addAll(singerItems.getItems());
//        }else{
//            System.out.println("Singer items not found!!");
//        }

        // Getting softlogic items
        ProductDto softLogicProducts = restTemplate.getForObject(
                "http://localhost:8080/items",
                ProductDto.class
        );

        if (softLogicProducts != null){
            products.addAll(softLogicProducts.getProducts());
        }else{
            System.out.println("items not found!!");
        }

        return products;
    }

}
