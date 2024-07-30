package com.capstone.mylittlelibrarybackend.openai;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class OpenAiApiTestUnit {

    @InjectMocks
    private OpenAiApiController openAiApiController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testChatWithEmptyPrompt() {
        // Act
        String result = openAiApiController.chat("");

        // Assert
        assertThat(result).isEqualTo("Prompt is required");
    }
}


