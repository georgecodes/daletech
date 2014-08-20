//noinspection GroovyAssignabilityCheck
beans {

    bean("helloService", class: "com.elevenware.daletech.lessons.language.beans.HelloServiceImpl")

    bean("messageFactory", class: "com.elevenware.daletech.lessons.language.beans.MessageFactoryImpl") {
        ref('helloService')
    }
}
