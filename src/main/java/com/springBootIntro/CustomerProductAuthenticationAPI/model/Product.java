package com.springBootIntro.CustomerProductAuthenticationAPI.model;


import com.springBootIntro.CustomerProductAuthenticationAPI.request.CreateProductRequest;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name="title",length = 255)
    @Size(min = 0,max = 255)
    private String title;

    @Column(name="description",length = 1024)
    @Size(min = 0,max = 1024)
    private String description;

    @Column(name = "price")
    @Size(min=2, max = 10)
    private int price;

    @Column(name = "is_deleted")
    private Boolean is_deleted;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at;

    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modified_at;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Product(CreateProductRequest createProductRequest){
        this.title=createProductRequest.getTitle();
        this.description=createProductRequest.getDescription();
        this.price=createProductRequest.getPrice();
        this.is_deleted=createProductRequest.getIs_deleted();
        this.created_at=createProductRequest.getCreated_at();
        this.modified_at=createProductRequest.getModified_at();

    }

}
