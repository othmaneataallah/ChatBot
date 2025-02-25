package me.oataallah.chatbot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
@CrossOrigin("*")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                )
                .defaultSystem("""
                        If you call the OwnerTools, do not add any irrelevant information.
                        """)
                .defaultTools(
                        new DateTimeTools(),
                        new OwnerTools()
                )
                .build();
    }

    @PostMapping("ollama")
    public String ollama(@RequestBody ChatRequest request) {
        String message = request.getMessage();
        return chatClient
                .prompt(message)
                .call()
                .content();
    }

    @PostMapping("ollama-stream")
    public Flux<String> ollamaStream(@RequestBody ChatRequest request) {
        String message = request.getMessage();
        return chatClient
                .prompt(message)
                .stream()
                .content();
    }

}
