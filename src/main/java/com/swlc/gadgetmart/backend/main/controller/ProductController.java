package com.swlc.gadgetmart.backend.main.controller;


import com.swlc.gadgetmart.backend.main.dto.ProductDto;
import com.swlc.gadgetmart.backend.main.repo.ProductRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;


@Controller
@CrossOrigin
@RequestMapping("/product")
public class ProductController {


    private final ProductRepo productRepo;

    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllItems() {
        try {
            ArrayList<ProductDto> items = productRepo.findItems();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
