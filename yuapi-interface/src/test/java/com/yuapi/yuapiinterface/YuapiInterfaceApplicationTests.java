package com.yuapi.yuapiinterface;

import com.yuxin.yuapiclientsdk.client.YuApiClient;
import com.yuxin.yuapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class YuapiInterfaceApplicationTests {
    @Resource
    private YuApiClient yuApiClient;

    @Test
    void contextLoads() {
        String result = yuApiClient.getNameByGet("yuxin");
        User user = new User();
        user.setUsername("yuxin");
        String usernameByinterface = yuApiClient.getUserNameByinterface(user);
        System.out.println(result);
        System.out.println(usernameByinterface);
    }

}
