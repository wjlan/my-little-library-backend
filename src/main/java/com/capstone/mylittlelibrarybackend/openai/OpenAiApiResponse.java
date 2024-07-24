package com.capstone.mylittlelibrarybackend.openai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiApiResponse {

    private List<Choices> choices;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choices {
        private int index;
        private Message message;
    }
}
