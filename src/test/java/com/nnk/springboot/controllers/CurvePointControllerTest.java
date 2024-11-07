package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePointEntity;
import com.nnk.springboot.service.CurvePointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;


    @Test
    @DisplayName("GET /curvePoint/list - success")
    @WithMockUser(username = "User", roles = "USER")
    public void home_shouldReturnCurvePointListView() throws Exception {

        when(curvePointService.getAll()).thenReturn(List.of(mock(CurvePointEntity.class)));

        mockMvc.perform(get("/curvePoint/list")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));

        verify(curvePointService).getAll();
    }

    @Test
    @DisplayName("GET /curvePoint/add - success")
    @WithMockUser(username = "User", roles = "USER")
    public void addCurvePointForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/curvePoint/add")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @DisplayName("POST /curvePoint/validate - success")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldRedirectToCurvePointList_whenValid() throws Exception {

        when(curvePointService.save(any(CurvePointEntity.class))).thenReturn(mock(CurvePointEntity.class));

        mockMvc.perform(post("/curvePoint/validate")
                        .param("CurveId", "10")
                        .param("term", "10.0")
                        .param("value", "20.0")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).save(any(CurvePointEntity.class));
    }

    @Test
    @DisplayName("POST /curvePoint/validate - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("CurveId", "")
                        .param("term", "")
                        .param("value", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));

        verify(curvePointService, never()).save(any(CurvePointEntity.class));
    }

    @Test
    @DisplayName("GET /curvePoint/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(curvePointService.getById(anyInt())).thenReturn(mock(CurvePointEntity.class));

        mockMvc.perform(get("/curvePoint/update/" + 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePointEntity"));

        verify(curvePointService).getById(anyInt());
    }

    @Test
    @DisplayName("POST /curvePoint/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void updateCurvePoint_shouldRedirectToCurvePointList_whenValid() throws Exception {
        when(curvePointService.update(anyInt(), any(CurvePointEntity.class))).thenReturn(mock(CurvePointEntity.class));

        mockMvc.perform(post("/curvePoint/update/" + 1)
                        .param("CurveId", "10")
                        .param("term", "15.0")
                        .param("value", "25.0")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).update(anyInt(), any(CurvePointEntity.class));
    }

    @Test
    @DisplayName("POST /curvePoint/update/{id} - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void updateCurvePoint_shouldReturnUpdateView_whenInvalid() throws Exception {
        mockMvc.perform(post("/curvePoint/update/" + 1)
                        .param("CurveId", "")
                        .param("term", "")
                        .param("value", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));

        verify(curvePointService, never()).update(anyInt(), any(CurvePointEntity.class));
    }

    @Test
    @DisplayName("GET /curvePoint/delete/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void deleteCurvePoint_shouldRedirectToCurvePointList() throws Exception {
        doNothing().when(curvePointService).delete(anyInt());

        mockMvc.perform(get("/curvePoint/delete/" + 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).delete(anyInt());
    }
}