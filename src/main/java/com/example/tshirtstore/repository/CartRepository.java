package com.example.tshirtstore.repository;

import com.example.tshirtstore.model.Cart;
import com.example.tshirtstore.model.User;
import com.example.tshirtstore.model.Product; // ✅ Make sure this is imported

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser(User user);

    Optional<Cart> findByUserAndProduct(User user, Product product);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user = :user")
    void deleteByUser(@Param("user") User user);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user = :user AND c.product = :product")
    void deleteByUserAndProduct(@Param("user") User user, @Param("product") Product product);
}
