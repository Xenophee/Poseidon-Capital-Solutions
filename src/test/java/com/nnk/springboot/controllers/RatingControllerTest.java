package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
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
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;



    @Test
    @DisplayName("GET /rating/list - success")
    public void home_shouldReturnRatingListView() throws Exception {

        when(ratingService.getAll()).thenReturn(List.of(mock(Rating.class)));

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));

        verify(ratingService).getAll();
    }


    @Test
    @DisplayName("GET /rating/add - success")
    public void addRatingForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }


    @Test
    @DisplayName("POST /rating/validate - success")
    public void validate_shouldRedirectToRatingList_whenValid() throws Exception {

        when(ratingService.save(any(Rating.class))).thenReturn(mock(Rating.class));

        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "Moodys Rating")
                        .param("sandPRating", "Sand PRating")
                        .param("fitchRating", "Fitch Rating")
                        .param("orderNumber", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).save(any(Rating.class));
    }


    @Test
    @DisplayName("POST /rating/validate - failure")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

        verify(ratingService, never()).save(any(Rating.class));
    }


    @Test
    @DisplayName("GET /rating/update/{id} - success")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(ratingService.getById(anyInt())).thenReturn(mock(Rating.class));

        mockMvc.perform(get("/rating/update/" + 1))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"));

        verify(ratingService).getById(anyInt());
    }


    @Test
    @DisplayName("POST /rating/update/{id} - success")
    public void updateRating_shouldRedirectToRatingList_whenValid() throws Exception {

        when(ratingService.update(anyInt(), any(Rating.class))).thenReturn(mock(Rating.class));

        mockMvc.perform(post("/rating/update/" + 1)
                        .param("moodysRating", "Updated Moodys Rating")
                        .param("sandPRating", "Updated Sand PRating")
                        .param("fitchRating", "Updated Fitch Rating")
                        .param("orderNumber", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).update(anyInt(), any(Rating.class));
    }


    @Test
    @DisplayName("POST /rating/update/{id} - failure")
    public void updateRating_shouldReturnUpdateView_whenInvalid() throws Exception {

        mockMvc.perform(post("/rating/update/" + 1)
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));

        verify(ratingService, never()).update(anyInt(), any(Rating.class));
    }


    @Test
    @DisplayName("GET /rating/delete/{id} - success")
    public void deleteRating_shouldRedirectToRatingList() throws Exception {

        doNothing().when(ratingService).delete(anyInt());

        mockMvc.perform(get("/rating/delete/" + 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).delete(anyInt());
    }
}