
**思路：**来源于某家装互联网公司，c端下订单，订单派单中心的职责把订单按派单算法分配给b端。

**1业务流程：**
----------

![这里写图片描述](http://img.blog.csdn.net/20170801111454334?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd29sZmpzb24=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

**2 技术框架**
----------

 1. **主流开源技术**：springBoot/springMVC/Mybatis/Redis/Kafka/hikari
 2.  **请求与响应加密传输：**
Request 加密传输，@WebFilter解密映射到HttpServletRequestWrapper
Response加密（AES）全局响应
 3. **访问权限控制：**@auth全局拦截处理分发权限
 4. **高并发：**
唯一终端+一次完整请求生成唯一hash，并发请求、网络延迟、用户重复操作，hash未	释放前处于挂起状态，请求处理完成并响应，hash释放
 5. **全局异常与系统预警**
 6. **日志分类按日切割** 
 7. **Mybatis多数据源灵活切换** 
 8. **集成kafak/RabbitMq消息**，实时出发派单算法 
 9. **集成redis 细可粒度缓存、session-redis 分布式session共享**
 10. **@LogOrderAnnotation全局切面过滤访问日志**
 11. **零配置多线程任务线程池**
 12. **全局跨域处理**

