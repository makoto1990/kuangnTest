package com.kuangn.ts_server.rest.resources.types;

import com.kuangn.ts_server.utils.DescodeSys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alfred on 16-7-12.
 */
@Component
@Path("/license")
public class License {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> recv(Map<String, Object> value) throws Exception {
        Map<String, String> map1 = (Map<String,String>)value.get("data");
        System.out.println(map1);

        Map<String, String> map = new HashMap<String, String>();
        if (value == null) {
            logger.error("have no variable");
            map.put("data", "fail");
//            return "fail";
            return map;
        }
        String jsonpath1 = "des3/license.json";
//        String jsonpath1 = "/home/alfred/ts-server/webapps/ts-server/des3/license.json";
//        String jsonpath1 = "/opt/apache-tomcat-8.0.35/webapps/ts-server/des3/license.json";
//        String des3path1 = "/opt/apache-tomcat-8.0.35/webapps/ts-server/des3/License.des3";
        String des3path1 = "des3/License.des3";
//        String des3path1 = "/home/alfred/ts-server/webapps/ts-server/des3/License.des3";
        DescodeSys descodeSys = new DescodeSys();
        String retcreatejson = null;
        int retcreatedes3 = 0;
        retcreatejson = descodeSys.createfile(map1);

        if (retcreatejson.equals("ok")) {
            retcreatedes3 = descodeSys.encode(jsonpath1, des3path1);
            if (retcreatedes3 == 0) {
                logger.info("translate des3 ok");
                map.put("data", "ok");
//                return "ok";
                return map;
            } else {
                logger.error("can not translate to des3");
                map.put("data", "fail");
                return map;
//                return "fail";
            }
        } else {
            logger.error("can not create json file");
            map.put("data", "fail");
//            return "fail";
            return map;
        }
    }
}
