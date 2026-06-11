package com.laptoppstore.web;

import com.laptoppstore.entity.Category;
import com.laptoppstore.entity.User;
import com.laptoppstore.repository.CategoryRepository;
import com.laptoppstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AdminCrudIntegrationTest {

        private MockMvc mockMvc;

        @Autowired
        private WebApplicationContext wac;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

        @org.junit.jupiter.api.BeforeEach
        public void setup() {
                this.mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders
                                .webAppContextSetup(this.wac)
                                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                                .build();
        }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void category_crud_flow() throws Exception {
        // list page
        mockMvc.perform(get("/admin/categories")).andExpect(status().isOk());

        // add
        mockMvc.perform(post("/admin/categories/add").param("name", "TCat").with(csrf()))
                .andExpect(status().is3xxRedirection());

        assertThat(categoryRepository.findAll()).extracting(Category::getName).contains("TCat");

        Category c = categoryRepository.findAll().stream().filter(x->"TCat".equals(x.getName())).findFirst().get();

        // edit
        mockMvc.perform(post("/admin/categories/edit/"+c.getId()).param("name","TCatEdited").with(csrf()))
                .andExpect(status().is3xxRedirection());

        assertThat(categoryRepository.findById(c.getId()).get().getName()).isEqualTo("TCatEdited");

        // delete
        mockMvc.perform(post("/admin/categories/delete/"+c.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection());

        assertThat(categoryRepository.findById(c.getId())).isEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void user_crud_flow() throws Exception {
        mockMvc.perform(get("/admin/users")).andExpect(status().isOk());

        mockMvc.perform(post("/admin/users/add").param("email","tuser@example.com").param("password","pass").param("fullName","T User").param("address","Addr").with(csrf()))
                .andExpect(status().is3xxRedirection());

        assertThat(userRepository.findAll()).extracting(User::getEmail).contains("tuser@example.com");

        User u = userRepository.findAll().stream().filter(x->"tuser@example.com".equals(x.getEmail())).findFirst().get();

        mockMvc.perform(post("/admin/users/edit/"+u.getId()).param("email","tuser2@example.com").param("password","pass2").param("fullName","T User 2").param("address","Addr2").with(csrf()))
                .andExpect(status().is3xxRedirection());

        assertThat(userRepository.findById(u.getId()).get().getEmail()).isEqualTo("tuser2@example.com");
        assertThat(userRepository.findById(u.getId())).isEmpty();
    }
}
