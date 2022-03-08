package com.springBootIntro.CustomerProductAuthenticationAPI.repository;

import com.springBootIntro.CustomerProductAuthenticationAPI.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductServiceRepository extends JpaRepository<Product,UUID> {
}
