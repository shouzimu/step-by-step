###  内存布局



![jvm-w70](https://modprobe.oss-cn-beijing.aliyuncs.com/github/sts/jvm_m.png)
(图)经典的JVM内存布局



#### 1. Heap 堆区

​	Heap是OOM故障的主要发生地，它存储了几乎所有的实例对象，堆由垃圾回收器自动回收，堆区由各

个子线程共享使用，通常情况下，它所占用的空间是所有内存中最大的。堆的内存即可以固定大小，也可以

动态调整可以通过参数 例如``-Xms256M -Xmx=1024M``设置，其中``-X``表示它是JVM运行参数，ms是memory

start的简称，ms是memory max的简称，分别表示最小堆容量和最大堆容量，通常情况下将2个参数设置为

一样大小，避免GC后动态调整堆大小给系统带来额外压力。

​	堆分成两大块，新生代和老年代。对象产生之初在新生代，步入暮年时进入老年代，但是老年带也接纳

新生代无法接容纳的超大对象。新生代=1个Eden+2个Survivor区，Eden和Survivor的比例默认为``8:1``，绝大

多数的对象在Eden生成，当Eden区域装满的时候，会触发Young Garbage Collection，即YGC。垃圾回收时，

在Eden区域实现清除策略，没有引用的直接回收，仍然存活的会被移动到Survivor区。Survivor分为``S0``和``S1``，

S0和S1同时只有一块正在使用，YGC的时候将对象复制到未使用的那块，然后清除Eden和正在使用的那块。每

个对象都有一个计数器，每次YGC的时候+1，可以通过参数``-XXMaxTenuriningThreshold``来设置，如果设置

为1，则将直接从Eden移动到老年代，默认值为15，即交换15次后晋升。




![jvm](https://modprobe.oss-cn-beijing.aliyuncs.com/github/sts/new_object.png)

(图)对象分配与简要GC流程图



如图，如果Survivor区域无法放下，或者超大对象的阀值超过上线，则尝试在老年代中分配，如果老年代也无法放

下，则触发Full Garbage Collection即FGC，如果还放不下，则抛出OOM。堆内出现这个异常的概率是所有内存

耗尽中最高。可以通过设置 ``-XX:+HeapDumpOnOutOfMemoryError``让JVM在遇到OOM时输出堆内的信息。



#### 2. MetaSpace 元空间

​	元空间在本地内存中分配，里面包括字符串常量池、类元信息、字段、静态属性、方法、常量等信息。





#### 3. JVM Stacks 虚拟机栈

​	栈（Stack）是一个先进后出的数据结构

##### （1）局部变量表



##### （2）操作栈



##### （3）动态链接



##### （4）方法返回地址

##### （5）操作栈



#### 4. Native Method Stacks 本地方法栈

#### 5. Program Counter Register 程序计数寄存器









