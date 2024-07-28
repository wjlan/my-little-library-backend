package com.capstone.mylittlelibrarybackend.openai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class OpenAiApiController {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    @Autowired
    private RestTemplate template;

    @GetMapping(path = "/chat")
    public String chat(@RequestParam(value = "prompt", defaultValue = "") String prompt){
        if (prompt.isEmpty()) {
            return "Prompt is required";
        }
        OpenAiApiRequest request = new OpenAiApiRequest(model, prompt);
        OpenAiApiResponse openAiApiResponse = template.postForObject(openAiApiUrl, request, OpenAiApiResponse.class);
        return openAiApiResponse.getChoices().get(0).getMessage().getContent();
    }
}
