package pt.altran
class Parser {
    def dom

    static String convertNodeName(node) {
        if (node.name() == 'InitialPseudoState') {
            return '(*)'
        }
        if (node.name() == 'FinalState2') {
            return '(*)'
        }

        return node.@Name.text().replace('\n', '\\n').replace('\'', '\\\'')
    }

    void secondTest() {
        def transitions = dom.Models.ModelRelationshipContainer.ModelChildren.ModelRelationshipContainer.ModelChildren.Transition2
        def nodes = dom.Models.'*'.findAll {
            ['InitialPseudoState', 'NOTE', 'State2', 'FinalState2'].contains(it.name())
        }
        transitions.each { transition ->
            def nodeFrom = nodes.find { it.@Id.text() == transition.@From.text() }
            def nodeTo = nodes.find { it.@Id.text() == transition.@To.text() }
            println "\"${convertNodeName(nodeFrom)}\" -> \"${convertNodeName(nodeTo)}\""
        }
    }

    static void main(String[] args) {
        Parser parser = new Parser()
        parser.dom = new XmlSlurper().parse(getClass().getResourceAsStream('/project.xml'))
        parser.secondTest()
    }
}
