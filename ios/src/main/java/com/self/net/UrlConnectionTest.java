package com.self.net;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author GTsung
 * @date 2021/10/13
 */
public class UrlConnectionTest {

    public static void main(String[] args) throws IOException {
        String urlName = "https://horstmann.com";
        // 创建URL
        URL url = new URL(urlName);
        // 创建连接
        URLConnection connection = url.openConnection();
        String username = "username";
        String password = "pwd";
        Base64.Encoder encoder = Base64.getEncoder();
        String input = username + ":" + password;
        connection.setRequestProperty("Authorization",
                "Basic "+encoder.encodeToString(input.getBytes(StandardCharsets.UTF_8)));
        connection.connect();
        // 获取头信息
        Map<String, List<String>> headers = connection.getHeaderFields();
        for(Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            for (String value : entry.getValue()) {
                System.out.println(key + ":" + value);
            }
        }

        System.out.println("-----------");
        System.out.println("getContentType:" + connection.getContentType());
        System.out.println("getContentLength:" + connection.getContentLength());
        System.out.println("getContentEncoding:" + connection.getContentEncoding());
        System.out.println("getDate:" + connection.getDate());
        System.out.println("getExpiration:" + connection.getExpiration());
        System.out.println("getLastModifed:" + connection.getLastModified());
        System.out.println("-----------");

        String encoding = connection.getContentEncoding();
        if (null == encoding) encoding = "UTF-8";
        Scanner in = new Scanner(connection.getInputStream(), encoding);
        for (int i = 0; in.hasNext()&& i <= 10 ; i++) {
            System.out.println(in.nextLine());
        }
    }
}
