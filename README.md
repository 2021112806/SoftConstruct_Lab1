# MQ
## java SpringBoot实现简单的MQ

此消息中间件采用了发布/订阅模式进行设计与实现。实现了全广播式、选择广播式-点对点、选择广播式-发布订阅三种调度策略。并采用了strategy的设计模式，提供了一种灵活、可扩展和可维护的方式来处理不同的策略。通过解耦合和封装Broker，使系统具有更好的可读性、可维护性和可扩展性，并遵循了面向对象设计原则。  整个系统包含3个主要对象：Platform、User和接口Broker。  l Platform的消息汇总到Broker中  l User通过Broker获取消息  l 采用不同的调度策略，User、Platform会有不同的继承类，Broker会有不同实现类  Broker相当于一个消息的处理中心，将平台和用户解耦，负责接收到来的消息并分发它们到用户，由Broker决定采用怎样的调度策略分发消息。此外，“平台/用户”与“中间件”采用socket来通信，这样可以实现分布式通信。主要有以下两种调度策略：

- 全⼴播式(All  broadcasting) ：调度模块将事件广播到所有的模块，但只有感兴趣的模块才去取事件并触发自身的行为； 

<div align=center><img src=".\images\img.png"></div>

- 选择⼴播式(Selected  broadcasting) ：调度模块将事件送到那些已经注册了的模块中；

<div align=center><img src=".\images\img2.png"></div>

点对点模式：基于消息队列，某个Consumer向消息队列注册，以监听并处理被放置在队列⾥的消息。Producer可以连接到该队列并向其中发布事件。消息队列可以设置一定的容量，来存储这些消息，直到接收端已经向消息队列注册过的的Consumer连接到队列，取回这些消息并加以处理。**消息只能够被唯一的Consumer所消费，消费之后即从队列中删除。**在已经注册的Consumer退出之前，消息队列无法被其他Consumer使用，也无法被注册，因此能每次连接后队列中的消息只能被唯一一个Consumer消费。 

 <div align=center><img src=".\images\img3.jpg" style="zoom:120%"></div>

发布-订阅模式：Publisher向Topic发布消息，Subscriber向Topic订阅消息。一个事件可以被多个Subscriber消费，消息在发送给Subscriber之后，并不会马上从topic中删除，topic会在消息过期之后自动将其删除。

  <div align=center><img src=".\images\img4.jpg"></div>

设计类图如下：  

<div align=center><img src=".\images\img5.png"></div>  

***注解：***

- Platform类指代发布者
- User类指代用户
- Broker类指代经纪人（中间件)
- Configure类用于以“static变量形式”存储一些基本配置
- LoadProject和BrokerServer类用于以多线程形式开启中间件，以实现同时与多个订阅者、多个发布者进行通信
- 开启中间件服务时，用可以选择**全广播式**、**选择广播式-点对点**、**选择广播式-发布订阅**三种调度策略，不同的用户和发布者试用不同的调度策略
- Model和Publisher使用全广播式、Consumer和Producer使用选择广播式、Subscriber和Publisher使用选择广播式-发布订阅  
