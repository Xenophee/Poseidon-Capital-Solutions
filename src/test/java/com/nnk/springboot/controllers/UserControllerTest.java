package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("GET /user/list - success")
    public void home_shouldReturnUserListView() throws Exception {
        when(userService.getAll()).thenReturn(List.of(mock(User.class)));

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));

        verify(userService).getAll();
    }

    @Test
    @DisplayName("GET /user/add - success")
    public void addUserForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @DisplayName("POST /user/validate - success")
    public void validate_shouldRedirectToUserList_whenValid() throws Exception {
        when(userService.save(any(User.class))).thenReturn(mock(User.class));

        mockMvc.perform(post("/user/validate")
                        .param("username", "Username")
                        .param("password", "Password")
                        .param("fullname", "Full Name")
                        .param("role", "Role"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).save(any(User.class));
    }

    @Test
    @DisplayName("POST /user/validate - failure")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "")
                        .param("role", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));

        verify(userService, never()).save(any(User.class));
    }

    @Test
    @DisplayName("GET /user/update/{id} - success")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(userService.getById(anyInt())).thenReturn(mock(User.class));

        mockMvc.perform(get("/user/update/" + 1))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));

        verify(userService).getById(anyInt());
    }

    @Test
    @DisplayName("POST /user/update/{id} - success")
    public void updateUser_shouldRedirectToUserList_whenValid() throws Exception {
        when(userService.save(any(User.class))).thenReturn(mock(User.class));

        mockMvc.perform(post("/user/update/" + 1)
                        .param("username", "Updated Username")
                        .param("password", "Updated Password")
                        .param("fullname", "Updated Full Name")
                        .param("role", "Updated Role"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).save(any(User.class));
    }

    @Test
    @DisplayName("POST /user/update/{id} - failure")
    public void updateUser_shouldReturnUpdateView_whenInvalid() throws Exception {
        mockMvc.perform(post("/user/update/" + 1)
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "")
                        .param("role", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));

        verify(userService, never()).save(any(User.class));
    }

    @Test
    @DisplayName("GET /user/delete/{id} - success")
    public void deleteUser_shouldRedirectToUserList() throws Exception {
        doNothing().when(userService).delete(anyInt());

        mockMvc.perform(get("/user/delete/" + 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).delete(anyInt());
    }
}