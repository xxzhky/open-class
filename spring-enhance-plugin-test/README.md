https://blog.csdn.net/qq_35915384/article/details/80227297

使用@WebMvcTest注解时，只有一部分的 Bean 能够被扫描得到，它们分别是：
`@Controller
@ControllerAdvice
@JsonComponent
Filter
WebMvcConfigurer
HandlerMethodArgumentResolver `

---------------
我们可以使用 @DataJpaTest注解表示只对 JPA 测试；
@DataJpaTest注解它只扫描@EntityBean 和装配 Spring Data JPA 存储库，
其他常规的`@Component（包括@Service、@Repository等）Bean` 则不会被加载到 Spring 测试环境上下文。

@DataJpaTest 还提供两种测试方式：
1.使用内存数据库 h2database，Spring Data Jpa 测试默认采取的是这种方式；
2.使用真实环境的数据库。

**默认情况下，在每个 JPA 测试结束时，事务会发生回滚。这在一定程度上可以防止测试数据污染数据库。**
如果你不希望事务发生回滚，你可以使用`@Rollback(false)`注解，该注解可以标注在类级别做全局的控制，
也可以标注在某个特定不需要执行事务回滚的方法级别上。

也可以显式的使用注解 @Transactional 设置事务和事务的控制级别，放大事务的范围。
