package CouldZuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "------------------Start Zuul----------------------" );
        SpringApplication.run(App.class, args);
        System.out.println( "------------------End Zuul----------------------" );
        
    }
}
