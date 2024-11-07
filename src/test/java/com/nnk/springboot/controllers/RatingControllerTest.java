package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RatingEntity;
import com.nnk.springboot.service.RatingService;
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
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;



    @Test
    @DisplayName("GET /rating/list - success")
    @WithMockUser(username = "User", roles = "USER")
    public void home_shouldReturnRatingListView() throws Exception {

        when(ratingService.getAll()).thenReturn(List.of(mock(RatingEntity.class)));

        mockMvc.perform(get("/rating/list")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));

        verify(ratingService).getAll();
    }


    @Test
    @DisplayName("GET /rating/add - success")
    @WithMockUser(username = "User", roles = "USER")
    public void addRatingForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/rating/add")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }


    @Test
    @DisplayName("POST /rating/validate - success")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldRedirectToRatingList_whenValid() throws Exception {

        when(ratingService.save(any(RatingEntity.class))).thenReturn(mock(RatingEntity.class));

        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "Moodys RatingEntity")
                        .param("sandPRating", "Sand PRating")
                        .param("fitchRating", "Fitch RatingEntity")
                        .param("orderNumber", "10")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).save(any(RatingEntity.class));
    }


    @Test
    @DisplayName("POST /rating/validate - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

        verify(ratingService, never()).save(any(RatingEntity.class));
    }


    @Test
    @DisplayName("GET /rating/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(ratingService.getById(anyInt())).thenReturn(mock(RatingEntity.class));

        mockMvc.perform(get("/rating/update/" + 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("ratingEntity"));

        verify(ratingService).getById(anyInt());
    }


    @Test
    @DisplayName("POST /rating/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void updateRating_shouldRedirectToRatingList_whenValid() throws Exception {

        when(ratingService.update(anyInt(), any(RatingEntity.class))).thenReturn(mock(RatingEntity.class));

        mockMvc.perform(post("/rating/update/" + 1)
                        .param("moodysRating", "Updated Moodys RatingEntity")
                        .param("sandPRating", "Updated Sand PRating")
                        .param("fitchRating", "Updated Fitch RatingEntity")
                        .param("orderNumber", "20")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).update(anyInt(), any(RatingEntity.class));
    }


    @Test
    @DisplayName("POST /rating/update/{id} - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void updateRating_shouldReturnUpdateView_whenInvalid() throws Exception {

        mockMvc.perform(post("/rating/update/" + 1)
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));

        verify(ratingService, never()).update(anyInt(), any(RatingEntity.class));
    }


    @Test
    @DisplayName("GET /rating/delete/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void deleteRating_shouldRedirectToRatingList() throws Exception {

        doNothing().when(ratingService).delete(anyInt());

        mockMvc.perform(get("/rating/delete/" + 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).delete(anyInt());
    }
}