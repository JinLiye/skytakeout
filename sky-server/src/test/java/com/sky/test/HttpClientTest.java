package com.sky.test;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@SpringBootTest
public class HttpClientTest {

    /**
     * 发送get请求
     */
    @Test
    public void testGET(){
       // 创建httpclient对象
        CloseableHttpClient aDefault = HttpClients.createDefault();
        // 创建请求对象
        HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");
        // 发送请求
        CloseableHttpResponse execute = null;
        try {
            execute = aDefault.execute(httpGet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 获取状态码
        int statusCode = execute.getStatusLine().getStatusCode();
        System.out.println("statusCode:"+statusCode);


        // 关闭资源
        try {
            execute.close();
            aDefault.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 发送post请求
     */
    @Test
    public void testpost() throws Exception{
        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建请求对象
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");
        StringEntity entity = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username","admin");
            jsonObject.put("password","123456");
            entity = new StringEntity(jsonObject.toString());
            entity.setContentType("application/json");
            entity.setContentEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        httpPost.setEntity(entity);

        // 发送请求
        CloseableHttpResponse execute = httpClient.execute(httpPost);
        int statusCode = execute.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        // 关闭资源
        httpClient.close();
        execute.close();
    }
}
