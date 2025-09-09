package controller;

import org.junit.jupiter.api.Test;
import org.lokhit.gamelove.GameLoveServiceApplication;
import org.lokhit.gamelove.controller.LoveController;
import org.lokhit.gamelove.service.LoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LoveController.class)
@ContextConfiguration(classes = GameLoveServiceApplication.class)
public class LoveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoveService loveService;

    @Test
    void shouldLoveGame() throws Exception {
        String json = """
                    {
                      "username": "mateusz",
                      "gameName": "Minecraft"
                    }
                """;

        mockMvc.perform(post("/love")
                        .with(user("mateusz").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUnloveGame() throws Exception {
        String json = """
        {
          "username": "mateusz",
          "gameName": "Minecraft"
        }
    """;

        doNothing().when(loveService).unloveGame("mateusz", "Minecraft");

        mockMvc.perform(delete("/love")
                        .with(user("mateusz").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());
    }
}
