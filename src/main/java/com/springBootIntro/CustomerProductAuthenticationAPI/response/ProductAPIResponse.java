package com.springBootIntro.CustomerProductAuthenticationAPI.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.springBootIntro.CustomerProductAuthenticationAPI.model.Product;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductAPIResponse {

    @JsonProperty
    private UUID id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String description;

    @JsonProperty
    private int price;

    public ProductAPIResponse(Product product){
        this.id=product.getId();
        this.title=product.getTitle();
        this.description= product.getDescription();
    }

}
