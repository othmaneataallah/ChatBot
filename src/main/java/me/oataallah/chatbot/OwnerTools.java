package me.oataallah.chatbot;

import org.springframework.ai.tool.annotation.Tool;

public class OwnerTools {

    @Tool(description = "Get your owner's name.")
    String getOwnerName() {
        return "ITC (IT Community) made me. It is a scientific club at university of Blida 1 located in Algeria.";
    }

}
