package com.promo.gmall.manager.acl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.promo.gmall.constants.CacheConstants;
import com.promo.gmall.domain.AclUserDO;
import com.promo.gmall.mapper.AclUserMapper;
import com.promo.gmall.mapper.AclUserRoleMapper;
import com.promo.gmall.model.acl.AclUserBO;
import com.promo.gmall.redis.CacheHelper;
import com.promo.gmall.utils.taobao.request.StateCodeQueryRequest;
import com.promo.gmall.utils.taobao.request.StateQueryRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Component
public class AclUserManager {

    @Resource
    private CacheHelper cacheHelper;

    @Resource
    private AclUserMapper aclUserMapper;

    @Resource
    private AclUserRoleMapper aclUserRoleMapper;


    public AclUserBO getByUsername(String username) {
        return cacheHelper.getOrCreate(username, () -> {

            AclUserDO user = aclUserMapper.getByUsername(username);


            return AclUserBO.from(user);
        }, CacheConstants.ONE_HOURS);
    }


    public static void main(String[] args) {

        // token + '&' + timestamp + "&" + appKey + "&" + params
        // 2acbdd330d22c1dfdb980ccecf079f24_1615370799236
        String token = "2acbdd330d22c1dfdb980ccecf079f24";
        StateQueryRequest request = new StateQueryRequest();
        request.setPid("228");
        Gson gson = new GsonBuilder().create();
        System.out.println("param: " + gson.toJson(request));

        String param = gson.toJson(request);
        String req = token + "&" + "1615369113718" + "&" + "12574478" + "&" + param;

        String sign = DigestUtils.md5Hex(req);
        System.out.println(sign);

        System.out.println(System.currentTimeMillis());
        // 7a81864b576a48dadb0a0b3ba6a93ee3

        test();
    }


    private static void test() {

        // 89b0fa747ea55b1f9ebc8aa80e9980c2

        Gson gson = new GsonBuilder().create();
        StateCodeQueryRequest codeQueryRequest = new StateCodeQueryRequest();
        codeQueryRequest.setSearchName("Acmar");
        String data = gson.toJson(codeQueryRequest);

        // {"language":"en","maxRegion":100,"maxPc":100,"returnPostcode":true,"iso":"US","searchName":"Abbeville"}
        // {"language":"en","maxRegion":100,"maxPc":100,"returnPostcode":true,"iso":"US","searchName":"Abbeville"}


        System.out.println("data:" + data);
        System.out.println("sign:" + getSign(data));
    }

    private static String getSign(String data) {
        String req = "ef5d4b2a65157489276adfd7b8b526e7" + "&" + "1615373673032" + "&" + "27769795" + "&" + data;
        return DigestUtils.md5Hex(req);
    }

}
