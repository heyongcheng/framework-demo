package org.example.dubbo;

import java.io.IOException;
import java.util.HashMap;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.example.dubbo.api.GreetingsService;
import org.example.dubbo.provider.GreetingsServiceImpl;

/**
 * @author heyc
 * @version 1.0
 * @date 2023/4/20 15:49
 */
public class ProviderApplication {

    public static void main(String[] args) throws IOException {
        exportService();
        System.in.read();
    }

    private static void exportService() {
        ServiceConfig<GreetingsService> service = new ServiceConfig<>();
        service.setApplication(new ApplicationConfig("first-dubbo-provider"));
        service.setRegistry(registryConfig());
        service.setInterface(GreetingsService.class);
        service.setRef(new GreetingsServiceImpl());
        service.export();
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
