# Mybatis学习笔记
	：mybatis是一个实现数据持久化的开源框架，简单理解就是对JDBC进行封装
	：Java到Mysql之间的映射
## 优点：
	- 减少代码量
	- 简单持久的矿建，容易上手
	- 将sql语句全部写在xml中，从代码中彻底分离，降低耦合度。
	- 支持编写动态sql
	- ORMapping 对象关系映射
## 缺点：
	- 每一条sql语句都需要编写
	- 依赖与底层的数据库，移植性差，不能随意更改数据库
## 初始化使用：
	- 依赖文件
		<dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.9</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.48</version>
        </dependency>

    - 创建表
    	usr mybatis;
    	create table t_ccount(
    		id int primary key auto_increment,
    		username varchar(11),
    		password varchar(11),
    		age int
    	);

    - 创建数据库表对应的实体类
    	public class Account {
		  private Integer id;
		  private String username;
		  private String password;
		  private Integer age;
		}

	- 编写xml文件
		<?xml version="1.0" encoding="UTF-8"?>
		<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
		<mapper namespace="com.shisan.mapper.AccountMapper">
		    <insert id="save">
		        insert into t_account(username, password, age)
		        values (#{username}, #{password}, #{age})
		    </insert>
		</mapper>
		· namespace通常设置为文件所在包+文件名的形式
		· id 对应接口类中方法名

	- 编写配置信息
		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE configuration
		        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
		        "http://mybatis.org/dtd/mybatis-3-config.dtd">
		<configuration>
		    <!-- 数据源配置 -->
		    <environments default="development">
		        <environment id="development">
		            <!--配置JDBC事务管理-->
		            <transactionManager type="JDBC"/>
		            <!--POOLED配置JDBC数据库连接池-->
		            <dataSource type="POOLED">
		                <property name="driver" value="com.mysql.jdbc.Driver"/>
		                <property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
		                <property name="username" value="root"/>
		                <property name="password" value="root"/>
		            </dataSource>
		        </environment>
		    </environments>
		    <!-- 映射文件配置 -->
		    <mappers>
		        <mapper resource="com/shisan/mapper/accountMapper.xml"/>
		        <!-- 如果使用注解方式，可以使用以下方式配置包扫描 -->
		        <!-- <package name="com.example.mapper" /> -->
		    </mappers>
		</configuration>

	# 调用Mybatis的原生接口指向添加操作
		public class Test {
		  public static void main(String[] args) {
		    // 加载Mybatis配置文件
		    InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("config.xml");
		    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
		    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
		    SqlSession sqlSession = sqlSessionFactory.openSession();
		    String statement = "com.shisan.mapper.AccountMapper.save";
		    Account account = new Account(1002, "admin", "admin", 20);
		    sqlSession.insert(statement,account);
		    sqlSession.commit();
		  }
		}

	# 通过Mapper代理实现自定义接口
		- 自定义接口
			public interface AccountMapper {
			    Integer save(Account account);
			    Integer update(Account account);
			    Integer delete(Integer id);
			    List<Account> findAll();
			    Account findById(Integer id);
			}

		- 创建接口对应Mapper.xml、
			<?xml version="1.0" encoding="UTF-8"?>
			<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
			        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
			<mapper namespace="com.shisan.mapper.AccountMapper">
			    <insert id="save" parameterType="com.shisan.entity.Account">
			        insert into t_account(username, password, age)
			        values (#{username}, #{password}, #{age})
			    </insert>
			    <update id="update" parameterType="com.shisan.entity.Account">
			        update account
			        set username=#{username},
			            password=#{password},
			            age     = #{age}
			        where id = #{id}
			    </update>
			    <delete id="delete">
			        delete
			        from account
			        where id = #{id}
			    </delete>
			    <select id="findAll" resultType="com.shisan.entity.Account">
			        select *
			        from account;
			    </select>
			    <select id="findById" parameterType="integer" resultType="com.shisan.entity.Account">
			        select *
			        from account
			        where id = #{id};
			    </select>
			</mapper>
		- 在config中注册AccountMapper.xml
			<!-- 映射文件配置 -->
		    <mappers>
		        <mapper resource="com/shisan/mapper/accountMapper.xml"/>
		        <!-- 如果使用注解方式，可以使用以下方式配置包扫描 -->
		        <!-- <package name="com.example.mapper" /> -->
		    </mappers>
		- 业务代码
			public class Test2 {
			  public static void main(String[] args) {
			    InputStream inputStream = Test2.class.getClassLoader().getResourceAsStream("config.xml");
			    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
			    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
			    SqlSession sqlSession = sqlSessionFactory.openSession();
			    // 获取实现接口的代理对象
			    AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
			    //    添加
			    accountMapper.save(new Account(1003, "孙悟空", "sun123", 500));
			    //    按照id查询
			    System.out.println(accountMapper.findById(1));
			    //    修改
			    accountMapper.update(new Account(1, "徐十三", "123123", 30));
			    //    删除
			    accountMapper.delete(2);
			    //    查询
			    List<Account> accounts = accountMapper.findAll();
			    for (Account account : accounts) {
			      System.out.println(account);
			    }
			    sqlSession.commit();
			    sqlSession.close();
			  }
			}
## Mapper.xml
	- statement标签：select、update、insert、delete(CRID)
	- parameterType:参数数据类型
		- 基本数据类型，通过id查询Account
		<select id="findById" parameterType="integer" resultType="com.shisan.entity.Account">
        select *
        from t_account
        where id = #{id};
    	</select>
    	- String类型
    	- 包装类
    	- 集合类型
    	- Java Bean
    	<update id="update" parameterType="com.shisan.entity.Account">
        update t_account
        set username=#{username},
            password=#{password},
            age     = #{age}
        where id = #{id}
   		</update>
   	- resultType: 结果类型
## 级联查询
	# 一对多
	- Student
	public class Student {
	  private Integer id;
	  private String name;
	  private Classes classes;
	}
	- Classes
	public class Classes {
	  private Integer id;
	  private String name;
	  private List<Student> studentList;
	}
	- StudentMapper
	public interface StudentMapper {
	  Student findById(Integer id);
	}
	- StudentMapper.xml
	<?xml version="1.0" encoding="UTF-8"?>
	<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

	<mapper namespace="com.shisan.mapper.StudentMapper">

	    <resultMap id="studentMap" type="com.shisan.entity.Student">
	        <id column="id" property="id"></id>
	        <result column="name" property="name"></result>
	        <association property="classes" javaType="com.shisan.entity.Classes">
	            <id column="cid" property="id"></id>
	            <result column="cname" property="name"></result>
	        </association>
	    </resultMap>
	    <select id="findById" resultMap="studentMap">
	        select s.id, s.name, c.id as cid, c.name as cname
	        from student s,
	             classes c
	        where s.id = #{id}
	          and s.cid = c.id;
	    </select>
	</mapper>
	- ClassesMapper
	public interface ClassesMapper {
	    Classes findById(Integer id);
	}
	- ClassesMapper.xml
	<?xml version="1.0" encoding="UTF-8"?>
	<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.shisan.mapper.ClassesMapper">
	    <resultMap id="classesMap" type="com.shisan.entity.Classes">
	        <id column="cid" property="id"></id>
	        <result column="cname" property="name"></result>
	        <collection property="studentList" ofType="com.shisan.entity.Student">
	            <id column="id" property="id"></id>
	            <result column="name" property="name"></result>
	        </collection>
	    </resultMap>

	    <select id="findById" resultMap="classesMap">
	        select s.id, s.name, c.id as cid, c.name as cname
	        from student s,
	             classes c
	        where c.id = #{id}
	          and s.cid = c.id;
	    </select>
	</mapper>

	# 多对多
	- Customer
	@Data
	public class Customer {
	  private Integer id;
	  private String name;
	  private List<Goods> goods;
	}
	- Goods
	@Data
	public class Goods {
	    private Integer id;
	    private String name;
	    private List<Customer> customers;
	}
	- CustomerMapper
	public interface CustomerMapper {
	  List<Customer> findById(Integer id);
	}
	- GoodsMapper
	public interface GoodsMapper {
	  List<Goods> findById(Integer id);
	}
	-CustomerMapper.xml
	<?xml version="1.0" encoding="UTF-8"?>
	<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.shisan.mapper.CustomerMapper">
	    <resultMap id="customerMap" type="com.shisan.entity.Customer">
	        <id column="cid" property="id"></id>
	        <result column="cname" property="name"></result>

	        <collection property="goods" ofType="com.shisan.entity.Goods">
	            <id column="gid" property="id"></id>
	            <result column="gname" property="name"></result>
	        </collection>
	    </resultMap>

	    <select id="findById" resultMap="customerMap">
	        select c.id, c.name cname, g.name gname
	        from customer c,
	             goods g,
	             menu m
	        where c.id = #{id}
	          and m.cid = c.id
	          and m.gid = g.id;
	    </select>
	</mapper>
	- GoodsMapper.xml
	<?xml version="1.0" encoding="UTF-8"?>
	<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.shisan.mapper.GoodsMapper">
	    <resultMap id="goodsMap" type="com.shisan.entity.Goods">
	        <id column="gid" property="id"></id>
	        <result column="gname" property="name"></result>

	        <collection property="customers" ofType="com.shisan.entity.Customer">
	            <id column="cid" property="id"></id>
	            <result column="cname" property="name"></result>
	        </collection>
	    </resultMap>

	    <select id="findById" resultMap="goodsMap">
	        select c.id, c.name cname, g.name gname
	        from customer c,
	             goods g,
	             menu m
	        where g.id = #{id}
	          and m.cid = c.id
	          and m.gid = g.id;
	    </select>
	</mapper>
## 逆向工程
	Mybatis框架需要：实体类、自定义Mapper接口、Mapper.xml
	逆向工程减少开发者来自动创建三个组件，减轻开发者的工作量，提高工作效率
	## Mybatis Generator MBG
	是一个专门为mybatis框架开发着制定的代码生成器，生成的接口支持基本的CRUD方法，但是一些相对复杂的sql还是需要手动完成
	- 新建maven工程，引入pom依赖
	<dependencies>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.10</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.49</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.4.0</version>
        </dependency>
    </dependencies>
    - 创建MBG配置文件
    	1.jdbcConnection配置数据可连接信息
    	2.javaModleGenerator配置JavaBean的生成策略
    	3.sqlMapGenerator配置SQL映射文件生成路径
    	4.JavaClientGenerator配置Mapper接口的生成策略
    	5.table配置目标数据表。
    	# generatorConfig.xml
    	<?xml version="1.0" encoding="UTF-8"?>
		<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
		        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
		<generatorConfiguration>
		    <!-- 数据库连接配置 -->
		    <context id="MyBatisGenerator" targetRuntime="MyBatis3">
		        <!-- 数据库连接信息 -->
		        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
		                        connectionURL="jdbc:mysql://localhost:3306/mybatis"
		                        userId="root"
		                        password="root">
		        </jdbcConnection>

		        <!-- Java 模型生成器配置 -->
		        <javaModelGenerator targetPackage="com.shisan.entity" targetProject="src/main/java">
		            <property name="enableSubPackages" value="true"/>
		            <property name="trimStrings" value="true"/>
		        </javaModelGenerator>

		        <!-- SQL 映射文件生成器配置 -->
		        <sqlMapGenerator targetPackage="com.shisan.mapper" targetProject="src/main/resources">
		            <property name="enableSubPackages" value="true"/>
		        </sqlMapGenerator>

		        <!-- Java 客户端生成器配置 -->
		        <javaClientGenerator type="XMLMAPPER" targetPackage="com.shisan.mapper" targetProject="src/main/java">
		            <property name="enableSubPackages" value="true"/>
		        </javaClientGenerator>

		        <!-- 逆向工程配置：要生成代码的表 -->
		        <table tableName="t_account" domainObjectName="Account"/>
		        <!-- 如果有多个表，可以继续添加多个 <table> 元素 -->
		    </context>
		</generatorConfiguration>
		# 启动类GeneratoConfig
		public class GeneratorConfig {
		    public static void main(String[] args) {
		        List<String> warnings = new ArrayList<>();
		        boolean overwrite = true;
		        String genConfig = "/generatorConfig.xml"; // 注意修改为正确的配置文件路径
		        File configFile = new File(GeneratorConfig.class.getResource(genConfig).getFile());

		        ConfigurationParser configurationParser = new ConfigurationParser(warnings);
		        Configuration configuration = null;
		        try {
		            configuration = configurationParser.parseConfiguration(configFile);
		        } catch (IOException | XMLParserException e) {
		            e.printStackTrace();
		        }

		        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		        MyBatisGenerator myBatisGenerator = null;
		        try {
		            myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
		        } catch (InvalidConfigurationException e) {
		            e.printStackTrace();
		        }

		        try {
		            myBatisGenerator.generate(null);
		        } catch (SQLException | IOException | InterruptedException e) {
		            e.printStackTrace();
		        }
		    }
		}
## Mybatis延迟加载
	延迟加载也叫懒加载、惰性加载，使用延迟加载可以提高程序的运行效率，针对数据持久层的操作，
	在某些特定的情况下访问特定的数据库，在其他情况下可以不访问这些表，从一定程度上减少Java应用和数据库的交互参数。
	- 开启延迟加载
	<settings>
        <!--打印日志-->
        <setting name="logImpl" value="STDOUT_LOGGING"/>
        <!--开启延迟加载-->
        <setting name="lazyLoadingEnabled" value="true"/>
    </settings>
## Mybatis缓存
	使用缓存可以减少Java应用和数据库的交互次数，从而提升程序的效率

	# Mybatis分类：
	1.一级缓存：SqlSession级别，默认是开启，不能关系
		SqlSession对象，再对象中有一个HashMap用于存储缓存数据，不同的SqlSession之间缓存数据区是互不影响的。
		但是如果执行DML操作之后，（增加、修改、删除）就必须清除缓存数据
	代码：
	public class AccountTest {
	  public static void main(String[] args) {
	    //
	    InputStream inputStream = AccountTest.class.getClassLoader().getResourceAsStream("config.xml");
	    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
	    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
	    SqlSession sqlSession = sqlSessionFactory.openSession();
	    AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
	    System.out.println(accountMapper.findById(1));
	    System.out.println(accountMapper.findById(1));
	    sqlSession.close();
	  }
	}
	2.二级缓存：Mapper级别，默认关闭，可以开启
		使用二级缓存，多个sqlSession使用同一个Mapper的SQL语句操作数据可得到的数据会存在二级缓存区，
		同样使用HashMap进行数据存储，相比较于一级缓存，二级缓存的范围更大，但优先级低，
		多个SqlSession可以共用二级缓存，二级缓存是跨SQL Session的。
		二级缓存是多个SqlSession共享的，起作用域是Maooer的同一个namespace，不同的SqlSession两次执行相同的
		SqlSession下的SQL语句，参数也相等，测第一次执行成功轴会将数据保存到二级缓存中，第二次可直接从二级缓存中取出数据

	- config.xml中开启二建缓存
	<settings>
        <!--打印日志-->
        <setting name="logImpl" value="STDOUT_LOGGING"/>
        <!--开启延迟加载-->
        <setting name="lazyLoadingEnabled" value="true"/>
        <!--开启二级缓存-->
        <setting name="cacheEnabled" value="true"/>
    </settings>

    - Mapper.xml中配置二级缓存
    <cache></cache>

    - Account实体类实现序列接口
    public class Account implements Serializable {
	  private Integer id;
	  private String username;
	  private String password;
	  private Integer age;
	}

	2.ehcache 二级缓存

	- pom.xml 添加相关依赖
	<dependency>
        <groupId>org.mybatis.caches</groupId>
        <artifactId>mybatis-ehcache</artifactId>
        <version>1.2.1</version>
        </dependency>
    <dependency>
        <groupId>net.sf.ehcache</groupId>
        <artifactId>ehcache-core</artifactId>
        <version>2.5.5</version>
    </dependency>

    - 添加ehcache.xml配置文件
    <ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="../config/ehcache.xsd"
         updateCheck="false">
    <diskStore/>
    <defaultCache
            maxEntriesLocalHeap="10000"
            eternal="false"
            overflowToDisk="false"
            timeToIdleSeconds="120"
            timeToLiveSeconds="120"
            memoryStoreEvictionPolicy="LRU"/>
	</ehcache>

    - congif.xml开启配置二级缓存
    <settings>
        <!--打印日志-->
        <setting name="logImpl" value="STDOUT_LOGGING"/>
        <!--开启延迟加载-->
        <setting name="lazyLoadingEnabled" value="true"/>
        <!--开启二级缓存-->
        <setting name="cacheEnabled" value="true"/>
    </settings>

    - Mapper.xml中配置二级缓存
    <cache type="org.mybatis.caches.ehcache.EhcacheCache">
        <!--缓存建立之后，最后一次访问缓存时间至缓存失效的时间间隔-->
        <property name="timeToIdleSeconds" value="3600"/>
        <!--缓存至创建的时间起，失效的时间间隔-->
        <property name="timeToLiveSeconds" value="3600"/>
        <!--缓存回收策略，LRU表示移除近期使用最少的对象-->
        <property name="memoryStoreEvictionPolicy" value="LRU"/>
    </cache>
    - 实例化类不需要实现序列化接口
    public class Account {
	  private Integer id;
	  private String username;
	  private String password;
	  private Integer age;
	}
## MyBatis 动态SQL




















