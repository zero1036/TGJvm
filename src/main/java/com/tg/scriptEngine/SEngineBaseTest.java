package com.tg.scriptEngine;


import com.google.gson.Gson;
//import groovy.lang.Binding;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by linzc on 2017/5/27.
 */
public class SEngineBaseTest {
    private static Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        ScriptEngine engineJs = generateEngine("js", "/engine/jstest.js");
        runScript(engineJs, "js", "fnSimple", false);
        runScript(engineJs, "js", "fnFor", false);
        runScript(engineJs, "js", "fnAdd", false);
        runScript(engineJs, "js", "fnArray", false);

        ScriptEngine engineGroovy = generateEngine("groovy", "/engine/ab.groovy");
        runScript(engineGroovy, "groovy", "fnSimple", false);
        runScript(engineGroovy, "groovy", "fnFor", false);
        runScript(engineGroovy, "groovy", "fnAdd", false);
        runScript(engineGroovy, "groovy", "fnArray", false);
    }


    public static ScriptEngine generateEngine(String scriptName, String path) throws IOException {
        FileReader scriptReader = null;
        try {
            String file = SEngineBaseTest.class.getResource(path).getPath();
            scriptReader = new FileReader(new File(file));

            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName(scriptName);
            engine.eval(scriptReader);

            return engine;
        } catch (ScriptException | FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            scriptReader.close();
        }
        return null;
    }

    public static void runScript(ScriptEngine engine, String scriptName, String functionName, Boolean logger) {
        try {
            long startTime = System.currentTimeMillis();
            Object result = ((Invocable) engine).invokeFunction(functionName, null);
            System.out.println(String.format("%s %s action time: %s ms", scriptName, functionName, (System.currentTimeMillis() - startTime)));

            if (logger) {
                printResult(result);
            }
        } catch (NoSuchMethodException | ScriptException ex) {
            ex.printStackTrace();
        }
    }

    public static void runScriptGo(ScriptEngine engine, String functionName, Boolean logger, Object... args) {
        try {
            Object result = ((Invocable) engine).invokeFunction(functionName, args);

            if (logger) {
                SEngineBaseTest.printResult(result);
            }
        } catch (NoSuchMethodException | ScriptException ex) {
            ex.printStackTrace();
        }
    }

    public static void printResult(Object object) {
        if (object == null) {
            System.out.println("null");
        } else if (object instanceof String) {
            System.out.println(object.toString());
        } else if (object instanceof ScriptObjectMirror) {
            ScriptObjectMirror mirror = ((ScriptObjectMirror) object);
            Object[] target = mirror.entrySet().toArray();
            System.out.println(gson.toJson(target));
        } else {
            System.out.println(object.toString());
        }
    }

    private static void runGroovy(ScriptEngine engine, String script) {
        try {
            Bindings bindings = engine.createBindings();
            bindings.put("language", "Groovy");
            engine.eval(script, bindings);
            long startTime = System.currentTimeMillis();
            Object object = ((Invocable) engine).invokeFunction("hello", null);
//            printResult(object);
            System.out.println("javascript for 100000 time:" + (System.currentTimeMillis() - startTime) + "ms");
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (ScriptException exception) {
            exception.printStackTrace();
        }
    }
}
