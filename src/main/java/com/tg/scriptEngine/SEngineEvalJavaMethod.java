package com.tg.scriptEngine;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linzc on 2017/6/6.
 */
public class SEngineEvalJavaMethod {

    public static void main(String[] args) throws IOException {
        ScriptEngine engine = SEngineBaseTest.generateEngine("js", "/engine/jstest.js");

//        SEngineBaseTest.runScriptGo(engine, "fnJavaMethod", true, null);

        engine.put("testVar", "abc");
        SEngineBaseTest.runScriptGo(engine, "fnDebug", true, null);
    }

    public List<String> testMethod(String name) {
        ArrayList<String> list = new ArrayList<>();
        list.add("abc");
        list.add(name);
        return list;
    }
}
