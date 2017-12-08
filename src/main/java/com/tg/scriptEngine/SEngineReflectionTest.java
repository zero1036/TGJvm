package com.tg.scriptEngine;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by linzc on 2017/6/5.
 */
public class SEngineReflectionTest {

    public static void main(String[] args) throws IOException {
        ScriptEngine engine = SEngineBaseTest.generateEngine("js", "/engine/jstest.js");

        //传入文本
        String entity = "{\"name\":\"tg\",\"age\":29}";
        runScript(engine, "fnReflection", true, entity);

        //传入Java类型
        ArrayList<String> strings = new ArrayList<>();
        strings.add("abc");
        runScript(engine, "fnJavaType", true, strings);
    }

    public static void runScript(ScriptEngine engine, String functionName, Boolean logger, Object... args) {
        try {
            Object result = ((Invocable) engine).invokeFunction(functionName, args);

            if (logger) {
                SEngineBaseTest.printResult(result);
            }
        } catch (NoSuchMethodException | ScriptException ex) {
            ex.printStackTrace();
        }
    }
}

