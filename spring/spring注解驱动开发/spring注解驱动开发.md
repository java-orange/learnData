# Spring 注解驱动开发



## 注册Bean的几种方式

- @Configuration
  - 标记为配置类，在其中进行自定义配置，同时将该配置类注入进容器
- @ComponentScan
  - 组件扫描，可自定义规则

- @Bean

  - 直接标注即可注入容器

- @Import

  - 对于不是自己写的类，引用的依赖，可以使用这种方式

- @ImportSelector

  - 选择导入，实现ImportSelector接口，直接返回String[] 即可注入

- @ImportBeanDefination

  - 导入Bean定义，直接注册至Bean容器

- 实现FactoryBean, 直接注入FactoryBean, 则注入getObject 的对象

  

## Bean的声明周期

- 通过@Bean直接指明初始化，销毁方法
  - @Bean(initMethod="", destoryMethod="")

- 通过让Bean实现InitializingBean(初始化方法)，DisposableBean（销毁方法）

- JSP250规范，@PostConstruct, @Destory
- BeanPostProcessor: bean的后置处理器，初始化前后工作，所有Bean都会用到
  - postProcessBeforeInitializtion: 初始化之前的工作（初始化方法执行之前执行）
  - postProcessAfterInitializtion: 初始化之后的工作（初始化方法执行之后执行）



## BeanPostProcessor工作原理

**AbstractAutowireCapableBeanFactory**

```java
populateBean(beanName, mbd, instanceWrapper);	// 给Bean赋值
{
    wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);	//前置处理器
    invokeInitMethods(beanName, wrappedBean, mbd);										// 初始化方法
    wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);	// 后置处理器
}
```



### Spring底层，大量用到了BeanPostProcess 

https://liayun.blog.csdn.net/article/details/110474598