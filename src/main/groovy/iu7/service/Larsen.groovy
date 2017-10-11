package iu7.service

import groovy.json.JsonSlurper
import iu7.model.Function
import iu7.model.Rule
import iu7.model.Variable

class Larsen implements NDAlgorithm {
    ArrayList<Variable> vars
    ArrayList<Rule> rules
    ArrayList<Function> funcs

    Larsen() {
    }

    void readVars(def json_data) {
        vars = new ArrayList<>()
        def data = json_data.variables
        for (int i = 0; i < data.size(); i++) {
            vars.add(new Variable(data[i]))
        }
    }

    void readFunction(def json_data) {
        funcs = new ArrayList<>()
        def data = json_data.funcs
        for (int i = 0; i < data.size(); i++) {
            funcs.add(new Function(data[i]))
        }
    }

    void readRule(def json_data) {
        rules = new ArrayList<>()
        def data = json_data.rules
        for (int i = 0; i < data.size(); i++) {
            rules.add(new Rule(data[i]))
        }
    }

    double calculate(def h, def d, def result_sname) {
        def json_data = new JsonSlurper().parseText('[{ "sname" : "h", "value" : '+ h + '}, {"sname" : "d", "value" : ' + d + '}]')
        def fuzzy_values = getFuzzyForVar(json_data)
        //println fuzzy_values
        def mins = []
        for (int i = 0; i < fuzzy_values.size(); i++) {
            mins.add(findMin(fuzzy_values[i]))
        }
        //println mins
        def maxs = calcResultArray(mins,result_sname)
        //println maxs
        def value = calcResult(maxs,result_sname)
        return value

    }

    def getXFromJson(def json_data, def sname) {
        // получаем переменную
        for (int i = 0; i < json_data.size(); i++)
            if (json_data.get(i).sname == sname)
                return json_data.get(i).value
        return -1
    }

    def getFuzzyForVar(def json_data) {
        def fuzzy_values = []
        for (int i = 0; i < rules.size(); i++) {
            def pre = rules.get(i).precondition
            def fuzzy_values_i = []
            for (int j = 0; j < pre.size(); j++) {
                def sname = pre.get(j).var
                def func_id = pre.get(j).func
                def value_x = getXFromJson(json_data, sname)

                for (int k = 0; k < funcs.size(); k++)
                    if (funcs.get(k).id == func_id)
                        fuzzy_values_i.add(funcs.get(k).calculateValue(value_x))
            }
            fuzzy_values.add(fuzzy_values_i)
        }
        return fuzzy_values
    }

    def findMin(def fuzzy_array){
        def min = fuzzy_array[0]
        for (int i = 1; i < fuzzy_array.size(); i++)
            if (min > fuzzy_array[i])
                min = fuzzy_array[i]
        return min;
    }

    def calcResultArray(def mins, def result_sname) {
        def funcs_id = []
        for (int i = 0; funcs_id.size() == 0 && i < vars.size(); i++)
            if (vars.get(i).sname == result_sname)
                funcs_id = vars.get(i).funcs

        def results = []
        for (int i = 0; i < funcs_id.size(); i++) {
            def max = 0;
            for (int j = 0; j < rules.size(); j++)
                if (rules.get(j).consequence.var == result_sname && rules.get(j).consequence.func == funcs_id[i] && max < mins[i])
                    max = mins[i]
            results.add(max)
        }
        return results
    }

    def calcResult(def maxs, def result_sname) {
        def funcs_id = []
        for (int i = 0; funcs_id.size() == 0 && i < vars.size(); i++)
            if (vars.get(i).sname == result_sname)
                funcs_id = vars.get(i).funcs

        def x0 = 0
        def x1 = 0
        def h = 0.01
        for (int i = 0; i < funcs_id.size(); i++)
            for (int j = 0; j < funcs.size(); j++) {
                if (funcs.get(j).id == funcs_id.get(i)) {
                    if (funcs.get(j).a < x0)
                        x0 = funcs.get(j).a
                    if (funcs.get(j).d > x1)
                        x1 = funcs.get(j).d
                }
            }



        def x = x0
        def integral = 0
        while (x <= x1) {
            def maxy = 0
            for (int i = 0; i < funcs_id.size(); i++)
                for (int j = 0; j < funcs.size(); j++) {
                    if (funcs.get(j).id == funcs_id.get(i)) {
                        def y = funcs.get(j).calculateValueForLarsen(x, maxs[i])
                        if (y > maxy)
                            maxy = y
                    }
                }

            integral = integral + h * maxy
            x = x + h
        }
        return integral
    }


}
