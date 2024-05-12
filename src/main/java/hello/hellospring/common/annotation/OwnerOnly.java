package hello.hellospring.common.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OwnerOnly {
    Class<?> value() default Object.class;
}
