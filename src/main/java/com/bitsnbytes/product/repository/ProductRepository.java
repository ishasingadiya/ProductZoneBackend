package com.bitsnbytes.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitsnbytes.product.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Long>{
	 

}
