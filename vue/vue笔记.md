# 学习vue

## Ⅰ.初识vue

### 01-helloworld

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		
		<div id="app">
			<h2>{{message}}</h2>
			<h3>{{name}}</h3>
		</div>	
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			// vue 可以做到数据与样式相分离
			// let/const  变量, 常量
			const app = new Vue({
				el: '#app' ,	// 用于挂载要管理的元素
				data: {			// 定义数据
					message: '你好啊,李银河',
					name: 'code'
				}
			})
		</script>
	</body>
</html>

```

### 02-列表遍历展示

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<h3>{{message}}</h3>
			<ul>
				<li v-for="item in movies">{{item}}</li>	<!--对于列表的展示	-->
			</ul>
		</div>
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
					movies: ['星际穿越','大话西游','盗梦空间','海贼王']
				}
			})
		</script>
	</body>
</html>

```

### 03-vue案例，计数器

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<h2>当前计数: {{counter}}</h2>
			<!--<button v-on:click="counter++">+</button>
			<button v-on:click="counter--">-</button>-->
			
			<!--语法糖-->
			<!--<button @click="add">+</button>-->
			<button v-on:click="add">+</button>
			<button v-on:click="sub">-</button>
		</div>
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',				// 挂载元素
				data: {					// 定义变量
					counter: 0
				},
				methods: {				// 定义方法
					add: function() {
						console.log('add被执行');
						this.counter ++;	// 使用app内部的counter变量,
					},
					sub: function() {
						console.log('sub被执行');
						this.counter --;
					}
				}
			})
		</script>
	</body>
</html>

```

## Ⅱ.插值操作

### 01-mustache语法

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			// mustache语法的插入
			<h3>{{message}}, 李银河</h3>							
			<h3>{{lastname + ' ' + firstname}}</h3>
			<h3>{{lastname}}  {{firstname}}</h3>
			<h3>{{counter * 2}}</h3>
			
		</div>
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
					lastname: 'code',
					firstname: 'why',
					counter: 100
				}
			})
			
		</script>
	</body>
</html>

```

### 02-v-once指令的使用

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<h3>{{message}}</h3>
			<h3 v-once>{{message}}</h3>				 <!--只作用一次,该指令表示元素和组件只渲染一次,不会随着数据的改变而改变-->
		</div>
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
				}
			})
			
		</script>
	</body>
</html>

```

### 03-v-html指令的使用

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<h3>{{message}}</h3>
			<h3>{{url}}</h3>
			<h3 v-html="url"></h3>			<!--以html形式展示-->
		</div>
		
	
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
					url: '<a href="http://www.baidu.com">百度一下</a>'
				}
			})
			
		</script>
	</body>
</html>

```

### 04-v-pre指令的使用

```vue 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app" v-cloak>    	<!--v-cloak , 可避免浏览器在网络不顺时,展示出原生的{{message}} 字样-->
			
			<h3 v-pre>{{message}}</h3>			<!--可以使所有效果失效,写的什么便原封不动的展示什么-->
			
		</div>
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
				}
			})
			
		</script>
	</body>
</html>

```

## Ⅲ.动态绑定属性

### 01-v-bind指令的基本使用

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<img v-bind:src="imgUrl" alt="" />
			<a v-bind:href="href">购买链接</a>
			
			<!--语法糖--> 
			<!--v-bind可直接省略为:-->
			<img :src="imgUrl" alt="" />
			<a :href="href">购买链接</a>
			
		</div>
		
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
					imgUrl: 'https://img20.360buyimg.com/jdcms/s300x300_jfs/t1/140242/6/4641/173561/5f2b620eE85cfc2f8/4bd85630ea316bab.jpg.webp',
					href: 'https://item.jd.com/47284860164.html'
					
				}
			})
			
		</script>
	</body>
</html>

```

### 02-v-bind动态绑定class

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
		<style>
			.active {
				color: red
			}
		</style>
	</head>	
	<body>
		<div id="app">	
			
			<!--动态绑定class属性, 使用对象方式,其中放入key-value 形式, 使用true, false来控制--> 
			<h3 :class="{active: isActive, line: isLine}">{{message}}</h3>
			
			<!--定义一个方法,用以存放class, 使用方法时要记得增加this关揵字-->
			<h3 :class="getClasses()">{{message}}</h3>
			<button v-on:click="btnClick">变色</button>
			
		</div>
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
					isActive: true,
					isLine: true
				},
				methods: {
					btnClick: function() {
						this.isActive = !this.isActive
					},
					getClasses: function() {
						return {active: this.isActive, line: this.isLine}
					}
				}
			})
			
		</script>
	</body>
</html>

```

## Ⅳ.计算属性

### 01-计算属性的基本使用

