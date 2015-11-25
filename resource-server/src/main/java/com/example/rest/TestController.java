package com.example.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Filip Lindby
 *
 */
@RestController
@RequestMapping("test")
public class TestController {

	@RequestMapping(method = GET)
	public String getTest() {
		return "test resource";
	}

}
