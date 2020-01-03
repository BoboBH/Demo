package org.CloudConsumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bobo.cloudmodule.client.UserFallback;

@Configuration
public class FallbackConfig {
	@Bean
    public UserFallback UserFallback() {
        return new UserFallback();
    }
}
