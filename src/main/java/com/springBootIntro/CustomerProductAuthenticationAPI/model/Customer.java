package com.springBootIntro.CustomerProductAuthenticationAPI.model;


import com.springBootIntro.CustomerProductAuthenticationAPI.request.CreateCustomerRequest;
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
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private UUID id;

    @Column(name="title",length = 255)
    @Size(min = 0,max = 255)
    private String title;

    @Column(name="is_deleted")
    private Boolean is_deleted;

    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at;

    @Column(name="modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modified_at;

    @OneToMany(mappedBy = "customer")
    private List<Product> products;

    public Customer(CreateCustomerRequest customerRequest){
        this.title=customerRequest.getTitle();
        this.is_deleted=customerRequest.getIs_deleted();
        this.created_at=customerRequest.getCreated_at();
        this.modified_at=customerRequest.getModified_at();
        this.products=getProducts();
    }
}
