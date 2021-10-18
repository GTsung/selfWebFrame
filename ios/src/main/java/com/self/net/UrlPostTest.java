package com.self.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author GTsung
 * @date 2021/10/13
 */
public class UrlPostTest {

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.baidu.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "user");
        // 开启输出流，向web输出
        connection.setDoOutput(true);
        PrintWriter writer = new PrintWriter(connection.getOutputStream());
        writer.print("name=");
        writer.print(URLEncoder.encode("adams", String.valueOf(StandardCharsets.UTF_8)));
        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
    }
}
