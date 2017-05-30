package com.tg.drools.simple;

import java.io.UnsupportedEncodingException;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * Created by linzc on 2017/5/25.
 */
public class MainTest {

    public static void main(String[] args) {
        //rule,rule2可以放在数据库中，有个唯一code和他们对于，代码要执行规则的时候，根据code从数据库获取出来就OK了，这样自己开发的规则管理系统那边对数据库里的规则进行维护就行了
        String rule = "package com.tg.drools.simple\r\n";
        rule += "import com.tg.drools.simple.Message;\r\n";
        rule += "rule \"rule1\"\r\n";
        rule += "\twhen\r\n";
        rule += "$message:Message( status == 1, myMessage : msg )";
        rule += "\tthen\r\n";
        rule += "\t\tSystem.out.println( 1+\":\"+myMessage );\r\n" +
                "\t\t$message.setStatus(4);\r\n" ;
//                "\t\tupdate($message);\r\n";
        rule += "end\r\n";


        String rule2 = "package com.tg.drools.simple\r\n";
        rule += "import com.tg.drools.simple.Message;\r\n";

        rule += "rule \"rule2\"\r\n";
        rule += "\twhen\r\n";
        rule += "Message( status == 2, myMessage : msg )";
        rule += "\tthen\r\n";
        rule += "\t\tSystem.out.println( 2+\":\"+myMessage );\r\n";
        rule += "end\r\n";


        StatefulKnowledgeSession kSession = null;
        try {


            KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
            //装入规则，可以装入多个
            kb.add(ResourceFactory.newByteArrayResource(rule.getBytes("utf-8")), ResourceType.DRL);
            kb.add(ResourceFactory.newByteArrayResource(rule2.getBytes("utf-8")), ResourceType.DRL);

            KnowledgeBuilderErrors errors = kb.getErrors();
            for (KnowledgeBuilderError error : errors) {
                System.out.println(error);
            }
            KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
            kBase.addKnowledgePackages(kb.getKnowledgePackages());

            kSession = kBase.newStatefulKnowledgeSession();


            Message message1 = new Message();
            message1.setStatus(1);
            message1.setMsg("hello world!");

            Message message2 = new Message();
            message2.setStatus(2);
            message2.setMsg("hi world!");

            kSession.insert(message1);
            kSession.insert(message2);
            kSession.fireAllRules();

            System.out.println("sdf");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (kSession != null)
                kSession.dispose();
        }

    }
}
