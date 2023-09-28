package com.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.exceptionH.UserNotFoundException;
import com.user.model.User;
import com.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@WebMvcTest(UserController.class)
class UserControllerTest {
    private static final String END_POINT_PATH = "/api/user";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService service;

    @Test
    public void testAddShouldReturn400BadRequest() throws Exception {
        User newUser = new User();

        String requestBody = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print())
        ;
    }

    @Test
    public void testAddShouldReturn201Created() throws Exception {
        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("Bob");
        newUser.setSurname("Jons");
        newUser.setEmail("bob@email.com");
        newUser.setAddress("Kyiv");
        newUser.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("1888-04-23"));
        newUser.setPhoneNumber("0405030201");

        // thanReturn(newUser.id(1L))
        Mockito.when(service.add(newUser)).thenReturn(newUser);

        String requestBody = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print())
        ;

    }

    @Test
    public void testGetShouldReturn404NotFound() throws Exception {
        Long userId = 123L;
        String requestURI = END_POINT_PATH + "/" + userId;

        Mockito.when(service.getById(userId)).thenThrow(UserNotFoundException.class);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testGetShouldReturn200OK() throws Exception {
        Long userId = 123L;
        String requestURI = END_POINT_PATH + "/" + userId;
        String email = "bob@email.com";

        User user = new User();
        user.setId(1L);
        user.setName("Bob");
        user.setSurname("Jons");
        user.setEmail("bob@email.com");
        user.setAddress("Kyiv");
        user.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("1888-04-23"));
        user.setPhoneNumber("0405030201");

        Mockito.when(service.getById(userId)).thenReturn(user);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.email", is(email)))
                .andDo(print());
    }

    @Test
    public void testListShouldReturn204NoContent() throws Exception {
        Mockito.when(service.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void testListShouldReturn200OK() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Bob");
        user1.setSurname("Jons");
        user1.setEmail("bob@email.com");
        user1.setAddress("Kyiv");
        user1.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("1998-04-23"));
        user1.setPhoneNumber("0405030201");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jack");
        user2.setSurname("Sparrow");
        user2.setEmail("jack@email.com");
        user2.setAddress("Odesa");
        user2.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("1999-04-23"));
        user2.setPhoneNumber("0405739281");

        List<User> listUser = List.of(user1, user2);

        Mockito.when(service.getAll()).thenReturn(listUser);

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].email", is("bob@email.com")))
                .andExpect(jsonPath("$[1].email", is("jack@email.com")))
                .andDo(print());
    }

    @Test
    public void testEditShouldReturn404NotFound() throws Exception {
        Long userId = 123L;
        String requestURI = END_POINT_PATH + "/" + userId;

        User user = new User();
        user.setName("Jack");
        user.setSurname("Sparrow");
        user.setEmail("jack@email.com");
        user.setAddress("Odesa");
        user.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("1999-04-23"));
        user.setPhoneNumber("0405739281");

        Mockito.when(service.edit(userId,user)).thenThrow(UserNotFoundException.class);

        String requestBody = objectMapper.writeValueAsString(user);

        mockMvc.perform(patch(requestURI).contentType("application/json").content(requestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testEditShouldReturn400BadRequest() throws Exception {
        User user = new User();
        user.setName("Jack");
        user.setSurname("Sparrow");
        user.setEmail("jackemail.com.///////");
        user.setAddress("Odesa");
        user.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-23"));
        user.setPhoneNumber("0405739281");

        Long userId = user.getId();
        String requestURI = END_POINT_PATH + "/" + userId;

        String requestBody = objectMapper.writeValueAsString(user);

        mockMvc.perform(patch(requestURI).contentType("application/json").content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testEditShouldReturn200OK() throws Exception {
        Long userId = 123L;
        String requestURI = END_POINT_PATH + "/" + userId;

        String email = "jack@email.com";

        User user = new User();
        user.setName("Jack");
        user.setSurname("Sparrow");
        user.setEmail("jack@email.com");
        user.setAddress("Odesa");
        user.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-23"));
        user.setPhoneNumber("0405739281");

        Mockito.when(service.edit(userId,user)).thenReturn(user);

        String requestBody = objectMapper.writeValueAsString(user);

        mockMvc.perform(patch(requestURI).contentType("application/json").content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(email)))
                .andDo(print());
    }

    @Test
    public void testDeleteShouldReturn404NotFound() throws Exception {
        Long userId = 123L;
        String requestURI = END_POINT_PATH + "/" + userId;

        Mockito.doThrow(UserNotFoundException.class).when(service).delete(userId);;

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testDeleteShouldReturn200OK() throws Exception {
        Long userId = 123L;
        String requestURI = END_POINT_PATH + "/" + userId;

        Mockito.doNothing().when(service).delete(userId);

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isOk())
                .andDo(print());
    }
}