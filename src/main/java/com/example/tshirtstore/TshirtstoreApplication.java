package com.example.tshirtstore;

import com.example.tshirtstore.dto.ProductDto;
import com.example.tshirtstore.model.Role;
import com.example.tshirtstore.model.User;
import com.example.tshirtstore.repository.UserRepository;
import com.example.tshirtstore.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TshirtstoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(TshirtstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, 
                                     PasswordEncoder passwordEncoder,
                                     ProductService productService) {
        return args -> {
            // Create admin user if not exists
            if (userRepository.findByUsername("Gokul").isEmpty()) {
                User admin = new User();
                admin.setUsername("Gokul");
                admin.setEmail("gokualagarsamy301@gmail.com");
                admin.setPassword(passwordEncoder.encode("Gokul17"));
                admin.setAddress("Admin Address");
                admin.setRole(Role.ROLE_ADMIN);
                userRepository.save(admin);
            }

            // Add some sample products if none exist
            if (productService.getAllProducts().isEmpty()) {
                ProductDto product1 = new ProductDto();
                product1.setName("Classic White T-Shirt");
                product1.setCategory("Basic");
                product1.setDescription("100% cotton, comfortable fit, classic white t-shirt");
                product1.setPrice(19.99);
                product1.setImageUrl("https://example.com/white-tshirt.jpg");
                productService.saveProduct(product1);

                ProductDto product2 = new ProductDto();
                product2.setName("Black Graphic Tee");
                product2.setCategory("Graphic");
                product2.setDescription("Soft black t-shirt with cool graphic print");
                product2.setPrice(24.99);
                product2.setImageUrl("https://example.com/black-graphic.jpg");
                productService.saveProduct(product2);

                ProductDto product3 = new ProductDto();
                product3.setName("Striped Polo Shirt");
                product3.setCategory("Polo");
                product3.setDescription("Classic striped polo shirt, perfect for casual wear");
                product3.setPrice(29.99);
                product3.setImageUrl("https://example.com/striped-polo.jpg");
                productService.saveProduct(product3);
            }
        };
    }
}