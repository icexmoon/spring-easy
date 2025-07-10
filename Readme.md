# Spring Easy

# 用途

通过自动配置，实现了一些国内 Spring Boot 开发时需要在 Spring Boot 框架基础上完成的一些配置工作，可以提升基于 Spring Boot 开发 Web 应用的效率。

# 安装

```xml
<dependency>
    <groupId>cn.icexmoon</groupId>
    <artifactId>spring-easy-boot-starter</artifactId>
</dependency>
```

# 功能说明

## 封装控制器方法返回值

提供对 JSON 返回的统一封装。

比如 Controller 方法：

```java
@GetMapping("/say")
public Map<String, String> say() {
    return Map.of("msg", "hello");
}
```

Spring Boot 默认返回：

```json
{
	"msg": "hello"
}
```

使用本应用后：

```json
{
	"success": true,
	"message": null,
	"data": {
		"msg": "hello"
	}
}
```

这里利用返回值处理器 [ResponseBodyAdvice](https://github.com/icexmoon/spring-easy/blob/main/spring-easy-boot-starter/src/main/java/cn/icexmoon/springeasy/boot/ResultControllerAdvice.java) 对控制器方法的返回值进行统一封装，将其封装为 [Result](https://github.com/icexmoon/spring-easy/blob/main/spring-easy-util/src/main/java/cn/icexmoon/springeasy/util/Result.java) 对象的 data 属性，然后由 Spring 的 ResponseBody 返回值处理器（Return Value Resolver）进行处理，即使用消息转换器（默认为 Jackson）将 Result 对象解析成 JSON 字符串作为响应报文体返回。

需要注意的是，由 @Response 注解标记，且返回类型是 String 的控制器方法比较特殊：

```java
@GetMapping("/msg")
public String msg() {
    return "<h1>hello</h1>";
}
```

这个控制器方法的返回值不走 ResponseBody 返回值处理器，会被另一个返回值处理器处理，处理方式也很简单，直接作为字符串填充到响应报文体中返回。这也符合开发者需要，因为这种方式定义的控制器方法，通常会直接返回 Html 片段等，不需要返回值处理器进行消息转换等额外处理。

因此，对于这种类型的控制器方法，本项目同样不会进行封装，以便行为和 Spring Boot 的默认实现保持一致。

如果你需要返回给前端的内容只有一个字符串，且希望返回标准封装的形式，可以直接构造 `Result` 对象并返回：

```java
@GetMapping("/msg2")
public Result<String> msg2() {
    return Result.success("hello", null);
}
```

本项目不会对 Result 类型的返回进行再次包装。

对于其他形式的基本类型返回，本项目都会进行封装，比如：

```java
@GetMapping("/void")
public void voidMethod() {
}

@GetMapping("/boolean")
public boolean booleanMethod() {
    return false;
}

@GetMapping("/int")
public int intMethod() {
    return 11;
}
```

都会包装并返回 Result 对象的 JSON 字符串。

## 统一处理并返回错误信息

项目使用异常处理器（Exception Handler）[ErrorControllerAdvice](https://github.com/icexmoon/spring-easy/blob/main/spring-easy-boot-starter/src/main/java/cn/icexmoon/springeasy/boot/ErrorControllerAdvice.java) 对异常进行统一处理，并封装成标准返回值返回。

比如会产生异常的控制器方法：

```java
@GetMapping("/default")
public Result<Void> defaultError() {
    int i = 1 / 0;
    return Result.success();
}
```

返回报文：

```json
{
	"success": false,
	"message": "/ by zero",
	"data": null
}
```

控制台中会由`cn.icexmoon.springeasy.boot.ErrorControllerAdvice`输出相关异常信息日志，日志级别为`error`。

业务层面产生的异常，比如缺少用户信息等，应当使用自定义异常 BusinessException，这个异常同样会被捕获并封装成统一格式返回，区别在于默认异常的 HTTP 状态码是 500，业务异常的 HTTP 状态码是 200。

对于 Servlet 异常（比如 Filter 产生的），定义了一个控制器 [ServletErrorController](https://github.com/icexmoon/spring-easy/blob/main/spring-easy-boot-starter/src/main/java/cn/icexmoon/springeasy/boot/ServletErrorController.java) 进行处理，以同样的返回标准 Result。控制器方法中只会进行简单异常信息打印和控制台异常调用日志输出：

```java
@ResponseBody
public Result<?> error(HttpServletRequest request) {
    Throwable error = (Throwable) request.getAttribute((RequestDispatcher.ERROR_EXCEPTION));
    log.error(error.getMessage(), error);
    return Result.fail("Servlet 错误：" + error.getMessage());
}
```

如果不满足需要，可以覆盖这个 Bean 进行自定义。

该控制器默认使用 `/error` 路径进行内部跳转，如果你不想使用该路径（通常是被其它控制器方法已经占用），可以通过配置文件中的属性进行修改：

```yaml
spring-easy:
  boot-starter:
    error-page-path: /servlet_error
```

##  时间类型转换

项目添加了针对 `LocalDateTime` 和 `LocalDate` 的类型转换器，以支持国内常见的 `yyyy-MM-dd HH:mm:ss` 或`yyyy-MM-dd` 格式的日期/时间字符串转换。

启用本项目后可以直接通过 URL 的查询参数传递时间字符串：

```
localhost:8080/hello/time?time=2025-07-09 11:08:03
```

控制器方法：

```java
@GetMapping("/time")
public LocalDateTime time(@RequestParam LocalDateTime time) {
    log.info(time.toString());
    return time;
}
```

`LocalDateTime`类型的`@RequestParam`参数可以被正常解析，不需要使用其他额外的类型转换注解。

为了能够让 jackson 正确解析和编码同样格式的时间字符串，本项目添加了相应的 jackson 解析器/编码器。因此如果使用本项目的时间类型转换功能，最好不要替换 Spring Boot 默认的消息转换器 jackson。

## IEnum 枚举类型转换

如果你的项目使用 MyBatisPlus，且实体类中存在枚举字段，使用`IEnum`接口是最方便的：

```java
@TableName(value = "user")
@Data
public class User {
    public enum Sex implements IDescEnum<Integer> {
        MALE(5, "男"), FEMALE(6, "女");
        private final Integer value;
        private final String desc;

        Sex(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        @Override
        public String getDesc() {
            return desc;
        }
    }

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 0 男，1 女
     */
    private Sex sex;
}
```

> 这里使用的 [IDescEnum](https://github.com/icexmoon/spring-easy/blob/main/spring-easy-util/src/main/java/cn/icexmoon/springeasy/util/IDescEnum.java) 接口是对 IEnum 接口的一个扩展，本质是一样的，只是额外记录枚举常量的中文信息。

MyBatisPlus 实现了 Service 层和数据库的 IEnum 和 int 值的转换，但如果将 IEnum 类型的枚举用于控制器方法参数或返回值，就会被 Spring Boot 当做普通的枚举类型进行处理。







