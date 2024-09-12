package com.purityvanilla.pvlib.config;

import com.google.gson.Gson;
import io.leangen.geantyref.TypeToken;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Messages {
    private final String filepath;
    protected HashMap<String, Object> messageMap;
    
    public Messages(ConfigFile config, String filepath) {
        this.filepath = filepath;
        File file = new File(filepath);
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            try {
                InputStream defaultStream = config.getClass().getResourceAsStream("/messages.json");
                Files.copy(defaultStream, Paths.get(file.toURI()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        Gson gson = new Gson();
        Reader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        messageMap = gson.fromJson(reader, new TypeToken<HashMap<String, Object>>(){}.getType());
    }

    public Component getMessage(String messageKey) {
        Object value = messageMap.get(messageKey);
        // If JSON at that value is list, each element is sent as a line of a single message
        if (value instanceof ArrayList) {
            ArrayList<String> messageList = (ArrayList<String>) value;
            TextComponent.Builder message = Component.text();
            for (int i = 0; i < messageList.size(); i++) {
                message.append(MiniMessage.miniMessage().deserialize(messageList.get(i)));
                if (i < messageList.size() - 1) { // Append newline if not final line in message
                    message.appendNewline();
                }
            }
            return message.build();
        }

        // Otherwise, deserialise element as single String and return
        String rawMessage = (String) messageMap.get(messageKey);
        Component message = MiniMessage.miniMessage().deserialize(rawMessage);
        return message;
    }
}
