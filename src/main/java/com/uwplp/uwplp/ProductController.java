package com.uwplp.uwplp;

import com.uwplp.components.ProductDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class ProductController {
    @GetMapping
    public String get() {
        UwplpApplication.log.info("GET REQUEST");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContext.class);
        ProductDAO productDAO = (ProductDAO)context.getBean("productDAO");
        return productDAO.readAll().toString();
    }

}
