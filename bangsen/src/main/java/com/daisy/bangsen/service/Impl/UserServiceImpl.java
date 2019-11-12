package com.daisy.bangsen.service.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.daisy.bangsen.dao.DeptDao;
import com.daisy.bangsen.dao.UserDao;
import com.daisy.bangsen.entity.Department;
import com.daisy.bangsen.entity.Role;
import com.daisy.bangsen.entity.User;
import com.daisy.bangsen.service.UserService;
import com.daisy.bangsen.util.RespBean;
import com.daisy.bangsen.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    DeptDao deptDao;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public RespBean save(String postData) {
        RespBean respBean = new RespBean();
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        try {
            User user = JSONUtil.toBean(postData, User.class);
            user.setId(snowflake.nextIdStr());
            user.setPassword(SecureUtil.md5().digestHex16(user.getPassword()));
            user.setUptime(new Timestamp(System.currentTimeMillis()));
            user.setBirthday(user.getBirthday());
            user.setInterviewtime(user.getInterviewtime());
            user.setIshire("0");
            user.setDeptid("—");
            HashMap param = new HashMap();
            param.put("account", user.getAccount());
            List<User> tmp = userDao.selectByMap(param);
            if (tmp != null && !tmp.isEmpty()) {
                respBean.setStatus(2000);
                respBean.setMsg("注册失败,账号名称重复");
            } else {
                int re = userDao.insert(user);
                if (re > 0) {
                    respBean.setStatus(200);
                    respBean.setMsg("注册成功");
                } else {
                    respBean.setStatus(2000);
                    respBean.setMsg("注册失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            respBean.setStatus(2000);
            respBean.setMsg("注册失败," + e.getMessage());
        }
        return respBean;
    }

    @Override
    public RespBean delete(String id) {
        RespBean respBean = new RespBean();
        try {
            int re = userDao.deleteById(JSONUtil.parseObj(id).getStr("id"));
            if (re > 0) {
                respBean.setStatus(200);
                respBean.setMsg("删除成功");
            } else {
                respBean.setStatus(2000);
                respBean.setMsg("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            respBean.setStatus(2000);
            respBean.setMsg("删除失败," + e.getMessage());
        }
        return respBean;
    }

    @Override
    public RespBean update(String postData) {
        RespBean respBean = new RespBean();
        try {
            User user = JSONUtil.toBean(postData, User.class);
            int re = userDao.updateById(user);
            if (re > 0) {
                respBean.setStatus(200);
                respBean.setMsg("更新成功");
            } else {
                respBean.setStatus(2000);
                respBean.setMsg("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            respBean.setStatus(2000);
            respBean.setMsg("更新失败，" + e.getMessage());
        }
        return respBean;
    }

    @Override
    public RespBean query(String postData) {
        RespBean respBean = new RespBean();
        try {
            JSONObject param = JSONUtil.parseObj(postData);
            HashMap paraMap = new HashMap<>();

            if (param.containsKey("name") && StringUtils.isNotBlank(param.getStr("name"))) {
                paraMap.put("name", param.get("name"));
            }
            List<User> re = userDao.selectUser(paraMap);
            List<Department> deptall = deptDao.selectList(null);
            Map<String, JSONObject> deptmaps = new HashMap(); //完整的部门树
            Map<String, JSONObject> sub_deptmaps = new HashMap();//查询的指定部门树
            for (Department d : deptall) {
                deptmaps.put(d.getId(), JSONUtil.parseObj(d));
            }

            if (paraMap.containsKey("name")) {  //是否有条件查询
                TreeUtil.AllKeys.clear(); //清空需要展开的列
                for (User u : re) {
                    String userdeptid = u.getDeptid();
                    TreeUtil.AllKeys.add(userdeptid);
                    getDeptTreeids(userdeptid, "", deptmaps); //递归组装需要展开的列
                }
                Map AllexistTree = new HashMap();
                for (String deptid : TreeUtil.AllKeys) {
                    AllexistTree.put(deptid, 0);
                }
                for (Map.Entry<String, JSONObject> entry : deptmaps.entrySet()) {
                    if (AllexistTree.containsKey(entry.getKey())) {
                        sub_deptmaps.put(entry.getKey(), entry.getValue());
                    }
                }
            }

            List<Role> roleall = userDao.selectRoles();
            Map rolemaps = new HashMap();
            for (Role d : roleall) {
                rolemaps.put(d.getId(), d.getName());
            }
            for (User u : re) {
                u.setInterviewtime(u.getInterviewtime() == null ? DateUtil.now().split(" ")[0] : u.getInterviewtime().split("T")[0]);
                u.setBirthday(u.getBirthday() == null ? DateUtil.now().split(" ")[0] : u.getBirthday().split("T")[0]);
                JSONObject ujson = JSONUtil.parseObj(u);

                if (rolemaps.containsKey(u.getRoleid())) {
                    ujson.put("role", rolemaps.get(u.getRoleid()));
                }
                if (u.getSex() == 0) {
                    ujson.put("sex", "女");
                } else {
                    ujson.put("sex", "男");
                }

                if ("0".equals(u.getIshire())) {
                    ujson.put("ishire", "是");
                } else {
                    ujson.put("ishire", "否");
                }
                if (deptmaps.containsKey(u.getDeptid())) {
                    ujson.put("key", u.getId());
                    JSONArray children = deptmaps.get(u.getDeptid()).containsKey("children") ? deptmaps.get(u.getDeptid()).getJSONArray("children") : new JSONArray();
                    children.add(JSONUtil.parseObj(ujson));
                    deptmaps.get(u.getDeptid()).put("children", children);
                    deptmaps.get(u.getDeptid()).put("key", deptmaps.get(u.getDeptid()).getStr("id"));
                    deptmaps.get(u.getDeptid()).put("departmentName", deptmaps.get(u.getDeptid()).getStr("name"));
                    deptmaps.put(u.getDeptid(), deptmaps.get(u.getDeptid()).put("dept", true));
                } else {
                    ujson.put("key", u.getId());
                    if (u.getDeptid() == null || "—".equals(u.getDeptid())) {
                        if (deptmaps.get("0") == null) {
                            Department d = new Department();
                            d.setId("0");
                            JSONObject tmpd = JSONUtil.parseObj(d);

                            JSONArray children = new JSONArray();
                            children.add(ujson);
                            tmpd.put("departmentName", "未分配");
                            tmpd.put("dept", true);
                            tmpd.put("children", children);
                            tmpd.put("parentid", "—");
                            deptmaps.put("0", tmpd);
                        } else {
                            JSONObject tmpd = deptmaps.get("0");
                            JSONArray children = tmpd.containsKey("children") ? tmpd.getJSONArray("children") : new JSONArray();
                            children.add(ujson);
                            tmpd.put("children", children);
                            deptmaps.put("0", tmpd);
                        }
                    }
                }
            }


            JSONObject reall = JSONUtil.createObj();
            JSONArray ja = new JSONArray();
            if (paraMap.containsKey("name")) {
                for (Map.Entry<String, JSONObject> entry : sub_deptmaps.entrySet()) {
                    if (!entry.getValue().containsKey("dept")) {
                        entry.getValue().put("dept", true);
                        entry.getValue().put("departmentName", entry.getValue().getStr("name"));
                    }
                    ja.add(JSONUtil.parseObj(entry.getValue()));
                }
            } else {
                for (Map.Entry<String, JSONObject> entry : deptmaps.entrySet()) {
                    if (!entry.getValue().containsKey("dept")) {
                        entry.getValue().put("dept", true);
                        entry.getValue().put("departmentName", entry.getValue().getStr("name"));
                    }
                    ja.add(JSONUtil.parseObj(entry.getValue()));
                }
            }
            JSONArray treeja = TreeUtil.listToTree(ja, "id", "parentid", "children");//将所有结果组装为树形结构
            if (re.size() > 0) {
                reall.put("list", treeja);
                if (paraMap.containsKey("name")) {
                    reall.put("expandedRowKeys", TreeUtil.AllKeys); //赋值需要展开的列
                }
            } else {
                reall.put("list", new ArrayList<>());
            }

            respBean.setData(reall);
            respBean.setStatus(200);
            respBean.setMsg("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            respBean.setStatus(2000);
            respBean.setMsg("查询失败，" + e.getMessage());
        }
        return respBean;
    }


    @Override
    public RespBean signin(String jsondata) {
        RespBean respBean = new RespBean();
        JSONObject param = JSONUtil.parseObj(jsondata);
        HashMap paraMap = new HashMap<>();
        if (param.containsKey("account")) {
            paraMap.put("account", param.get("account"));
        }
        if (param.containsKey("password")) {
            paraMap.put("password", SecureUtil.md5().digestHex16(param.get("password").toString()));
        }
        List<User> users = userDao.selectByMap(paraMap);
        if (users != null && !users.isEmpty()) {
            Map<String, String> CurrentUserMap = redisTemplate.opsForHash().entries("CurrentUserMap");
            CurrentUserMap.put(users.get(0).getId(), JSONUtil.toJsonStr(users.get(0)));
            redisTemplate.opsForHash().putAll("CurrentUserMap", CurrentUserMap);

            Role role = new Role();
            role.setId("1");
            role.setName("管理员");

            String token = IdUtil.simpleUUID();
            Map<String, String> TokenUserMap = redisTemplate.opsForHash().entries("TokenUserMap");
            TokenUserMap.put(users.get(0).getId(), token);
            redisTemplate.opsForHash().putAll("TokenUserMap", TokenUserMap);

            respBean.setMsg("登录成功");
            respBean.setStatus(200);
            Map<String, Object> re = new HashMap<>();
            re.put("token", token);
            re.put("role", role);
            users.get(0).setPassword("");
            re.put("user", users.get(0));
            respBean.setData(re);
        } else {
            respBean.setMsg("登录失败，密码错误");
            respBean.setStatus(2000);
        }

        return respBean;
    }

    @Override
    public RespBean signup(String jsondata) {
        RespBean respBean = new RespBean();
        Map<String, String> CurrentUserMap = redisTemplate.opsForHash().entries("CurrentUserMap");
        Map<String, String> TokenUserMap = redisTemplate.opsForHash().entries("TokenUserMap");
        CurrentUserMap.remove(JSONUtil.parseObj(jsondata).getStr("userid"));
        TokenUserMap.remove(JSONUtil.parseObj(jsondata).getStr("userid"));
        redisTemplate.opsForHash().putAll("CurrentUserMap", CurrentUserMap);
        redisTemplate.opsForHash().putAll("TokenUserMap", TokenUserMap);

        respBean.setMsg("退出成功");
        respBean.setStatus(200);
        return respBean;
    }

    @Override
    public RespBean getRoles() {
        RespBean respBean = new RespBean();
        List<Role> list = userDao.selectRoles();
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.put("list", list);
        respBean.setMsg("查询成功");
        respBean.setStatus(200);
        respBean.setData(jsonObject);
        return respBean;
    }

    public void getDeptTreeids(String current_deptid, String parentid, Map<String, JSONObject> deptmaps) {
        if ("—".equals(parentid)) {
            TreeUtil.AllKeys.add(current_deptid);//结束条件
        } else {
            if (deptmaps.get(current_deptid) != null && deptmaps.get(current_deptid).containsKey("parentid")) {
                parentid = deptmaps.get(current_deptid).getStr("parentid");
                TreeUtil.AllKeys.add(parentid);
                getDeptTreeids(parentid, "", deptmaps);
            }
        }
    }
}
