package com.uwplp.uwplp;

import com.uwplp.components.models.ProductModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AuthorizationTests {
    @Test
    public void encoderTest() {
        ProductModel productModel = new ProductModel();
        productModel.setProduct_id(2L);
        productModel.setProduct_description("");
        productModel.setProduct_name("skirt");
        productModel.setVendor_id(1L);
        String command = String.format(
                "INSERT INTO %s(product_id, product_name, product_description, vendor_id)  values(%d, %s, %s, %d)",
                "NAME",
                productModel.getProduct_id(),
                productModel.getProduct_name(),
                productModel.getProduct_description(),
                productModel.getVendor_id()
        );
        System.out.println(command);
    }
}
