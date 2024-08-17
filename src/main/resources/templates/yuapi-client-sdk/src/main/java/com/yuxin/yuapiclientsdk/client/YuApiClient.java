package com.yuxin.yuapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yuxin.yuapiclientsdk.model.User;
import com.yuxin.yuapiclientsdk.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用第三方接口地客户端
 *
 * @author yuxin
 */

public class YuApiClient {
    private String accessKey;
    private String secretKey;

    public YuApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        String result = HttpUtil.get("http://localhost:8123/api/name/",paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByinterface(String name) {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        String result = HttpUtil.get("http://localhost:8123/api/name/",paramMap);
        System.out.println(result);
        return result;
    }

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("accessKey", accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtils.genSign(body, secretKey));
        return hashMap;
    }

    public String getUserNameByinterface(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.interface("http://localhost:8123/api/name/user/")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }
}
