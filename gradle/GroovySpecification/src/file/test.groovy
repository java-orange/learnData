package file


import objectorention.Person


//def person = new Person(name: 'Qndroid', age: 26)
//saveObject(person, '../../person.bin')

//读取文件中的对象
//def readPerson = (Person) readObject('../../person.bin')
//println "the name is ${readPerson.name} and the age is ${readPerson.age}"
/**
 * 将obj写入文件
 * @param obj
 */
def saveObject(Object obj, String path) {
    try {
        //先创建目的文件
        def desFile = new File(path)
        if (!desFile.exists()) {
            desFile.createNewFile()
        }

        desFile.withObjectOutputStream { ObjectOutputStream out ->
            out.writeObject(obj)
        }
    } catch (Exception e) {
        e.printStackTrace()
        return false
    }
    return true
}

def readObject(String path) {
    def obj = null
    try {
        def file = new File(path)
        if (file == null || !file.exists()) return null
        //文件不为空，则开始读取数据
        file.withObjectInputStream { input ->
            obj = input.readObject()
        }
    } catch (Exception e) {
        println e.getMessage()
    }
    return obj
}

/**
 * 字节读写
 */
def byteList = readByte('../../person.bin')
println byteList.toList()

def readByte(String path) {
    def file = new File(path)
    byte[] bytes = new byte[file.length()]
    file.withDataInputStream { input ->
        input.readFully(bytes)
    }
    return bytes
}

/**
 * 文件的核心操作呢主要就是这些，下面给大家继续留一个作用，
 * 前面我们讲完xml的生成的时候给
 * 大家留的作业是将服务器返回的json最终转化成xml格式的数据，
 * 本小节的作业就是在上小节作业
 * 的基础上将生成的xml格式数据存储到本地文件中，
 * 使它真正成为一个xml文件
 */