package objectorention

/**
 * 直接可使用任意参数的构造方法
 *
 * 无论是直接使用属性还是调用get/set,最终都由get/set 执行
 */
//def person = new Person(name: 'Qndroid', age: 26)
//println person.cry()

ExpandoMetaClass.enableGlobally()
//为类动态的添加一个属性
Person.metaClass.sex = 'male'
def person = new Person(name: 'Qndroid', age: 26)
println person.sex
person.sex = 'female'
println "the new sex is:" + person.sex
//为类动态的添加方法
Person.metaClass.sexUpperCase = { -> sex.toUpperCase() }
def person2 = new Person(name: 'Qndroid', age: 26)
println person2.sexUpperCase()
//为类动态的添加静态方法
Person.metaClass.static.createPerson = {
    String name, int age -> new Person(name: name, age: age)
}
def person3 = Person.createPerson('renzhiqiang', 26)
println person3.name + " and " + person3.age
