package com.tg.scriptEngine;

import javax.script.ScriptEngine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by linzc on 2017/6/6.
 */
public class SEngineJavaHashMap {

    public static void main(String[] args) throws IOException {
        ScriptEngine engine = SEngineBaseTest.generateEngine("js", "/engine/jstest.js");

        SEngineBaseTest.runScriptGo(engine, "fnJavaHashMap", true, null);
    }

    public void example() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "tg");
        map.put("age", 23);
    }
}
