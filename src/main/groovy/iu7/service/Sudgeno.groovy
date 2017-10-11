package iu7.service

import groovy.json.JsonSlurper
import iu7.model.Function
import iu7.model.Rule
import iu7.model.Variable

class Sudgeno implements NDAlgorithm{
    ArrayList<Variable> vars
    ArrayList<Rule> rules
    ArrayList<Function> funcs

    Sudgeno() {
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
        def line_values = calcResultArray(json_data[0].value, json_data[1].value)
        //println maxs
        def value = calcResult(mins, line_values)
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

    def findMin(def fuzzy_array) {
        def min = fuzzy_array[0]
        for (int i = 1; i < fuzzy_array.size(); i++)
            if (min > fuzzy_array[i])
                min = fuzzy_array[i]
        return min;
    }

    def calcResultArray(def x, def y) {
        def line_values = []
        for (int i = 0; i < rules.size(); i++) {
            def func_id = rules.get(i).consequence.func
            def n_calculated = true
            for (int j = 0; n_calculated && j < funcs.size(); j++)
                if (funcs.get(j).id == func_id) {
                    line_values.add(funcs.get(i).calculateLine(x,y))
                    n_calculated = false
                }
        }
        return line_values
    }

    def calcResult(def mins, def line_values) {
        def w_sum = 0
        def weights = 0
        for (int i = 0; i < mins.size(); i++) {
            w_sum += mins[i]*line_values[i]
            weights += mins[i]
        }
        return (double)w_sum/weights
    }


}

