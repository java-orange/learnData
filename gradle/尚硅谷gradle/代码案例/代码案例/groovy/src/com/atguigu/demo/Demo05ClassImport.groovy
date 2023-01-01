package com.atguigu.demo

import groovy.xml.MarkupBuilder
import java.text.SimpleDateFormat

def xml = new MarkupBuilder()
assert xml != null


def sdf =new SimpleDateFormat("yyyy-MM-dd");
println(sdf.format(new Date()))