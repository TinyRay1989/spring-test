package com.test;


import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestJavaConfig {

    private AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
            ApplicationConfig.class);

    @Test
    public void testDemoService() {
        DemoRepository demoService = ctx.getBean(DemoRepository.class);
        demoService.print();
    }

    @Test
    public void testDemoRepository() {
        DemoRepository demoRepository = ctx.getBean(DemoRepository.class);
        demoRepository.print();
    }

    @Test
    public void testDataSource() {
        DataSource dataSource = ctx.getBean(DataSource.class);
        dataSource.print();
    }

}
