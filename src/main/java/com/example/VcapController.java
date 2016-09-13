package com.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Profile("cloud")
@Controller
public class VcapController {

    private static final String[] VCAP_APPLICATION_PROPERTIES_OBJECT = {
            "application_id",
            "application_name",
            "application_version",
            "instance_id",
            "name",
            "space_id",
            "version",
            "instance_index",
            "port"
    };

    private static final String[] VCAP_APPLICATION_PROPERTIES_ARRAYS = {
            "application_uris",
            "uris",
            "users"
    };

    @RequestMapping(value = "/vcap-application")
    public String vcapApplication(Model model) {
        Map<String, Object> application = new HashMap<>();
        JSONObject vcap = new JSONObject(System.getenv("VCAP_APPLICATION"));
        for (String property : VCAP_APPLICATION_PROPERTIES_OBJECT) {
            try {
                application.put(property, vcap.get(property));
            } catch (JSONException e) {
                System.out.println("JSONException: " + e);
            }
        }
        for (String property : VCAP_APPLICATION_PROPERTIES_ARRAYS) {
            try {
                JSONArray array = vcap.getJSONArray(property);
                for (int i=0; i<array.length(); i++) {
                    application.put(property + ".[" + i + "]", array.getString(i));
                }
            } catch (JSONException e) {
                System.out.println("JSONException: " + e);
            }
        }
        model.addAttribute("name", "VCAP Application");
        model.addAttribute("variables", application);
        return "variables";
    }

    @RequestMapping(value = "/vcap-services")
    public String vcapServices(Model model) {
        Map<String, Object> services = new HashMap<>();
        JSONObject vcap = new JSONObject(System.getenv("VCAP_SERVICES"));
        for (Object object : vcap.keySet()) {
            String key = object.toString();
            JSONArray service = vcap.getJSONArray(key);
            services.put(key,service);
        }
        model.addAttribute("name", "VCAP Services");
        model.addAttribute("variables", services);
        return "variables";
    }

}
