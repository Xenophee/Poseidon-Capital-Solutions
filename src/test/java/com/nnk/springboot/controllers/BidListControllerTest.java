package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidListEntity;
import com.nnk.springboot.service.BidListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import org.springframework.security.test.context.support.WithMockUser;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;


    @Test
    @DisplayName("GET /bidList/list - success")
    @WithMockUser(username = "User", roles = "USER")
    public void home_shouldReturnBidListView() throws Exception {

        when(bidListService.getAll()).thenReturn(List.of(mock(BidListEntity.class)));

        mockMvc.perform(get("/bidList/list")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));

        verify(bidListService).getAll();
    }

    @Test
    @DisplayName("GET /bidList/add - success")
    @WithMockUser(username = "User", roles = "USER")
    public void addBidForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/bidList/add")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @DisplayName("POST /bidList/validate - success")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldRedirectToBidList_whenValid() throws Exception {

        when(bidListService.save(any(BidListEntity.class))).thenReturn(mock(BidListEntity.class));

        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("bidQuantity", "10.0")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).save(any(BidListEntity.class));
    }

    @Test
    @DisplayName("POST /bidList/validate - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

        verify(bidListService, never()).save(any(BidListEntity.class));
    }

    @Test
    @DisplayName("GET /bidList/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(bidListService.getById(anyInt())).thenReturn(mock(BidListEntity.class));

        mockMvc.perform(get("/bidList/update/" + 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidListEntity"));

        verify(bidListService).getById(anyInt());
    }

    @Test
    @DisplayName("POST /bidList/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void updateBid_shouldRedirectToBidList_whenValid() throws Exception {
        when(bidListService.update(anyInt(), any(BidListEntity.class))).thenReturn(mock(BidListEntity.class));

        mockMvc.perform(post("/bidList/update/" + 1)
                        .param("account", "Updated Account")
                        .param("type", "Updated Type")
                        .param("bidQuantity", "20.0")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).update(anyInt(), any(BidListEntity.class));
    }

    @Test
    @DisplayName("POST /bidList/update/{id} - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void updateBid_shouldReturnUpdateView_whenInvalid() throws Exception {
        mockMvc.perform(post("/bidList/update/" + 1)
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));

        verify(bidListService, never()).update(anyInt(), any(BidListEntity.class));
    }

    @Test
    @DisplayName("GET /bidList/delete/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void deleteBid_shouldRedirectToBidList() throws Exception {
        doNothing().when(bidListService).delete(anyInt());

        mockMvc.perform(get("/bidList/delete/" + 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).delete(anyInt());
    }
}