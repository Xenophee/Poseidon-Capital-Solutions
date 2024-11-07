package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleNameEntity;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @Test
    @DisplayName("GET /ruleName/list - success")
    @WithMockUser(username = "User", roles = "USER")
    public void home_shouldReturnRuleNameListView() throws Exception {
        when(ruleNameService.getAll()).thenReturn(List.of(mock(RuleNameEntity.class)));

        mockMvc.perform(get("/ruleName/list")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"));

        verify(ruleNameService).getAll();
    }

    @Test
    @DisplayName("GET /ruleName/add - success")
    @WithMockUser(username = "User", roles = "USER")
    public void addRuleForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/ruleName/add")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @DisplayName("POST /ruleName/validate - success")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldRedirectToRuleNameList_whenValid() throws Exception {
        when(ruleNameService.save(any(RuleNameEntity.class))).thenReturn(mock(RuleNameEntity.class));

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "Rule Name")
                        .param("description", "Description")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SQL String")
                        .param("sqlPart", "SQL Part")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).save(any(RuleNameEntity.class));
    }

    @Test
    @DisplayName("POST /ruleName/validate - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));

        verify(ruleNameService, never()).save(any(RuleNameEntity.class));
    }

    @Test
    @DisplayName("GET /ruleName/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(ruleNameService.getById(anyInt())).thenReturn(mock(RuleNameEntity.class));

        mockMvc.perform(get("/ruleName/update/" + 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleNameEntity"));

        verify(ruleNameService).getById(anyInt());
    }

    @Test
    @DisplayName("POST /ruleName/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void updateRuleName_shouldRedirectToRuleNameList_whenValid() throws Exception {
        when(ruleNameService.update(anyInt(), any(RuleNameEntity.class))).thenReturn(mock(RuleNameEntity.class));

        mockMvc.perform(post("/ruleName/update/" + 1)
                        .param("name", "Updated Rule Name")
                        .param("description", "Updated Description")
                        .param("json", "Updated Json")
                        .param("template", "Updated Template")
                        .param("sqlStr", "Updated SQL String")
                        .param("sqlPart", "Updated SQL Part")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).update(anyInt(), any(RuleNameEntity.class));
    }

    @Test
    @DisplayName("POST /ruleName/update/{id} - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void updateRuleName_shouldReturnUpdateView_whenInvalid() throws Exception {
        mockMvc.perform(post("/ruleName/update/" + 1)
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));

        verify(ruleNameService, never()).update(anyInt(), any(RuleNameEntity.class));
    }

    @Test
    @DisplayName("GET /ruleName/delete/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void deleteRuleName_shouldRedirectToRuleNameList() throws Exception {
        doNothing().when(ruleNameService).delete(anyInt());

        mockMvc.perform(get("/ruleName/delete/" + 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).delete(anyInt());
    }
}