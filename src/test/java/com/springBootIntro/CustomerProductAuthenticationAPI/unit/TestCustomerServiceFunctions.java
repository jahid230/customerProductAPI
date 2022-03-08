package com.springBootIntro.CustomerProductAuthenticationAPI.unit;


import com.springBootIntro.CustomerProductAuthenticationAPI.repository.CustomerServiceRepository;
import com.springBootIntro.CustomerProductAuthenticationAPI.service.CustomerProductAPIService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
public class TestCustomerServiceFunctions {

    Logger logger= (Logger) LoggerFactory.getLogger(TestCustomerServiceFunctions.class);

    @InjectMocks
    private CustomerProductAPIService customerProductAPIService;
    @Mock
    private CustomerServiceRepository customerServiceRepository;




}
