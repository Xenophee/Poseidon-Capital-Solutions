package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @Test
    @DisplayName("GET /ruleName/list - success")
    public void home_shouldReturnRuleNameListView() throws Exception {
        when(ruleNameService.getAll()).thenReturn(List.of(mock(RuleName.class)));

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"));

        verify(ruleNameService).getAll();
    }

    @Test
    @DisplayName("GET /ruleName/add - success")
    public void addRuleForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @DisplayName("POST /ruleName/validate - success")
    public void validate_shouldRedirectToRuleNameList_whenValid() throws Exception {
        when(ruleNameService.save(any(RuleName.class))).thenReturn(mock(RuleName.class));

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "Rule Name")
                        .param("description", "Description")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SQL String")
                        .param("sqlPart", "SQL Part"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).save(any(RuleName.class));
    }

    @Test
    @DisplayName("POST /ruleName/validate - failure")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));

        verify(ruleNameService, never()).save(any(RuleName.class));
    }

    @Test
    @DisplayName("GET /ruleName/update/{id} - success")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(ruleNameService.getById(anyInt())).thenReturn(mock(RuleName.class));

        mockMvc.perform(get("/ruleName/update/" + 1))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"));

        verify(ruleNameService).getById(anyInt());
    }

    @Test
    @DisplayName("POST /ruleName/update/{id} - success")
    public void updateRuleName_shouldRedirectToRuleNameList_whenValid() throws Exception {
        when(ruleNameService.update(anyInt(), any(RuleName.class))).thenReturn(mock(RuleName.class));

        mockMvc.perform(post("/ruleName/update/" + 1)
                        .param("name", "Updated Rule Name")
                        .param("description", "Updated Description")
                        .param("json", "Updated Json")
                        .param("template", "Updated Template")
                        .param("sqlStr", "Updated SQL String")
                        .param("sqlPart", "Updated SQL Part"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).update(anyInt(), any(RuleName.class));
    }

    @Test
    @DisplayName("POST /ruleName/update/{id} - failure")
    public void updateRuleName_shouldReturnUpdateView_whenInvalid() throws Exception {
        mockMvc.perform(post("/ruleName/update/" + 1)
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));

        verify(ruleNameService, never()).update(anyInt(), any(RuleName.class));
    }

    @Test
    @DisplayName("GET /ruleName/delete/{id} - success")
    public void deleteRuleName_shouldRedirectToRuleNameList() throws Exception {
        doNothing().when(ruleNameService).delete(anyInt());

        mockMvc.perform(get("/ruleName/delete/" + 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).delete(anyInt());
    }
}