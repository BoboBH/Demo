package org.rabbitmqtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootConfiguration
@EnableAutoConfiguration 
@ComponentScan(basePackages={"org.rabbitmqtest"})
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "-------------RabbitMq Test Start--------------------" );
        SpringApplication.run(App.class, args);
        System.out.println( "-------------RabbitMq Test End--------------------" );
    }
}
