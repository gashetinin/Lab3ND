package iu7.model


class Variable {
    def name
    def sname
    def funcs

    Variable(def json_data) {
        this.name = json_data.name
        this.sname = json_data.sname
        this.funcs = json_data.funcs.clone()
    }

    def getName() {
        return name
    }

    void setName(name) {
        this.name = name
    }

    def getSname() {
        return sname
    }

    void setSname(sname) {
        this.sname = sname
    }

    def getFuncs() {
        return funcs
    }

    void setFuncs(funcs) {
        this.funcs = funcs
    }


    @Override
    public String toString() {
        return "Variable{" +
                "name=" + name +
                ", sname=" + sname +
                ", funcs=" + funcs +
                '}';
    }
}
