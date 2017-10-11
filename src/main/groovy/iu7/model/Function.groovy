package iu7.model

class Function {
    def id
    def a
    def b
    def c
    def d
    def comment

    Function(def json_data) {
        this.id = json_data.id
        this.a = json_data.a
        this.b = json_data.b
        this.c = json_data.c
        this.d = json_data.d
        this.comment = json_data.comment
    }

    def getId() {
        return id
    }

    void setId(id) {
        this.id = id
    }

    def getA() {
        return a
    }

    void setA(a) {
        this.a = a
    }

    def getB() {
        return b
    }

    void setB(b) {
        this.b = b
    }

    def getC() {
        return c
    }

    void setC(c) {
        this.c = c
    }

    def getD() {
        return d
    }

    void setD(d) {
        this.d = d
    }

    def getComment() {
        return comment
    }

    void setComment(comment) {
        this.comment = comment
    }

    double calculateValue(double x) {
        if (x <= a || x >= d)
            return 0
        if (x >= b && x <= c)
            return 1
        if (x > a && x < b)
            return ((double)(x-a)) / ((double)(b-a))
        if (x > c && x < d)
            return ((double)(d-x)) / ((double)(d-c))
    }

    double calculateValueWithMax(double x, double max) {
        def y = calculateValue(x)
        return y > max ? max : y
    }

    @Override
    public String toString() {
        return "Function{" +
                "id=" + id +
                ", a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", comment=" + comment +
                '}';
    }
}
