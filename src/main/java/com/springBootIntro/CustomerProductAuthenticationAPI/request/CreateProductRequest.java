package com.springBootIntro.CustomerProductAuthenticationAPI.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springBootIntro.CustomerProductAuthenticationAPI.model.Customer;
import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateProductRequest {


    @JsonProperty
    @NotNull(message = "Title is required")
    @NotBlank(message = "Title can't be empty")
    @Min(0)
    @Max(255)
    private String title;

    @JsonProperty
    @NotNull(message = "Description is required")
    @NotBlank(message = "Description can't be empty")
    @Min(0)
    @Max(1024)
    private String description;

    @JsonProperty
    @NotNull(message = "Price is required")
    @NotBlank(message = "Price can't be empty")
    @Min(2)
    @Max(10)
    private int price;

    @JsonProperty
    private Boolean is_deleted;

    @JsonProperty
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @JsonProperty
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified_at;



}
