package com.tg.scriptEngine;

import javax.script.*;
import java.io.IOException;

/**
 * Created by linzc on 2017/6/12.
 */
public class SEngineContextTest {
    public static void main(String[] args) throws IOException {
        ScriptEngine engine = SEngineBaseTest.generateEngine("js", "/engine/jstest.js");

//        testEngine(engine);

        testContext(engine);
    }


    private static void testEngine(ScriptEngine engine) {
        engine.put("word", "hello");
        SEngineBaseTest.runScript(engine, "js", "fnContext", true);

        engine.put("word", "hello world");
        SEngineBaseTest.runScript(engine, "js", "fnContext", true);
    }

    private static void testContext(ScriptEngine engine) {
        engine.put("word", "hello");
        SEngineBaseTest.runScript(engine, "js", "fnContext", true);

        SEngineBaseTest.runScript(engine, "js", "fnVarial", true);
    }

}
