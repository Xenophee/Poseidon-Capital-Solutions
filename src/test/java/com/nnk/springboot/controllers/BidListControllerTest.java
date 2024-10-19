package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
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
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;


    @Test
    @DisplayName("GET /bidList/list - success")
    public void home_shouldReturnBidListView() throws Exception {

        when(bidListService.getAll()).thenReturn(List.of(mock(BidList.class)));

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));

        verify(bidListService).getAll();
    }

    @Test
    @DisplayName("GET /bidList/add - success")
    public void addBidForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @DisplayName("POST /bidList/validate - success")
    public void validate_shouldRedirectToBidList_whenValid() throws Exception {

        when(bidListService.save(any(BidList.class))).thenReturn(mock(BidList.class));

        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("bidQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).save(any(BidList.class));
    }

    @Test
    @DisplayName("POST /bidList/validate - failure")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

        verify(bidListService, never()).save(any(BidList.class));
    }

    @Test
    @DisplayName("GET /bidList/update/{id} - success")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(bidListService.getById(anyInt())).thenReturn(mock(BidList.class));

        mockMvc.perform(get("/bidList/update/" + 1))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));

        verify(bidListService).getById(anyInt());
    }

    @Test
    @DisplayName("POST /bidList/update/{id} - success")
    public void updateBid_shouldRedirectToBidList_whenValid() throws Exception {
        when(bidListService.update(anyInt(), any(BidList.class))).thenReturn(mock(BidList.class));

        mockMvc.perform(post("/bidList/update/" + 1)
                        .param("account", "Updated Account")
                        .param("type", "Updated Type")
                        .param("bidQuantity", "20.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).update(anyInt(), any(BidList.class));
    }

    @Test
    @DisplayName("POST /bidList/update/{id} - failure")
    public void updateBid_shouldReturnUpdateView_whenInvalid() throws Exception {
        mockMvc.perform(post("/bidList/update/" + 1)
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));

        verify(bidListService, never()).update(anyInt(), any(BidList.class));
    }

    @Test
    @DisplayName("GET /bidList/delete/{id} - success")
    public void deleteBid_shouldRedirectToBidList() throws Exception {
        doNothing().when(bidListService).delete(anyInt());

        mockMvc.perform(get("/bidList/delete/" + 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).delete(anyInt());
    }
}