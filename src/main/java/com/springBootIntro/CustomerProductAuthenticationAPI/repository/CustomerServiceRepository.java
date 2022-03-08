package com.springBootIntro.CustomerProductAuthenticationAPI.repository;

import com.springBootIntro.CustomerProductAuthenticationAPI.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerServiceRepository extends JpaRepository<Customer,UUID> {

}
