package iu7.model

import groovy.json.JsonSlurper
import iu7.service.Mamdani

class Main {

    Mamdani mamdani = new Mamdani()

    void readDB(String filenameV, String filenameR, String filenameF) {
        def inputFileV = new File(filenameV)
        def inputFileR = new File(filenameR)
        def inputFileF = new File(filenameF)

        mamdani.readVars(new JsonSlurper().parseText(inputFileV.text))
        mamdani.readRule(new JsonSlurper().parseText(inputFileR.text))
        mamdani.readFunction(new JsonSlurper().parseText(inputFileF.text))
    }

    void calculateMamdani(String filename) {
        def inputFile = new File(filename)
        def value = mamdani.calculate(new JsonSlurper().parseText(inputFile.text),"m")
        println value

    }
}
