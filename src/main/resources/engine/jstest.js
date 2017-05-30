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