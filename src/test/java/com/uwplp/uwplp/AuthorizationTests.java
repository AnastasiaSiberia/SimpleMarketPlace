package com.uwplp.uwplp;

import com.uwplp.components.models.ProductModel;
import com.uwplp.services.CloudService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.util.Date;


@ExtendWith(SpringExtension.class)
public class AuthorizationTests {
    @Test
    public void encoderTest() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
        System.out.println("CURRDATE " + formatter.format(date));

    }
}
