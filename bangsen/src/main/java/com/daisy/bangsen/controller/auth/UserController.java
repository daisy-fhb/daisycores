package com.daisy.bangsen.controller.auth;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.daisy.bangsen.dao.UserDao;
import com.daisy.bangsen.entity.auth.User;
import com.daisy.bangsen.service.UserService;
import com.daisy.bangsen.util.NetImgUtil;
import com.daisy.bangsen.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserDao userDao;

     @RequestMapping(value = "/signUp" , method = RequestMethod.POST)
     public RespBean regist(@RequestBody String jsondata){
        return userService.save(jsondata);
    }

    @RequestMapping(value = "/del" , method = RequestMethod.DELETE)
     public RespBean del(@RequestBody String jsondata){
        return userService.delete(jsondata);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.PUT)
     public RespBean update(@RequestBody String jsondata){
        return userService.update(jsondata);
    }

    @RequestMapping(value = "/query" , method = RequestMethod.GET)
     public RespBean query(String name, HttpServletRequest request,HttpServletResponse response) {
        RespBean re = new RespBean();
         try {
             String token = request.getHeader("token");
             Map<String, HashMap<String,String>> TokenUserMaps= redisTemplate.opsForHash().entries("TokenUserMap");
             HashMap<String,String> TokenUserMap=TokenUserMaps.get("data");
             if (!TokenUserMap.containsValue(token)) {
                 re.setStatus(401);
                 response.setStatus(401);
                 response.sendError(401);
             } else {
                 JSONObject jsondata = new JSONObject();
                 jsondata.put("name", name);
                 re = userService.query(jsondata.toString());
             }
         }catch (Exception e){
             e.printStackTrace();
         }
        return re;
    }

    @RequestMapping(value = "/getEmailCode" , method = RequestMethod.POST)
    public RespBean getEmailCode(@RequestBody String email){
        return  userService.getEmailCode(email);
    }

    @RequestMapping(value = "/validationName" , method = RequestMethod.POST)
    public RespBean validationName(@RequestBody String name){
        return  userService.validationName(name);
    }

    @RequestMapping(value = "/validationEmail" , method = RequestMethod.POST)
    public RespBean validationEmail(@RequestBody String postData){
        return  userService.validationEmail(postData);
    }
    @RequestMapping(value = "/validationEmailCode" , method = RequestMethod.POST)
    public RespBean validationEmailCode(@RequestBody String postData){
        return  userService.validationEmailCode(postData);
    }

    @RequestMapping(value = "/resetPassword" , method = RequestMethod.POST)
    public RespBean resetPassword(@RequestBody String postData){
        return  userService.resetPassword(postData);
    }

    @RequestMapping(value = "/getUserInfo" , method = RequestMethod.POST)
    public RespBean getUserInfo(@RequestBody String postData){
        Map<String, Map<String,String>> TokenUserMaps = redisTemplate.opsForHash().entries("TokenUserMap");
        Map<String, String> TokenUserMap =TokenUserMaps.get("data");
        Map<String, String> tmpToken=new HashMap();
        for (Map.Entry<String, String>  entry:TokenUserMap.entrySet()){
            tmpToken.put(entry.getValue(),entry.getKey());
        }

        String token= JSONUtil.parseObj(postData).getStr("token");
        RespBean respBean=new RespBean();
        if (tmpToken.containsKey(token)){
            String userid=tmpToken.get(token);
            User user=userDao.selectById(userid);;
            JSONObject res=JSONUtil.createObj();
            List<String> roles=new ArrayList<>();
            roles.add("admin");
            roles.add("ceo");
            res.put("info",user);
            res.put("token",token);
            res.put("role",roles);
            if (user!=null){
                respBean.setStatus(200);
                respBean.setData(res);
            }
        }else{
            respBean.setStatus(2000);
        }
        return respBean;
    }

    @RequestMapping(value = "/signIn" , method = RequestMethod.POST)
     public RespBean signin(@RequestBody String jsondata){
        return  userService.signin(jsondata);
    }

    @RequestMapping(value = "/vertifypic" , method = RequestMethod.GET)
     public boolean vertifypic(HttpServletResponse response) {
         try {
             String url = "https://uploadbeta.com/api/pictures/random/?key=BingEverydayWallpaperPicture";
             NetImgUtil.ImageRequest(url, response);
         }catch (Exception e){
             e.printStackTrace();
         }
            return true;
        }



    @RequestMapping(value = "/signOut" , method = RequestMethod.POST)
     public RespBean signup(@RequestBody String jsondata){
        return  userService.signout(jsondata);
    }

    @RequestMapping(value = "/roles" , method = RequestMethod.GET)
     public RespBean roles(){
        RespBean  re=userService.getRoles();
        return re;
    }
}
