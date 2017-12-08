package com.tg.mongo.morphia;

import com.tg.mongo.bean.RuntimeEventObj;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.query.Query;

import java.util.Date;
import java.util.Iterator;

import static org.mongodb.morphia.aggregation.Group.id;
import static org.mongodb.morphia.aggregation.Group.sum;
import static org.mongodb.morphia.aggregation.Group.grouping;

/**
 * Created by linzc on 2017/7/14.
 */
public class MongoAggregate extends MongoBase {

    public class Target {
        private Integer _id;

        private Integer target;

        public Integer get_id() {
            return _id;
        }

        public void set_id(Integer _id) {
            this._id = _id;
        }

        public Integer getTarget() {
            return target;
        }

        public void setTarget(Integer target) {
            this.target = target;
        }
    }

    @Test
    public void test1() {
        Datastore datastore = getDatastore();

        Query<RuntimeEventObj> query = datastore.createQuery(RuntimeEventObj.class);
        AggregationPipeline pipeline = datastore.createAggregation(RuntimeEventObj.class);
        pipeline.match(query.filter("Evt =", "InvestEvent"))
                .group(id(grouping("Cid"), grouping("Evt")), grouping("total", sum("Data.Amount")));

        System.out.println(pipeline.toString());


        Iterator<Target> iterator = pipeline.aggregate(Target.class);
        System.out.println(iterator.hasNext());
        while (iterator.hasNext()) {
            Target ug = iterator.next();
            System.out.println(ug);
        }
    }

    @Test
    public void test() {
//        Datastore datastore = getDatastore();
//
//        ObjectId objectId=null;
//      int t=  objectId.getTimestamp();
//        Date date = new Date();
////        date.setTime();


        Date date1 = new Date(2017, 7, 13, 10, 0, 0);
        Date date2 = new Date(2017, 7, 14, 9, 0, 0);

        ObjectId objectId2 = new ObjectId(date2);
        ObjectId objectId1 = new ObjectId(date1);

        Assert.assertTrue(objectId2.compareTo(objectId1) == 0);


//        objectId1.


    }


}
