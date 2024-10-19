package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Test
    @DisplayName("GET /trade/list - success")
    public void home_shouldReturnTradeListView() throws Exception {
        when(tradeService.getAll()).thenReturn(List.of(mock(Trade.class)));

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));

        verify(tradeService).getAll();
    }

    @Test
    @DisplayName("GET /trade/add - success")
    public void addTradeForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @DisplayName("POST /trade/validate - success")
    public void validate_shouldRedirectToTradeList_whenValid() throws Exception {
        when(tradeService.save(any(Trade.class))).thenReturn(mock(Trade.class));

        mockMvc.perform(post("/trade/validate")
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("buyQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).save(any(Trade.class));
    }

    @Test
    @DisplayName("POST /trade/validate - failure")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "")
                        .param("type", "")
                        .param("buyQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));

        verify(tradeService, never()).save(any(Trade.class));
    }

    @Test
    @DisplayName("GET /trade/update/{id} - success")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(tradeService.getById(anyInt())).thenReturn(mock(Trade.class));

        mockMvc.perform(get("/trade/update/" + 1))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));

        verify(tradeService).getById(anyInt());
    }

    @Test
    @DisplayName("POST /trade/update/{id} - success")
    public void updateTrade_shouldRedirectToTradeList_whenValid() throws Exception {
        when(tradeService.update(anyInt(), any(Trade.class))).thenReturn(mock(Trade.class));

        mockMvc.perform(post("/trade/update/" + 1)
                        .param("account", "Updated Account")
                        .param("type", "Updated Type")
                        .param("buyQuantity", "20.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).update(anyInt(), any(Trade.class));
    }

    @Test
    @DisplayName("POST /trade/update/{id} - failure")
    public void updateTrade_shouldReturnUpdateView_whenInvalid() throws Exception {
        mockMvc.perform(post("/trade/update/" + 1)
                        .param("account", "")
                        .param("type", "")
                        .param("buyQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));

        verify(tradeService, never()).update(anyInt(), any(Trade.class));
    }

    @Test
    @DisplayName("GET /trade/delete/{id} - success")
    public void deleteTrade_shouldRedirectToTradeList() throws Exception {
        doNothing().when(tradeService).delete(anyInt());

        mockMvc.perform(get("/trade/delete/" + 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).delete(anyInt());
    }
}