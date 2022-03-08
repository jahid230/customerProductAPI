package com.springBootIntro.CustomerProductAuthenticationAPI.service;

import com.springBootIntro.CustomerProductAuthenticationAPI.Exception.ExceptionResponse;
import com.springBootIntro.CustomerProductAuthenticationAPI.Exception.ProductNotFoundException;
import com.springBootIntro.CustomerProductAuthenticationAPI.Exception.UserNotFoundException;
import com.springBootIntro.CustomerProductAuthenticationAPI.model.Customer;
import com.springBootIntro.CustomerProductAuthenticationAPI.model.Product;
import com.springBootIntro.CustomerProductAuthenticationAPI.repository.CustomerServiceRepository;
import com.springBootIntro.CustomerProductAuthenticationAPI.repository.ProductServiceRepository;
import com.springBootIntro.CustomerProductAuthenticationAPI.request.CreateCustomerRequest;
import com.springBootIntro.CustomerProductAuthenticationAPI.request.CreateProductRequest;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class CustomerProductAPIService {

    @Autowired
    private CustomerServiceRepository customerServiceRepository;
    @Autowired
    private ProductServiceRepository productServiceRepository;

    public List<Customer> getAllCustomers() throws RuntimeException{

        try{
            if(customerServiceRepository.findAll()==null){
                return null;
            }
            else{
                return customerServiceRepository.findAll();
            }
        }catch(Exception ex) {

            throw new RuntimeException("There is no Customer in the Database");

        }

    }

    public Customer createNewCustomer(CreateCustomerRequest createCustomerRequest){

        try{
            if(createCustomerRequest!=null){
                Customer to_be_saved_Customer= new Customer();
                to_be_saved_Customer.setTitle(createCustomerRequest.getTitle());
                to_be_saved_Customer.setIs_deleted(createCustomerRequest.getIs_deleted());
                to_be_saved_Customer.setCreated_at(createCustomerRequest.getCreated_at());
                to_be_saved_Customer.setModified_at(createCustomerRequest.getModified_at());
                customerServiceRepository.save(to_be_saved_Customer);
                List<Product> productsList=new ArrayList<Product>();
                if(createCustomerRequest.getProducts()!= null){
                    for(CreateProductRequest productRequest: createCustomerRequest.getProducts()){
                        Product product=new Product();
                        product.setTitle(productRequest.getTitle());
                        product.setDescription(productRequest.getDescription());
                        product.setPrice(productRequest.getPrice());
                        product.setIs_deleted(productRequest.getIs_deleted());
                        product.setCreated_at(productRequest.getCreated_at());
                        product.setModified_at(productRequest.getModified_at());
                        product.setCustomer(to_be_saved_Customer);
                        productsList.add(product);
                    }
                    productServiceRepository.saveAll(productsList);
                }

                return to_be_saved_Customer;
            }
            else{
                throw new ExceptionResponse(new Date(),"Bad Request Format","Internal Server could");
            }
        }
        catch(Exception ex){
                throw new ExceptionResponse(new Date(), ex.getMessage(), ex.getCause().getMessage());
        }


    }

    public Customer getCustomerById(UUID id){
        try{
            Optional<Customer> customer=customerServiceRepository.findById(id);
            if(customer!=null){
                return customer.get();
            }
            else{
                throw new UserNotFoundException("Customer: "+id+" Not found in the db");
            }

        }catch (ExceptionResponse e){
            throw new ExceptionResponse(new Date(),e.getMessage(),e.getDetails());
        }

    }

    public Customer updateCustomerById(UUID id, CreateCustomerRequest createCustomerRequest){
        try{
            Customer customer= getCustomerById(id);
            if(getCustomerById(id)!=null){
                customer.setTitle(createCustomerRequest.getTitle());
                customer.setIs_deleted(createCustomerRequest.getIs_deleted());
                customer.setCreated_at(createCustomerRequest.getCreated_at());
                customer.setModified_at(createCustomerRequest.getModified_at());
                customerServiceRepository.save(customer);
                List<Product> productsList=new ArrayList<Product>();
                if(createCustomerRequest.getProducts()!= null){
                    for(CreateProductRequest productRequest: createCustomerRequest.getProducts()){
                        Product product=new Product();
                        product.setTitle(productRequest.getTitle());
                        product.setDescription(productRequest.getDescription());
                        product.setPrice(productRequest.getPrice());
                        product.setIs_deleted(productRequest.getIs_deleted());
                        product.setCreated_at(productRequest.getCreated_at());
                        product.setModified_at(productRequest.getModified_at());
                        product.setCustomer(customer);
                        productsList.add(product);
                    }
                    productServiceRepository.saveAll(productsList);
                }
            return customer;
            }
            else {
                SQLException sqlException=new SQLException();
                JDBCException jdbcException=new JDBCException("DB Error",sqlException);
                throw new ExceptionResponse(new Date(), jdbcException.getMessage(), jdbcException.getSQLException().getMessage());
            }
        }
        catch (ExceptionResponse e){
            throw new ExceptionResponse(new Date(),e.getMessage(),e.getDetails());
        }
    }

    public Boolean deleteCustomerById(UUID id){

        try{
            Customer customer=getCustomerById(id);
            List<Product> ProductswithSameCustomer=getAllProductsByCustomer(id);
            for(Product product: ProductswithSameCustomer){
                deleteProductById(product.getId());
            }
            customerServiceRepository.delete(customer);
            return true;
            }
        catch(Exception ex){
            throw new ExceptionResponse(new Date(),ex.getLocalizedMessage(),ex.getCause().getMessage());
        }
    }

    public List<Product> getAllProductsByCustomer(UUID id){
        try{
            Customer customer=getCustomerById(id);
            List<Product> products=customer.getProducts();
            if(products!=null){
                return products;
            }
            else if(products.isEmpty()){
                return null;
            }
            else{
                SQLException sqlException=new SQLException();
                JDBCException jdbcException=new JDBCException("DB Error",sqlException);
                throw new ExceptionResponse(new Date(), jdbcException.getMessage(), jdbcException.getSQLException().getMessage());
            }
        }
        catch(Exception ex){
            throw new ExceptionResponse(new Date(),ex.getLocalizedMessage(),ex.getCause().getMessage());
        }
    }

    public Product getProductById(UUID id){
        try{
            Optional<Product> product=productServiceRepository.findById(id);
            if(product!=null){
                return product.get();
            }
            else if(product.isEmpty()){
                throw new ProductNotFoundException("Product: "+id+" Not found in the db");
            }
            else{
                SQLException sqlException=new SQLException();
                JDBCException jdbcException=new JDBCException("DB Error",sqlException);
                throw new ExceptionResponse(new Date(), jdbcException.getMessage(), jdbcException.getSQLException().getMessage());
            }

        }catch (ExceptionResponse e){
            throw new ExceptionResponse(new Date(),e.getMessage(),e.getDetails());
        }

    }

    public Product createProductByCustomerId( UUID id, CreateProductRequest productRequest){
        try{
            Customer customer=getCustomerById(id);
            Product product=new Product(productRequest);
            if(customer.getProducts().isEmpty()){
                List<Product> productList=new ArrayList<>();
                productList.add(product);
                customer.setProducts(productList);
                customerServiceRepository.save(customer);
                productServiceRepository.save(product);
                return product;
            }
            if(!customer.getProducts().isEmpty()){
                List<Product> currentProductList=customer.getProducts();
                currentProductList.add(product);
                customerServiceRepository.save(customer);
                productServiceRepository.save(product);
                return product;
            }
            else{
                SQLException sqlException=new SQLException();
                JDBCException jdbcException=new JDBCException("DB Error",sqlException);
                throw new ExceptionResponse(new Date(), jdbcException.getMessage(), jdbcException.getSQLException().getMessage());
            }
        }
        catch(Exception ex){
            throw new ExceptionResponse(new Date(),ex.getLocalizedMessage(),ex.getCause().getMessage());
        }
    }



    public Product updateProductById(UUID id,CreateProductRequest productRequest){
        try{
            Product product=getProductById(id);
            if(product!=null){
                product.setTitle(productRequest.getTitle());
                product.setDescription(productRequest.getDescription());
                product.setPrice(productRequest.getPrice());
                product.setCreated_at(productRequest.getCreated_at());
                product.setModified_at(productRequest.getModified_at());
                product.setIs_deleted(productRequest.getIs_deleted());
                productServiceRepository.save(product);
                Customer customer=getCustomerById(product.getCustomer().getId());

                List<Product> productList=customer.getProducts();
                for(int i=0; i<productList.size();i++){
                    if(productList.get(i).getId()==id){
                        productList.add(i,product);
                    }
                }
                customer.setProducts(productList);
                customerServiceRepository.save(customer);
                return product;
            }
            else{
                SQLException sqlException=new SQLException();
                JDBCException jdbcException=new JDBCException("DB Error",sqlException);
                throw new ExceptionResponse(new Date(), jdbcException.getMessage(), jdbcException.getSQLException().getMessage());
            }
        }catch (Exception ex){
            throw new ExceptionResponse(new Date(),ex.getLocalizedMessage(),ex.getCause().getMessage());
        }

    }

    public Boolean deleteProductById(UUID id){
                  try{
                      if(productServiceRepository.findById(id)!=null){
                                productServiceRepository.delete(productServiceRepository.findById(id).get());
                                return true;
                      }
                      else if(productServiceRepository.findById(id)==null) {
                          throw new ProductNotFoundException("Product id: "+ id+" Not found in the DB");
                      }
                      else{
                          SQLException sqlException=new SQLException();
                          JDBCException jdbcException=new JDBCException("DB Error",sqlException);
                          throw new ExceptionResponse(new Date(), jdbcException.getMessage(), jdbcException.getSQLException().getMessage());
                      }
                  }
                  catch (Exception e){
                      throw new ExceptionResponse(new Date(),e.getLocalizedMessage(),e.getCause().getMessage());
                  }
    }


}



