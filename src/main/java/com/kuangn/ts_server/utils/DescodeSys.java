package com.kuangn.ts_server.utils;

import java.io.*;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.text.DateFormat;

//import com.acorn.dao.licenceKey;

/**
 * Created by wli on 24/06/16.
 */
public class DescodeSys {
    public int encode(String filepath, String destfile) {
        File file = new File(filepath);
        if (file.exists()) {
            try {
//                Process p = Runtime.getRuntime().exec("tar -zcvf -  " + filepath + "|openssl des3 -salt -k kuangn | dd of=kuangn.des3");
                Runtime rt = Runtime.getRuntime();
                BufferedReader input = null;
                BufferedReader err = null;
                Process pr = null;
                StringBuffer stdout = new StringBuffer();
                StringBuffer stderr = new StringBuffer();


//                String command = "tar -zcvf - " + filepath+ "|"+"openssl des3 -salt -k kuangn"+"|"+ " dd of=License.des3";
                String command = "tar -zcvf - " + filepath+ "|"+"openssl des3 -salt -k kuangn"+"|"+ " dd of=" + destfile;
                System.out.println(command);

                pr = rt.exec(new String[]{"bash","-c",command});
                input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                err = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
                String line = null;

                while ((line = input.readLine()) != null) {
                    System.out.println(line);
                    stdout.append(line);
                }

                while((line = err.readLine()) != null){
                    System.out.println("err:" + line);
                    stderr.append(line);
                }
                pr.waitFor();
                int exitVal = pr.exitValue();
                System.out.println("Process exit value is: " + exitVal);
                return exitVal;
            } catch (Exception e) {
                System.out.println(e.toString());
                return 1;
            }

        }else{
            return 1;
        }
    }

    public int decode() {
        /*find the latest des3 file in upload folder then  decode*/
        try {
            Runtime rt = Runtime.getRuntime();
            BufferedReader input = null;
            BufferedReader err = null;
            Process pr = null;
            StringBuffer stdout = new StringBuffer();
            StringBuffer stderr = new StringBuffer();

            String command = "ls -rt /opt/tcdir/upload/*.des3";
//                System.out.println(command);

            pr = rt.exec(new String[]{"bash","-c",command});
            input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            err = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String line = null;
            String timinal = null;

            while ((line = input.readLine()) != null) {
                timinal = line.trim();
                stdout.append(timinal);
            }

            System.out.println(timinal);

            while((line = err.readLine()) != null){
                stderr.append(line);
            }
            pr.waitFor();

            if(timinal !=null) {
                String command1 = "dd if=" + timinal + " |openssl des3 -d -k kuangn|tar zxf - -C /opt/touchstone/";
                int exit = execcommand(command1);
                return exit;
            }else{
                return 1;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return 1;
        }
    }

    public int  execcommand(String command){
        try {
            Runtime rt = Runtime.getRuntime();
            BufferedReader input = null;
            BufferedReader err = null;
            Process pr = null;
            StringBuffer stdout = new StringBuffer();
            StringBuffer stderr = new StringBuffer();
            System.out.println(command);


            pr = rt.exec(new String[]{"bash","-c",command});
            input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            err = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String line = null;

            while ((line = input.readLine()) != null) {
                System.out.println(line);
                stdout.append(line);
            }

            while((line = err.readLine()) != null){
                System.out.println( line);
                stderr.append(line);
            }
            pr.waitFor();
            int exitVal = pr.exitValue();
            System.out.println("Process exit value is: " + exitVal);
            return exitVal;
        } catch (Exception e) {
            System.out.println(e.toString());
            return 1;
        }
    }

    public String createfile(Map<String, String> map){
        /*
        create license json file
        */
        System.out.println(map.getClass());
        if(map==null) {
             return "licence have something value is empty" ;
        }else{
            try {
//                File file = new File("/opt/apache-tomcat-8.0.35/webapps/ts-server/des3/license.json");
                File file = new File("/var/ts-server/webapps/ts-server/des3/license.json");
//                File file = new File("/home/alfred/ts-server/src/main/webapp/des3/license.json");
                if(file.exists()){
                    try {
                        file.delete();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                file.createNewFile();
                Date d = new Date();
                String s ;
                s = d.toString();
                StringBuilder sb = new StringBuilder();
                sb.append("{");
                sb.append("\n");
                sb.append("'uuid'" + ":'" + map.get("uuid")+"',");
                sb.append("\n");

                sb.append("'authorizedFor'" +":'"  +map.get("authorizedFor") + "',");
                sb.append("\n");
                sb.append("'authorizedEdtion' " +":'" +map.get("authorizedEdtion") +"',");
                sb.append("\n");
                sb.append("'sn' " + ":'" +map.get("sn") +"',");
                sb.append("\n");
//                sb.append("'authorizedDate'" +":'" + map.get("authorizedDate") +"',");
                sb.append("'authorizedDate'" +":'" + s +"',");
                sb.append("\n");
                sb.append("'authorizedPeriod' " +":'" +map.get("authorizedPeriod") +"',");
                sb.append("\n");
                sb.append("'protocol'"+":'" + "all" + "',");
                sb.append("\n");
                sb.append("'module'"+ ":'" + "all" +"'");
                sb.append("\n");
                sb.append("}");
                FileWriter fw = new FileWriter(file);

                fw.write(sb.toString());
                fw.flush();
                fw.close();
                return "ok";
            }catch (Exception e){
                return e.toString();
            }
//            return "ok";
            }
        }

      public String createjson(String str){
          System.out.println(str);
//          System.out.println(System.getProperty("user.dir"));
          File file = new File("/var/ts-server/webapps/ts-server/des3/testcase.json");
//          File file = new File("/opt/apache-tomcat-8.0.35/webapps/ts-server/des3/testcase.json");
          try {
              System.out.println(file.getCanonicalPath());
          }catch(Exception e){
              e.printStackTrace();
          }

          try{
              file.createNewFile();
              FileWriter fw = new FileWriter(file);
              fw.write(new String(str));
              fw.flush();
              fw.close();
              return "ok";
          }catch(Exception e){
              System.out.println(e.toString());
              return e.toString();
            }
      }
//    public static void main(String[] args) throws Exception {
//        FileWriter fw = new FileWriter("/home/alfred/demo.json");
//        StringBuilder sb = new StringBuilder();
//        sb.append("{");
//        sb.append("'uuid '" + ":" + "'hello'");
//        sb.append("}");
//        fw.write(sb.toString());
//        fw.flush();
//        fw.close();
/*        Date t = new Date();
        String s ;
        s = DateFormat.getDateInstance(DateFormat.MEDIUM).format(t);
        System.out.println(s);*/
//    }

}
