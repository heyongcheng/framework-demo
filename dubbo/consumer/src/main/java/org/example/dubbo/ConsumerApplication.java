package org.example.dubbo;

import java.io.IOException;
import java.util.HashMap;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.example.dubbo.api.GreetingsService;

/**
 * @author heyc
 * @version 1.0
 * @date 2023/4/20 16:02
 */
public class ConsumerApplication {

    public static void main(String[] args) throws IOException {
        invokeService();
        System.in.read();
    }

    private static void invokeService() {
        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(registryConfig());
        reference.setInterface(GreetingsService.class);
        GreetingsService greetingsService = reference.get();
        String response = greetingsService.sayHi("dubbo");
        System.out.println(response);

    }

    private static RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("nacos");
        registryConfig.setAddress("192.168.44.10:8848");
        registryConfig.setParameters(new HashMap<String, String>(){{
            put("namespace", "a0344ebd-c339-4e8e-95ac-fbc097f08782");
        }});
        return registryConfig;
    }

}
