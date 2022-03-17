package com.baizhi;

import com.baizhi.entity.User;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;
import java.util.List;

public class MongoTemplateTests extends SpringBootMongodbDemoApplicationTests {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoTemplateTests(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    //文档操作 添加
    @Test
    public void testSave() {
        User user = new User(9, "徐凤年", 2100.1, new Date());
        mongoTemplate.save(user);//save 方法在_id 存在时更新数据
        mongoTemplate.findAll(User.class).forEach(System.out::println);
        //mongoTemplate.insert(user);//insert 方法在_id 存在时 主键冲突
        //List<User> users = Arrays.asList(new User(5, "姜泥", 2800.1, new Date()), new User(6, "红薯", 2800.1, new Date()));
        //mongoTemplate.insert(users,User.class);//参数 1:批量数据 参数 2:放入哪个集合
        //mongoTemplate.insert(users,"users");
    }

    //文档操作 查询
    @Test
    public void testFind() {
        //1.查询所有
        mongoTemplate.findAll(User.class);
        mongoTemplate.findAll(User.class, "users");


        //2.基于 id 查询一个
        mongoTemplate.findById(1, User.class);

        //3.添加查询 参数 1: 查询条件   参数 2: 返回类型
        mongoTemplate.find(new Query(), User.class);

        //4.等值查询
        mongoTemplate.find(Query.query(Criteria.where("username").is("编程不良人")), User.class);

        //5.> < >= <= 查询
        mongoTemplate.find(Query.query(Criteria.where("salary").gte(2500.1)), User.class);


        //6.and 查询
        mongoTemplate.find(Query.query(Criteria.where("name").is("编程不良人").and("salary").is(2300.1)), User.class);

        //7.or 查询
        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("name").is("编程不良人"),
                Criteria.where("name").is("剑神李淳罡")
        );
        mongoTemplate.find(Query.query(criteria), User.class);


        //8. and  or
        mongoTemplate.find(Query.query(Criteria.where("salary").is(2800.1).orOperator(Criteria.where("name").is("徐凤年"))), User.class);

        //9.排序
        Query querySort = new Query();
        querySort.with(Sort.by(Sort.Order.asc("salary")));
        mongoTemplate.find(querySort, User.class);


        //10.分页查询
        Query querySortPage = new Query();
        querySortPage.with(Sort.by(Sort.Order.asc("salary")))
                .skip(4)
                .limit(2);
        mongoTemplate.find(querySortPage, User.class);

        //11.总条数
        long count = mongoTemplate.count(Query.query(Criteria.where("name").is("编程不良人")), User.class);
        System.out.println(count);

        //12.去重 distinct
        List<Double> doubleList = mongoTemplate.findDistinct(new Query(), "salary", User.class, Double.class);
        doubleList.forEach(System.out::println);

        //13.使用 json 字符串方式查询
        Query query = new BasicQuery("{$or:[{name:'编程不良人'},{name:'徐凤年'}]}", "{name:0}");

        List<User> users = mongoTemplate.find(query, User.class);
        users.forEach(System.out::println);
    }

    //文档操作 更新
    @Test
    public void testUpdate() {

        Update update = new Update();
        update.set("salary", 4000.1);
        //更新符合条件第一条数据
        //mongoTemplate.updateFirst(Query.query(Criteria.where("salary").is(2800.1)), update, User.class);
        //多条更新
        //mongoTemplate.updateMulti(Query.query(Criteria.where("salary").is(2800.1)), update, User.class);
        //没有符合条件数据插入数据
        UpdateResult updateResult = mongoTemplate.upsert(Query.query(Criteria.where("salary").is(2800.1)), update, User.class);
        System.out.println(updateResult.getModifiedCount());
        System.out.println(updateResult.getMatchedCount());
        System.out.println(updateResult.getUpsertedId());

    }

    //文档操作 删除
    @Test
    public void testRemove() {
        //删除所有
        mongoTemplate.remove(new Query(), User.class);
        //条件删除
        //mongoTemplate.remove(Query.query(Criteria.where("name").is("张小五")), User.class);
    }

    //1.创建集合
    @Test
    public void testCreateCollection() {
        boolean exists = mongoTemplate.collectionExists("products");
        if (!exists) {
            mongoTemplate.createCollection("products");
        }
    }

    //2.删除集合操作
    @Test
    public void testDropCollection() {
        mongoTemplate.dropCollection("products");
    }
}
