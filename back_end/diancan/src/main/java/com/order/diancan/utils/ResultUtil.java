package com.order.diancan.utils;

import com.alibaba.fastjson.JSON;
import com.order.diancan.bean.User;

public class ResultUtil {
    /**
     * 请求返回成功
     * @param object
     * @return
     */

    public static Msg success(Object object){
        Msg msg = new Msg();
        msg.setCode(200);
        msg.setMsg("请求成功");
        msg.setData(object);
        return msg;
    }
    public static Msg success(){
        return success(null);
    }
    public static Msg registerSuccess(){
        return success("用户注册成功");
    }
    public static Msg loginSuccess(User user){
        user.setPassword("");
        String json = JSON.toJSONString(user);
        return success(json);
    }
    public static Msg error(Integer code,String resultmsg){
        Msg msg = new Msg();
        msg.setCode(code);
        msg.setMsg(resultmsg);
        return msg;
    }
}
