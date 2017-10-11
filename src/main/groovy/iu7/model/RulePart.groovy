package iu7.model

class RulePart {
    def var_sname
    def func_id

    RulePart(def json_data) {
        this.var_sname = json_data.var_sname
        this.func_id = json_data.func_id
    }

    def getVar_sname() {
        return var_sname
    }

    void setVar_sname(var_sname) {
        this.var_sname = var_sname
    }

    def getFunc_id() {
        return func_id
    }

    void setFunc_id(func_id) {
        this.func_id = func_id
    }


    @Override
    public String toString() {
        return "RulePart{" +
                "var_sname=" + var_sname +
                ", func_id=" + func_id +
                '}';
    }
}
