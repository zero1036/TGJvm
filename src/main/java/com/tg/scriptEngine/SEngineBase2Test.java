package com.tg.scriptEngine;

import com.google.gson.Gson;
import org.junit.Test;

import javax.script.ScriptEngine;

import java.io.IOException;

import static com.tg.scriptEngine.SEngineBaseTest.generateEngine;

/**
 * Created by linzc on 2017/7/19.
 */
public class SEngineBase2Test {

    private Gson gson = new Gson();

    @Test
    public void testType() throws IOException {
        ScriptEngine engine = SEngineBaseTest.generateEngine("js", "/engine/jstest.js");

        SEngineReflectionTest.runScript(engine, "fnValidateType", true);
    }

    @Test
    public void testType2() throws IOException {
        ScriptEngine engine = SEngineBaseTest.generateEngine("js", "/engine/jstest.js");

        Object object = gson.fromJson("{\"name\":333}", Object.class);

        SEngineReflectionTest.runScript(engine, "fnValidateType2", true, object);
    }
}
