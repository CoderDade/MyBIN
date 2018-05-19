# MyBin

---

## 声明
- 这个工具包只是个人用于学习 [MyBatis](https://github.com/mybatis/mybatis-3) 练手项目，可以认为是 Mybais 代码的简化版本，不用于任何商业项目，若有侵权之处，麻烦联系本人，mail：815524363@qq.com
- 这个工具包主要用于十六进制文件（以下简称Bin文件）的清洗（转化）过程，把十六进制里面的数据转化为具体的实体类
- 工具包主要是模仿 [MyBatis](https://github.com/mybatis/mybatis-3) 这个框架，期间有间接应用Mybatis的思路（比如Mapper的概念，以及Entity的处理等），也有直接使用Mybatis的代码（这部分只是暂时，未来会逐渐移除）

## DEMO
假如有Bin文件内容如下，```050001010255560600010203454647```,清洗规则是：
0. 文件是分为很多个block的，一个block代表一个实体
1. 前4个字节是一个Block的长度，并且要反转，比如 0500 反转就是 0005，把十六进制的(0005)转化为十进制就是5
2. 根据前四个字节的值读取一个block的内容，也就是`0101025556`
3. 接着是block里面的子元素，规则是开头一个字节为子元素的长度，比如这里第一个子元素是十六进制(01)，读取一个字节长度，值也就是十六进制(01),依次类推，第二个子元素的长度是十六进制(02)，值是十六进制(5556)
4. 根据调用工具包的Entity将资源数转为对应的Entity,比如Entity是
```
public class Entity{
    int id;
    String name;
}
```
那么，这个block就转化为 {id:1,name:'UV'} 的 Entity对象
5. 剩余block重复步骤2-4

## 期望
从上面的Demo可以看出，不使用工具包也可以轻松实现。但是当要清晰的文件数量一多，很多IO操作（不管是IO还是NIO）都会大量重复。
因此，我期望，只需要简单的配置（xml或者注解），像使用mybatis一样定义接口，然后加上必要的注解信息就可以直接清洗文件。不需要我自己去处理IO。

## 用法
1、 xml 配置
类似Mybatis的配置，不过这里不需要DataSource之类的，只需要配置好指定的接口或者接口的路径，比如
```
<!--单个接口-->
    <bean class="com.dade.bin.mybin.mapper.MapperFactoryBean">
        <property name="mapperInterface" value="com.dade.bin.test.BINTestInterface"/>
    </bean>
```
或者
```
<!--basePackage-->
    <bean class="com.dade.bin.mybin.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.dade.bin.test"/>
    </bean>
```
2、 定义返回值，并使用注解配置
```
public class BINTestEntity {

    @Order(1)
    Integer len;

    @Order(2)
    String str;

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "BINTestEntity{" +
                "len=" + len +
                ", str='" + str + '\'' +
                '}';
    }
}
```

3、定义接口，并使用注解配置
```
public interface BINTestInterface {

    @Block(len=2, reverse = true)
    @SubBlock(len=1)
    @FilePackage("D:\\standBin.BIN")
    BINTestEntity testSingleEntity();

    @Block(len=2, reverse = true)
    @SubBlock(len=1)
    @FilePackage("D:\\standBin.BIN")
    List<BINTestEntity> testMulEntity();

}

```
4、 最后，调用接口方法并执行就可以
```
    @Test
    public void getSpringBean(){
        BINTestEntity binTestEntity = binTestInterface.testSingleEntity();
        System.out.println(binTestEntity.toString());
    }

    @Test
    public void getBean(){
        List<BINTestEntity> binTestEntitys = binTestInterface.testMulEntity();
        binTestEntitys.forEach(System.out::println);
    }
```
