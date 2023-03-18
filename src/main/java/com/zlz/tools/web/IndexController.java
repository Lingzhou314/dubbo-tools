package com.zlz.tools.web;

import static com.zlz.tools.utils.Result.success;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zlz.tools.bean.InvokeBean;
import com.zlz.tools.utils.DubboTelnet;
import com.zlz.tools.utils.Result;

@RestController
public class IndexController {
	DubboTelnet telnet;

	@GetMapping
	public ModelAndView index() {
		return new ModelAndView("index");
	}
	
	@GetMapping("/connect")
	public Result<String> connect(String address, int port) {
		telnet = new DubboTelnet(address, port);
		return success();
	}
	
	@GetMapping("/ls/provider")
	public Result<String> lsProvider() {
		return success(telnet.sendCommand("ls"));
	}
	
	@GetMapping("/ls/method")
	public Result<String> lsMethod(String provider) {
		return success(telnet.sendCommand("ls -l " + provider));
	}
	
	@PostMapping("/invoke")
	public Result<?> invoke(@RequestBody InvokeBean bean) {
		bean.setMethod(bean.getMethod().replaceAll(".*?\\s+(.+?)\\(.*?\\)", "$1"));
		String cmd = String.format("invoke %s.%s(%s)", bean.getProvider(), bean.getMethod(), bean.getParams());
		System.out.println(cmd);
		return success(telnet.sendCommand(cmd));
	}
	
}
