package com.swlc.gadgetmart.backend.main.repo;



import com.swlc.gadgetmart.backend.main.dto.ProductDto;

import java.util.ArrayList;

public interface ProductRepo {

    ArrayList<ProductDto> findItems();
}