```vue 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<h3>{{firstName + ' ' + lastName}}</h3>
			<h3>{{firstName}} {{lastName}}</h3>
			
			<h3>{{getFullName()}}</h3>
			
			<h3>{{fullName}}</h3>
			
			<h2>{{full}}</h2>
		</div>
		
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					firstName: '荆',
					lastName: '晓华'
				},
			  computed: {				// 计算属性, 
					fullName: function () {
						return this.firstName + ' ' + this.lastName
					},
					full() {
						return this.firstName + ' ' + this.lastName
					}
				},
				methods: {
					getFullName() {		
						return this.firstName + ' ' + this.lastName
					}
				}
			})
			
		</script>
	</body>
</html>

```

### 02-计算属性的复杂使用

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<h3>总价格: {{totalPrice}}</h3>
		</div>
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					books: [
						{id: 001, name: 'java', price: 58.4},
						{id: 002, name: 'c', price: 123.5},
						{id: 003, name: 'c++', price: 58.5},
						{id: 004, name: 'python', price: 123.5},
					]
				},
				computed: {
					totalPrice: function () {
						let result = 0;
						for (let i = 0; i < this.books.length; i++) {
							result += this.books[i].price
						}
						return result;
					}
				}
			})
			
		</script>
	</body>
</html>

```

## Ⅴ.事件绑定

### 01-v-on的基本使用

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<h3>{{counter}}</h3>
			<!--<button v-on:click="counter++">+</button>
			<button v-on:click="counter--">-</button>-->
			
			<!-- @ 表示v-on 的语法糖-->
			
			<button @click="increment">+</button>
			<button @click="decrement">-</button>
			
		</div>
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					counter: 0
				},
				methods: {
					increment() {
						this.counter ++;
					},
					decrement() {
						this.counter --;
					}
				}
			})
			
		</script>
	</body>
</html>

```

### 02-v-on的参数传递

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">

			<button @click="btnClick1">按钮1</button>					<!--没有参数, 省略方法后括号-->
			<button @click="btnClick2(123)">按钮2.1</button>		<!--有参数,传参数,则是参数, -->
			<button @click="btnClick2('peter')">按钮2.1.1</button>		<!--若参数为peter等命名,则在data中寻找,若想使用本身含义,需加双引号 -->
			<button @click="btnClick2()">按钮2.2</button>			<!--有参数,不传参数,空括号,则传递undefined -->
			<button @click="btnClick2">按钮2.3</button>				<!--有参数,不传参数,省略括号,则将浏览器的事件对象传输-->
			<button @click="btnClick3(123,$event)">按钮3</button>		<!--有参数,并且需要浏览器事件对象,则使用$event用以传输-->
			
		</div>
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
				},
				methods: {
					btnClick1() {
						console.log("你好")
					},
					btnClick2(abc) {
						console.log("-------",abc)
					},
					btnClick3(abc,event){
						console.log("++++++",abc,event)
					}
				}
			})
			
		</script>
	</body>
</html>

```

### 03-v-on的修饰符

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		
		<div id="app" @click="divClick">
			aaa
			<!--.stop 阻止冒泡事件的发生, 即点击按钮,不会触发divClick ,-->
			<button @click.stop="btnClick">按钮</button>
			<br />
			
			<!--.prevent 阻止默认事件的发生, 即点击按钮, 触发绑定事件, 阻止默认事件的发生-->
			<form action="baidu">
				<input type="submit" value="提交" 	@click.prevent="submitClick" />
			</form>
			<br />
			
			<!--.enter 监听键盘回车键帽的抬起, 属于键盘的简称alias -->
			<input type="text" @keyup.enter="keyUp" />
			<input type="text" @keyup.a="keyUpA" />
			
			
		</div>
		
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
				},
				methods: {
					btnClick() {
						console.log("btnClick")
					},
					divClick() {
						console.log("divClick")
					},
					submitClick() {
						console.log("submitClick")
					},
					keyUp() {
						console.log("keyUp")
					},
					keyUpA() {
						console.log("keyUpA")
					}
				}
			})
			
		</script>
	</body>
</html>

```

## Ⅵ.条件判断

### 01-v-if的使用

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			
			<!--并不推荐使用-->
			<h3 v-if="score >= 90">优秀</h3>
			<h3 v-else-if="score >= 80">良好</h3>
			<h3 v-else-if="score >= 60">及格</h3>
			<h3 v-else>不及格</h3>
			
			<h2>{{result}}</h2>
			
		</div>
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					score: 99,
				},
				computed: {
//				逻辑判断,推荐使用 computed 计算属性 
					result() {
						let showMessage = "不及格"
						if (this.score >= 90){
							showMessage = "优秀"
						}else if (this.score >= 80){
							showMessage = "良好"
						}else if (this.score >= 60){
							showMessage = "及格"
						}
						return showMessage;
					}
				}
			})
			
		</script>
	</body>
</html>

