package com.example;

import org.json.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("name", "Home");
        return "index";
    }

    @RequestMapping(value = "/server-info")
    public String serverInfo(Model model, HttpServletRequest request) {
        Map<String, String> serverInfo = new HashMap<>();
        serverInfo.put("remote_host", request.getRemoteHost());
        serverInfo.put("remote_address", request.getRemoteAddr());
        serverInfo.put("remote_port", String.valueOf(request.getRemotePort()));
        serverInfo.put("remote_user", request.getRemoteUser());
        serverInfo.put("local_address", request.getLocalAddr());
        serverInfo.put("local_port", String.valueOf(request.getLocalPort()));
        serverInfo.put("method", request.getMethod());
        serverInfo.put("context_path", request.getContextPath());
        serverInfo.put("servlet_path", request.getServletPath());
        serverInfo.put("path_info", request.getPathInfo());
        serverInfo.put("request_uri", request.getRequestURI());
        serverInfo.put("request_url", request.getRequestURL().toString());
        serverInfo.put("query_string", request.getQueryString());
        serverInfo.put("character_encoding", request.getCharacterEncoding());
        serverInfo.put("scheme", request.getScheme());
        serverInfo.put("content_length", String.valueOf(request.getContentLength()));
        serverInfo.put("server_name", request.getServerName());
        serverInfo.put("server_port", String.valueOf(request.getServerPort()));
        model.addAttribute("name", "Server Info");
        model.addAttribute("variables", serverInfo);
        return "variables";
    }

    @RequestMapping(value = "/headers-info")
    public String headersInfo(Model model, HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerEnum = request.getHeaderNames();
        while ( headerEnum.hasMoreElements()) {
            String key = headerEnum.nextElement();
            String value = request.getHeader(key);
            headers.put(key,value);
        }
        model.addAttribute("name", "HTTP Headers");
        model.addAttribute("variables", headers);
        return "variables";
    }

    @RequestMapping(value = "/system-environment")
    public String systemEnvironment(Model model) {
        Map<String, String> environment = new HashMap<>();
        for (String key : System.getProperties().stringPropertyNames()) {
            environment.put(key, System.getProperty(key));
        }
        model.addAttribute("name", "System Environment Variables");
        model.addAttribute("variables", environment);
        return "variables";
    }

    @RequestMapping(value = "/environment")
    public String javaEnvironment(Model model) {
        model.addAttribute("name", "Java Environment Variables");
        model.addAttribute("variables", System.getenv());
        return "variables";
    }

}
