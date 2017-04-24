package com.test;

public class DemoRepository {

    private DataSource dataSource;

    public DemoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void print() {
        dataSource.getClass();
        System.out.println("DemoRepository" + dataSource.getClass());
    }

}
