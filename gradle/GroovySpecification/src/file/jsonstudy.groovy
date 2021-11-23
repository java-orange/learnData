package file

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import objectorention.Person


def list = [new Person(name: "john",age: 12),
            new Person(name: "Major", age: 43)]

def json =  JsonOutput.toJson(list)
println json
println JsonOutput.prettyPrint(json)



def reponse =
        getNetworkData(
                'http://localhost:9200/?pretty')

println reponse.version.number

def getNetworkData(String url) {
    //发送http请求
    def connection = new URL(url).openConnection()
    connection.setRequestMethod('GET')
    connection.connect()
    def response = connection.content.text
    //将json转化为实体对象
    def jsonSluper = new JsonSlurper()
    return jsonSluper.parseText(response)
}


