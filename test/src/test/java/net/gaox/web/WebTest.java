package net.gaox.web;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.gaox.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;


/**
 * <p> 接口测试 </p>
 *
 * @author gaox·Eric
 * @date 2023-04-05 20:37
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getMethod() throws Exception {

        String httpUrl = "/get";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(httpUrl).param("name", "gaox");
        ResultActions perform = mockMvc.perform(requestBuilder);
        // 对返回值进行断言
        perform.andExpect(MockMvcResultMatchers.status().isOk());
        // 打印输出发出请求的详细信息
        perform.andDo(MockMvcResultHandlers.print());
        // 获取方法的返回值
        String contentAsString = perform.andReturn().getResponse().getContentAsString();

        log.info("perform response content: [{}]", contentAsString);
    }

    @Test
    public void postMethod() throws Exception {

        User user = new User();
        user.setId(1L).setName("gaox");

        String httpUrl = "/post";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(httpUrl).contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(user));

        ResultActions perform = mockMvc.perform(requestBuilder);
        // 对返回值进行断言
        perform.andExpect(MockMvcResultMatchers.status().isOk());
        // 打印输出发出请求的详细信息
        perform.andDo(MockMvcResultHandlers.print());
        //使用Json path验证JSON
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));

        // 获取方法的返回值
        MvcResult mvcResult = perform.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        log.info("perform response content: [{}]", contentAsString);
    }

    @Test
    public void file() throws Exception {

        String httpUrl = "/file";

        File file = new File("/user/admin/file/readme.md");
        FileInputStream fileInputStream = new FileInputStream(file);

        // controller 方法指定的文件接收对象的值
        String param = "file";
        MockMultipartFile multipartFile = new MockMultipartFile(param, file.getName(), null, fileInputStream);
        String name = multipartFile.getName();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(httpUrl).file(multipartFile);


        ResultActions perform = mockMvc.perform(requestBuilder);
        // 对返回值进行断言
        perform.andExpect(MockMvcResultMatchers.status().isOk());
        // 打印输出发出请求的详细信息
        perform.andDo(MockMvcResultHandlers.print());

        // 获取方法的返回值
        MvcResult mvcResult = perform.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        log.info("perform response content: [{}]", contentAsString);
    }

}
