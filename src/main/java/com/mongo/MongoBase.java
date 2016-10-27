package com.mongo;

import com.mongo.bean.Setting;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import com.google.gson.Gson;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linzc on 2016/10/24.
 */
public class MongoBase {

    static Gson gson = new Gson();
    static MongoClient client;

    static {
        try {
            // client = new MongoClient("192.168.1.72", 27017);
            System.out.println("连接服务器测试.................");

            ServerAddress serverAddress = new ServerAddress("192.168.1.72", 27017);
            List<ServerAddress> seeds = new ArrayList<ServerAddress>();
            seeds.add(serverAddress);
            MongoCredential credentials = MongoCredential.createMongoCRCredential("ppmoney", "test",
                    "Da3LzMogT88DjDleiE8".toCharArray());
            List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
            credentialsList.add(credentials);
            client = new MongoClient(seeds, credentialsList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Datastore getDatastore() {
        Morphia morphia = new Morphia();

        Datastore datastore = morphia.createDatastore(client, "test");
        return datastore;
    }
}
