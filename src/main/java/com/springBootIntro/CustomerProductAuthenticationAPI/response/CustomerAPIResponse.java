package com.springBootIntro.CustomerProductAuthenticationAPI.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.springBootIntro.CustomerProductAuthenticationAPI.model.Customer;
import com.springBootIntro.CustomerProductAuthenticationAPI.model.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerAPIResponse {

    @JsonProperty
    private UUID id;
    @JsonProperty
    private String title;
    @JsonProperty
    private List<ProductAPIResponse> products;

    public CustomerAPIResponse(Customer customer){
        this.title=customer.getTitle();
        if(customer.getProducts() !=null){
            products=new ArrayList<ProductAPIResponse>();
            for(Product product: customer.getProducts()){
                products.add(new ProductAPIResponse(product));
            }

        }
    }
}
