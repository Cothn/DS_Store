package com.example.tradeplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


/**
 * @author Vsevolod Grinchick
 */
@SpringBootApplication(exclude = { //
        DataSourceAutoConfiguration.class, //
        HibernateJpaAutoConfiguration.class })
public class TradePlaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradePlaceApplication.class, args);
    }
}
