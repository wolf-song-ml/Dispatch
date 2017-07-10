订单派单中心
思路：来源于某家装互联网公司，c端下订单，订单派单中心的职责把订单按派单算法分配给b端。
业务流程：

2 技术框架
主流开源技术：springBoot/springMVC/Mybatis/Redis/Kafka/hikari
请求与响应加密传输：
Request 加密传输，@WebFilter解密映射到HttpServletRequestWrapper
Response加密（AES）全局响应
访问权限控制：
@auth全局拦截处理分发权限
高并发：
唯一终端+一次完整请求生成唯一hash，并发请求、网络延迟、用户重复操作，hash未	释放前处于挂起状态，请求处理完成并响应，hash释放
全局异常与系统预警
日志分类按日切割
Mybatis多数据源灵活切换
集成kafak消息，实时出发派单算法
集成redis 细可粒度缓存、session-redis 分布式session共享
@LogOrderAnnotation全局切面过滤访问日志
零配置多线程任务线程池
全局跨域处理