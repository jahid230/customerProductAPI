package com.springBootIntro.CustomerProductAuthenticationAPI.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springBootIntro.CustomerProductAuthenticationAPI.model.Product;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateCustomerRequest {

    @JsonProperty
    @NotNull(message = "Title is required")
    @NotBlank(message = "Title can't be empty")
    @Min(0)
    @Max(255)
    private String title;

    @JsonProperty
    private Boolean is_deleted;

    @JsonProperty
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @JsonProperty
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified_at;

    @JsonProperty
    private List<CreateProductRequest> products;

}
