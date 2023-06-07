## 常见注解
@Autowired: 先通过byType判断再通过byName判断

@Resource:先通过byName判断再通过byType判断  且 @Resource(name = "dog22")可以设置要自动装配对象的名称