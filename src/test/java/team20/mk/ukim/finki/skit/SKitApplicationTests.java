package team20.mk.ukim.finki.skit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import team20.mk.ukim.finki.skit.model.Category;
import team20.mk.ukim.finki.skit.model.Item;
import team20.mk.ukim.finki.skit.model.Subject;
import team20.mk.ukim.finki.skit.model.enumerations.Role;
import team20.mk.ukim.finki.skit.service.CategoryService;
import team20.mk.ukim.finki.skit.service.ItemService;
import team20.mk.ukim.finki.skit.service.SubjectService;
import team20.mk.ukim.finki.skit.service.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SKitApplicationTests {
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ItemService itemService;

    @Autowired
    SubjectService subjectService;

    private static Category categoryFirst;
    private static Subject subjectFirst;
    private static boolean dataInitialized = false;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        initData();
    }

    private void initData() {
        if (!dataInitialized) {
            categoryFirst = categoryService.create("CategoryOne");
            categoryService.create("CategoryTwo");

            subjectFirst = subjectService.save("SubjectOne").get();
            subjectService.save("SubjectTwo");

            String user = "user";
            String admin = "admin";

            userService.register(user, user, user, user, user, Role.ROLE_USER);
            userService.register(admin, admin, admin, admin, admin, Role.ROLE_ADMIN);
            dataInitialized = true;
        }
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetItems() throws Exception {
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/items");
        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("items"))
                .andExpect(MockMvcResultMatchers.model().attribute("bodyContent", "items"))
                .andExpect(MockMvcResultMatchers.view().name("master-template"));

    }

    @Test
    public void testDeleteItem() throws Exception {
        Item product = this.itemService.save("test", 200L, 20.0, 2, categoryFirst.getId(), subjectFirst.getId(), "test").get();
        MockHttpServletRequestBuilder productDeleteRequest = MockMvcRequestBuilders
                .delete("/items/delete/" + product.getId());
        this.mockMvc.perform(productDeleteRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/items"));
    }
}
