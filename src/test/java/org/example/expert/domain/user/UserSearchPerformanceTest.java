package org.example.expert.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserSearchPerformanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser  // Spring Security 인증 우회
    void nicknameSearch_PerformanceTest() throws Exception {
        String targetNickname = "2r5ox2";
        long start = System.currentTimeMillis();

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .param("page", "1")
                .param("size", "10")
                .param("nickname", targetNickname))
                .andExpect(status().isOk());

        long end = System.currentTimeMillis();

        System.out.println("Nickname Search Time: " + (end - start) + " ms");
    }
}