```

### 02-条件渲染案例

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			
<!--vue内部存在虚拟dom的应用, 此案例中, 虽替换不同的lable + input, 但内部dom并无改变 , 所以用户输入的内容不会清空,还是相同的dom-->
			<!--可以使用key关揵字来确定不通dom, 让vue重新进行渲染-->
			
			<span v-if="isUser">
				<label for="username">用户名登录</label>
				<input id="username" type="text" placeholder="请使用用户名登录" key="username"/>
			</span>
			<span v-else>
				<label for="email">邮箱登录</label>
				<input id="email" type="text" placeholder="请使用邮箱登录" key="email" />
			</span>
			<button @click="isUser = !isUser">切换登录方式</button>
		</div>
		
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					isUser: true,
				}
			})
			
		</script>
	</body>
</html>
  	
```

### 03-v-show的使用

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			
			<!--v-if 使用的方式是删除dom, 并不加载渲染,-->
			<!--v-show 使用的方式是,将元素的 display 属性更改为 none, 不显示, 但存在dom-->
			
			<h3 v-if="isShow">{{message}}</h3>
			<h3 v-show="isShow">{{message}}</h3>
		</div>
		
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
					isShow: true
				}
			})
			
		</script>
	</body>
</html>

```

## Ⅶ.循环遍历

### 01-v-for遍历数组

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<h3>{{message}}</h3>
			
			<!--普通遍历, 不使用索引值-->
			<ul>
				<li v-for="movie in movies">{{movie.name}}</li>
			</ul>
			
			<!--遍历循环, 使用索引值-->
			<ul>
				<li v-for="(movie, index) in movies">{{index+1}}.{{movie.name}}</li>
			</ul>
			
			
			<!--遍历对象, 只显示value-->
			<ul>
				<li v-for="item in person">{{item}}</li>
			</ul>
			
			<!--遍历对象, 显示key - value-->
			<ul>
				<li v-for="(value, key) in person ">{{key}}-{{value}}</li>
			</ul>
			
			<!--遍历对象, 获取 index-key-value-->
			<ul>
				<li v-for="(value, key, index) in person">{{index}}-{{key}}-{{value}}</li>
			</ul>
			
			
		</div>
		
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
					movies: [
						{id: 001, name: "星际穿越"},
						{id: 002, name: "大话西游"},
						{id: 003, name: "盗梦空间"}
					],
					person: {
						name: "荆晓华",
						age: 18,
						height: 174
					}
				}
			})
			
		</script>
	</body>
</html>

```

### 02-v-for-性能调优

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<ul>
				<!--使用 :key="item"关揵字 可以性能调优, 优化使用diff算法, 删除插入性能更高-->
				<!--类似于: 数组 ->转变为 -> map 的性能-->
				
				<li v-for="item in letters" :key="item">{{item}}</li>
			</ul>
				
			<button @click="btnClick">按钮</button>
			
		</div>
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					letters: ['A','B','C','D','E']
				},
				methods: {
					// 响应式函数: push, pop, shift, unshitf, splice, sort, reverse  
					
					// 使用此些函数, 则浏览器直接渲染响应, 
					
					// shift : 删除数组中第一个元素
					
					// unshift: 在数组的第一个位置添加元素
					
					// splice: 粘合, splice(2,0,'F'): 表示为在第二个位置添加元素
					// 							splice(2,2):     表示为从第二个开始,往后删除2个元素, 即删除 'B', 'C'
					//							splice(2,3,'m','n','l'):  表示从第二个开始, 往后删除三个元素,并增添元素 'm','n','l'
					
					// 使用 this.letters[0] = 'bb' 这种直接操作数组元素, 不能相应式,内部元素更改,但是页面不会加载出来.
					
					// 修改元素可以使用: splice 方法
					
					btnClick() {
						// 注意: 使用 this.letters[0] = 'bb' 这种直接操作数组元素, 不能相应式,内部元素更改,但是页面不会加载出来.
//						this.letters[0] = 'bb'
//						 修改元素可以使用: splice 方法
//						this.letters.splice(0,1,'bb')
//						使用Vue内部的方法set(要修改的对象,修改的索引值,修改的内容)也可, 
//							参数1:要修改的对象, 参数2:修改的索引值, 参数3:修改的内容
						Vue.set(this.letters,0,'bb')
					}
				}
			})
			
		</script>
	</body>
</html>

```

### 03-v-for-小练习

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	<style> 
		.active {
			color: red;
		}
	</style>
	
	</head>
	<body>
		<div id="app">
			<ul>
				<li v-for="(movie, index) in movies" 
					  :class="{active: index === currentIndex}" 
					  @click="liClick(index)">
					{{index}}.{{movie}}
				</li>
			</ul>
		</div>
		
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					movies: ['海贼王','火影忍者','七龙珠','进击的巨人'],
					currentIndex: 0
				},
				methods: {
					liClick(index) {
						this.currentIndex = index;
					}
				}
			})
			
		</script>
	</body>
</html>

```

