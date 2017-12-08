package com.tg.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * Jackson实现弱类型字段命名驼峰式转换
 * Created by linzc on 2017/7/17.
 */
public class JacksonCamel {

    @Test
    public void test() throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        String json = "{\"CustomerId\":123,\"UserNumber\":\"123456\"}";
//        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
//        JacksonCamel.ABc object = mapper.readValue(json, JacksonCamel.ABc.class);
//        System.out.println("ddd");

        Gson gson = new Gson();
        String json = "{\"CustomerId\":123,\"UserNumber\":\"123456\"}";
        JsonObject returnData = new JsonParser().parse(json).getAsJsonObject();

//        returnData.get
//
//        Iterator<Map.Entry<String, JsonElement>> it =   returnData.entrySet().iterator();
//        while (it.hasNext()){
//            Map.Entry<String,JsonElement> entry= it.next();
//            entry.
//        }

        System.out.println("ddd");
    }

    public class ABc implements Serializable {

        private static final long serialVersionUID = 7409208852048633519L;

        @JsonProperty("CustomerId")
        private int CustomerId =0;
        private String UserNumber="";

        public int getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(int customerId) {
            CustomerId = customerId;
        }

        public String getUserNumber() {
            return UserNumber;
        }

        public void setUserNumber(String userNumber) {
            UserNumber = userNumber;
        }

        public ABc(String userNumber, int customerId) {
            super();
            UserNumber = userNumber;
            CustomerId = customerId;
        }

        public ABc() {
            super();
        }
    }
}
