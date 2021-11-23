package variable

//def name = 'a single \'a\'string'
//println name.class

def thupleName = '''\
line one
line two
line three
'''
//println thupleName

def doubleName = "this a common String"
//println doubleName.class

def name = "Qndroid"
def sayHello = "Hello: ${name}"
//println sayHello
//println sayHello.class

def sum = "the sum of 2 and 3 equals ${2 + 3}" //可扩展做任意的表达式
//println sum
def result = echo(sum)
//println result.class

String echo(String message) {
    return message
}
/* ==================字符串的方法=================== */
def str = "groovy Hello"
//println str.center(8)
//println str.padLeft(8, 'a')
def str2 = 'Hello'
//println str > str2
//println str[0]
//println str[0..1]
//println str - str2

//println str.reverse()
//println str.capitalize()
//println str.










