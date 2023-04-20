package org.example.dubbo.provider;

import org.example.dubbo.api.GreetingsService;

/**
 * @author heyc
 * @version 1.0
 * @date 2023/4/20 15:52
 */
public class GreetingsServiceImpl implements GreetingsService {

    @Override
    public String sayHi(String name) {
        return "hi, " + name;
    }
}
