package com.test.resource;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Scope("prototype")
public class SpringControllerIsSingleton {
	private static int st = 0;
	private int index = 0;

	@RequestMapping("/test")
	public void test() {
		System.out.println(st++ + " | " + index++);
	}
}
