package com.tg.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 泛型对象反序列化
 * Created by linzc on 2018/4/9.
 */
public class TypeReferenceTest {

    @Test
    public void test() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Customer customer = new Customer();
        customer.setName("tgor");
        customer.setAge(39);
        Message<Customer> message = new Message<>();
        message.setId(1);
        message.setData(customer);

        String result = mapper.writeValueAsString(message);

        System.out.println(result);

        //结果正常
        Message<Customer> obj = mapper.readValue(result, new TypeReference<Message<Customer>>() {
        });
        System.out.println(obj);

        //结果正常
        Message<Customer> obj2 = tomap2(result, new TypeReference<Message<Customer>>() {
        });
        System.out.println(obj2);

        //报错：java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to com.tg.jackson.Message
        Message<Customer> obj3 = tomap3(result);
        System.out.println(obj3);


    }

    private <T> T tomap3(String result) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(result, new TypeReference<T>() {
        });
    }

    private <T> T tomap2(String result, TypeReference<T> typeReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(result, typeReference);
    }

}