## Ⅷ.v-model的使用

### 01-v-model的基本使用

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			
			<!--v-model 实现表单与数据的双向绑定  -->
			
			<input type="text" v-model="message" />
			<h3>{{message}}</h3>
		</div>
		
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
				}
			})
			
		</script>
	</body>
</html>

```

### 02-v-model的原理

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<input type="text" :value="message" @input="changeValue" />
	<!--v-model 的原理: 使用v-bind绑定属性value,  使用v-on绑定事件,实现动态改变Message信息, 因为data中message数据实时刷新-->
			
			<!--简写:-->
			<!--简写:-->
			<input type="text" :value="message" @input="message = $event.target.value" />
			
			<h3>{{message}}</h3>
		</div>
		
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
				},
				methods: {
					changeValue(event) {
//						事件event , event.target.value 中为事件的值,
						this.message = event.target.value;
					}
				}
			})
			
		</script>
	</body>
</html>

```

### 03-v-model结合radio使用

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			
			<label for="male">
				<input type="radio" value="男" id="male" v-model="sex"/> 男			
			</label>
			<label for="female">
				<input type="radio" value="女" id="female" v-model="sex"/> 女
			</label>
			<h4>{{sex}}</h4>
			
			
			<!--动态值绑定:--> 
			<label v-for="item in orginalSex" :for="item">
				<input type="radio" :value="item" :id="item" v-model="sex" />{{item}}
			</label>
			
			<h3>{{message}}</h3>
		</div>
		
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
					sex: '男', 		// 若sex 设置值,则默认有选择, 使用v-model进行name的绑定,只能选择一个,传入value值进入data中的sex
					orginalSex: ['男','女']
					
				}
			})
			
		</script>
	</body>
</html>

```

### 04-v-model结合checkbox使用

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<!--checkbox 单选框的使用-->
			<label for="agree">
				<input type="checkbox" id="agree" v-model="isAgree" />是否同意本协议
			</label>
			<br />
			<button :disabled="!isAgree">下一步</button>
			
			<br />
			<!--checkbox 多选框的使用-->
			<label for="run">
				<input type="checkbox" value="run" id="run" v-model="hobbies" /> 跑步
			</label>
			<label for="dance">
				<input type="checkbox" value="dance" id="dance" v-model="hobbies" /> 舞蹈
			</label>
			<label for="basketball">
				<input type="checkbox" value="basketball" id="basketball" v-model="hobbies" /> 篮球
			</label>
			<h3>你的爱好是: {{hobbies}}</h3>
			
			
			<!--动态值绑定操作-->
			<label v-for="item in orginalHobbies" :for="item">
				<input type="checkbox" :value="item" :id="item" v-model="hobbies" />{{item}}
			</label>
			
		</div>
		
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
					isAgree: false,
					hobbies: [],
					orginalHobbies: ['篮球','足球','乒乓球','羽毛球','高尔夫球','台球','地球']
				}
			})
			
		</script>
	</body>
</html>

```

### 05-v-model结合select使用

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			
			<!--select 选择单个-->
			<select name="fruit" v-model="fruit">
				<option value="香蕉">香蕉</option>
				<option value="橘子">橘子</option>
				<option value="葡萄">葡萄</option>
			</select>
			<h3>你选择的水果是: {{fruit}}</h3>
			
			<!--select 选择多个-->
			<select name="fruits" v-model="fruits" multiple="multiple">
				<option value="香蕉">香蕉</option>
				<option value="橘子">橘子</option>
				<option value="葡萄">葡萄</option>
			</select>
			<h3>你选择的水果是: {{fruits}}</h3>
			
		</div>
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
					fruit: '香蕉',
					fruits: []
				}
			})
			
		</script>
	</body>
</html>

```

### 06-v-model修饰符的使用

```vue
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
		<div id="app">
			<!--修饰符1: .lazy  懒加载, 当失去焦点时,进行数据绑定,-->
			<input type="text" v-model.lazy="message" />
			<h3>{{message}}</h3>
			
			<!--修饰符2: .number 强制数字, 将用户输入的值直接为数组-->
			<!--若不是用.number, 即使type="number" 使用v-model默认绑定为string类型-->
			<input type="number" v-model.number="age" />
			<h3>{{age}}-{{typeof age}}</h3>
			
			<!--修饰符3: .trim 取出两边的空格-->
			<input type="text" v-model.trim="name" />
			<h3>{{name}}</h3>
			
		</div>
		
		<script type="text/javascript" src="../js/vue.js" ></script>
		<script>
			const app = new Vue({
				el: '#app',
				data: {
					message: '你好啊',
					age: 19,
					name: ''
				}
			})
			
		</script>
	</body>
</html>

```





