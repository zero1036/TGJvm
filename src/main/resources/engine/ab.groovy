def fnSimple() {
    return "Hello world";
}

def fnFor() {
    for (int i = 0; i < 100000; i++) {
    }
}

def fnAdd() {
    int r = 0;
    for (int i = 1; i <= 100000; i++) {
        r += i;
    }
    return r;
}

def fnArray() {
    def r = [];
    for (int i = 1; i <= 100000; i++) {
        r.add(i - 1, i)
    }
    return r;
}