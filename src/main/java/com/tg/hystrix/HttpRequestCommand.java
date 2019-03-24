package com.tg.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by linzc on 2018/4/4.
 */
public class HttpRequestCommand extends HystrixCommand<String> {

    private String url;
    CloseableHttpClient httpclient;

    public HttpRequestCommand(String url) {
//调用父类的构造器，设置命令组的key，默认用来作为线程池的key
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
//创建HttpClient客户端
        this.httpclient = HttpClients.createDefault();
        this.url = url;
    }

    protected String run() throws Exception {
        try {
//调用GET方法请求服务
            HttpGet httpget = new HttpGet(url);
//得到服务响应
            HttpResponse response = httpclient.execute(httpget);
//解析并返回命令执行结果
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected String getFallback() {
        System.out.println("fallback");
        return "fallback";
    }

    public static void main(String[] args) {
//请求正常的服务
        String normalUrl = "http://localhost:50578/FreeBuy/GetProductList";
        HttpRequestCommand command = new HttpRequestCommand(normalUrl);
        String result = command.execute();
        System.out.println("请求正常的服务，结果：" + result);
    }
}
