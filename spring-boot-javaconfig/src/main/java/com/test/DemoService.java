package com.test;

public class DemoService {
    private DemoRepository demoRepository;

    public DemoService(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    public void print() {
        demoRepository.getClass();
        System.out.println("DemoService" + demoRepository.getClass());
    }
}
