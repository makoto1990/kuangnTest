package com.kuangn.ts_server.rest.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kuangn.ts_server.utils.DescodeSys;

/**
 * Created by alfred on 16-7-7.
 */
@Component
@Path("/transcontenttestcase")
public class transcontent {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> recv(Map<String, String> value) throws Exception {
        Map<String, String > map = new HashMap<String, String>();
        if (value == null){
            logger.error("have no variable");
            map.put("data","fail");
//            return "fail";
            return map;
        }
        String jsonpath = "des3/testcase.json";
//        String jsonpath = "/home/alfred/ts-server/src/main/webapp/des3/testcase.json";
//        String des3path= "/home/alfred/ts-server/src/main/webapp/des3/testcase.des3";
        String des3path= "des3/testcase.des3";
        DescodeSys descodeSys = new DescodeSys();
        String retcreatejson = null;
        int retcreatedes3 = 0;
        retcreatejson = descodeSys.createjson(value.get("data").toString());

        if(retcreatejson.equals("ok")) {
            retcreatedes3 = descodeSys.encode(jsonpath, des3path);
            if (retcreatedes3 == 0) {
                logger.info("translate des3 ok");
                map.put("data","ok");
//                return "ok";
                return map;
            }else{
                logger.error("can not translate to des3");
                map.put("data","fail");
                return map;
//                return "fail";
            }
        }else{
            logger.error("can not create json file");
            map.put("data","fail");
//            return "fail";
            return  map;
        }
    }
}
