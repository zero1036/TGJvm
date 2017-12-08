/**
 * Created by linzc on 2017/5/27.
 */
function fnSimple() {
    var a = 'hello word';
    return a;
}

function fnFor() {
    for (var i = 1; i <= 100000; i++) {
    }
}

function fnAdd() {
    var r = 0;
    for (var i = 1; i <= 100000; i++) {
        r += i;
    }
    return r;
}

function fnArray() {
    var r = [];
    for (var i = 1; i <= 100000; i++) {
        r.push(i);
    }
    return r;
}

function fnReflection(evtStr) {
    var evt = JSON.parse(evtStr);
    return evt.age;
}

function fnJavaMethod() {
    var SEngineEvalJavaMethod = Java.type('com.tg.scriptEngine.SEngineEvalJavaMethod');
    var entity = new SEngineEvalJavaMethod();
    var arr = entity.testMethod("tgor");
    return arr;
}

function fnContext() {
    print(word);
    txt = "abc" + word;
}

var txt = "";
function fnVarial() {
    return txt;
}

function fnJavaType(arr) {
    for each(var entity in arr) {
        if (entity == "abc") {
            return entity;
        }
    }
    return "null";
}

function fnDebug(){
    var abc =123;
    print("bc" + testVar);
}

function fnValidateType() {
    var a ={"name":"tg","age":29};
    var ok = "bbb" in a;
    print("-----"+ok);
}

function fnValidateType2(obj) {
    var ok = obj.bbb == null; //通过验证属性值是否null，判断对应属性是否存在
    print("-----"+ok);
}

function fnJavaHashMap() {
    //java.util包无需通过java.type引入
    var map = new java.util.HashMap();
    map.put("name", "tg");
    map.put("age", 23);
    return map;
}
