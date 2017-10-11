package iu7.model

class Rule {
    def id
    def precondition
    def consequence

    Rule(def json_data) {
        this.id = json_data.id
        this.precondition = json_data.precondition
        this.consequence = json_data.consequence
    }

    def getId() {
        return id
    }

    void setId(id) {
        this.id = id
    }

    def getPrecondition() {
        return precondition
    }

    void setPrecondition(precondition) {
        this.precondition = precondition
    }

    def getConsequence() {
        return consequence
    }

    void setConsequence(consequence) {
        this.consequence = consequence
    }


    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", precondition=" + precondition +
                ", consequence=" + consequence +
                '}';
    }
}
