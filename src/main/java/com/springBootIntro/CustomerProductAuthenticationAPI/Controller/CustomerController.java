package com.springBootIntro.CustomerProductAuthenticationAPI.Controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.springBootIntro.CustomerProductAuthenticationAPI.Exception.ExceptionResponse;
import com.springBootIntro.CustomerProductAuthenticationAPI.model.Customer;
import com.springBootIntro.CustomerProductAuthenticationAPI.model.Product;
import com.springBootIntro.CustomerProductAuthenticationAPI.request.CreateCustomerRequest;
import com.springBootIntro.CustomerProductAuthenticationAPI.response.CustomerAPIResponse;
import com.springBootIntro.CustomerProductAuthenticationAPI.response.ProductAPIResponse;
import com.springBootIntro.CustomerProductAuthenticationAPI.service.CustomerProductAPIService;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class CustomerController {

    @Autowired
    private CustomerProductAPIService ApiService;

    @GetMapping("/customers")
    public EntityModel<List<CustomerAPIResponse>> getAllCustomers() {
        try {
            List<Customer> customerList = ApiService.getAllCustomers();
            List<CustomerAPIResponse> allCustomerResponses = new ArrayList<>();
            for (Customer customer : customerList) {
                List<ProductAPIResponse> allProductResponse = new ArrayList<ProductAPIResponse>();
                customer.getProducts().stream().forEach(product ->
                {
                    allProductResponse.add(new ProductAPIResponse(product));
                });
                allCustomerResponses.add(new CustomerAPIResponse(customer.getId(), customer.getTitle(), allProductResponse));
            }
            return EntityModel.of(allCustomerResponses);

        } catch (Exception ex) {
            throw new ExceptionResponse(new Date(), ex.getLocalizedMessage(), ex.getCause().getMessage());

        }

    }

    @PostMapping("/customers")
    public EntityModel<CustomerAPIResponse> createNewCustomer(@Valid @RequestBody CreateCustomerRequest customerRequest){
        try {
            Customer customer=ApiService.createNewCustomer(customerRequest);
            return EntityModel.of(new CustomerAPIResponse(customer));
        }catch (Exception ex){
            throw new ExceptionResponse(new Date(), ex.getLocalizedMessage(), ex.getCause().getMessage());
        }
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable(name = "id") UUID id, @Valid @RequestBody CreateCustomerRequest customerRequest){
        try{
            if(ApiService.updateCustomerById(id,customerRequest)!=null){
                return new ResponseEntity<>("Succesfully Updated the Customer",HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Error in Request Format",HttpStatus.BAD_REQUEST);
            }

        }
        catch (Exception ex){
            throw new ExceptionResponse(new Date(),ex.getLocalizedMessage(),ex.getCause().getMessage());
        }
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Object> deleteCustomerById(@PathVariable(name = "id") UUID id){
        try {
            if(ApiService.deleteCustomerById(id)){
                return new ResponseEntity<>("Successfully deleted the customer",HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Unexpected Error",HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        catch (Exception ex){
            throw new ExceptionResponse(new Date(),ex.getLocalizedMessage(),ex.getCause().getMessage());
        }
    }








/*


    @GetMapping(value="/vehicle/find/{VIN}",produces = { "application/json", "application/xml" })
    public EntityModel<VehicleEntity> specificVehicle(@PathVariable String VIN) throws VehicleException{
        VehicleEntity vehicle=vehicheService.getVehicleByVIN(VIN);
        logger.info(vehicle.toString());
        if(vehicle ==null){
            throw new VehicleNotFoundException("VIN:-"+VIN+ "Not Found in the DB");
        }
        EntityModel<VehicleEntity> vehicleEntityEntityModel=EntityModel.of(vehicle);
        WebMvcLinkBuilder linkToVehicle=WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).specificVehicle(VIN));
        vehicleEntityEntityModel.add(linkToVehicle.withRel("The link to get the Specific Vehicle"));
        return vehicleEntityEntityModel;

    }


 */
}