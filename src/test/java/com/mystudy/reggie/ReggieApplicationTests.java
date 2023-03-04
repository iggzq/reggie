package com.mystudy.reggie;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mystudy.reggie.Util.MailUtil;
import com.mystudy.reggie.Util.MakeCode;
import com.mystudy.reggie.entity.Dish;
import com.mystudy.reggie.service.DishService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.tms.v20201229.TmsClient;
import com.tencentcloudapi.tms.v20201229.models.TextModerationRequest;
import com.tencentcloudapi.tms.v20201229.models.TextModerationResponse;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
class ReggieApplicationTests {

    @Autowired
    public DishService dishService;

    @Value("${tencentcloud.secretId}")
    public String secretId;

    @Value("${tencentcloud.secretKey}")
    public String secretKey;

    public JSONObject CheckUserTextByTentcent(String text) throws TencentCloudSDKException {
        Credential credential = new Credential(secretId, secretKey);
        TmsClient client = new TmsClient(credential, "ap-shanghai");
        TextModerationRequest textModerationRequest = new TextModerationRequest();
        String encryptionText = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        textModerationRequest.setContent(encryptionText);
        TextModerationResponse textModerationResponse = client.TextModeration(textModerationRequest);
        String s = TextModerationResponse.toJsonString(textModerationResponse);
        return JSON.parseObject(s);
    }

    @Test
    void contextLoads() throws TencentCloudSDKException {
//        LambdaUpdateWrapper<Dish> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//        LambdaQueryWrapper<>
//        int count = dishService.count();
//        int start = 0;
//        int pageSize = 2;
//        int currentPageSize = 2;
//        lambdaUpdateWrapper.eq(Dish::getCreateUser, 1);
//        lambdaUpdateWrapper.set(Dish::getIsDeleted, 1);
//        while (currentPageSize <= count + 100) {
//            String sql = "limit " + start + "," + pageSize;
//            lambdaUpdateWrapper.last(sql);
//            dishService.update(lambdaUpdateWrapper);
//            start = pageSize;
//            currentPageSize += 2;
//        }
        JSONObject jsonObject = CheckUserTextByTentcent("哈哈哈哈");
        String suggestion = (String) jsonObject.get("Suggestion");
        System.out.println(suggestion);


    }

    @Test
    public void testString() {
        String a = "abc";
        String b = "def";
        a.concat(b);
        System.out.println(a);
    }


}
//UPDATE tb_name SET column_name='test' WHERE id in (SELECT id FROM (SELECT * FROM tb_name ORDER BY id ASC LIMIT 20,10) AS tt);
