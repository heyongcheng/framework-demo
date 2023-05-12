package org.example;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.example.bean.User;

/**
 * @author heyc
 * @version 1.0
 * @date 2023/5/12 17:20
 */
public class ContrastUtilTest {

    public static void main(String[] args) throws InterruptedException {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("张三");
        user1.setAge(32);
        user1.setCreateTime(new Date());
        user1.setUpdateTime(new Date());

        TimeUnit.SECONDS.sleep(1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("李四");
        user2.setAge(31);
        user2.setCreateTime(new Date());
        user2.setUpdateTime(new Date());

        String contrast = ContrastUtil.contrast(user1, user2);
        System.out.println(contrast);

        String contrast2 = ContrastUtil.contrast(user1, user1);
        System.out.println(contrast2);
    }

}
