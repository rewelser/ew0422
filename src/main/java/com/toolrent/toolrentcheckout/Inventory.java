package com.toolrent.toolrentcheckout;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Inventory {
    HashMap<String, Tool> toolsInventory = new HashMap<>();

    public Inventory() throws IOException {
        InputStream in = CheckoutApplication.class.getResourceAsStream("ToolInventory.csv");
        if (in != null) {
            InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            try {
                Stream<String> lines = reader.lines().skip(1);
                lines.forEachOrdered(line -> {
                    List<String> items = new ArrayList<>(Arrays.asList(line.split("\\s*,\\s*")));
                    toolsInventory.put(items.get(0), new Tool(items.get(0), items.get(1),
                            new ToolType(items.get(2), items.get(3), Boolean.parseBoolean(items.get(4)), Boolean.parseBoolean(items.get(5)), Boolean.parseBoolean(items.get(6)))));
                });
            } catch (Exception e) {
                System.err.println("Inventory file read error: " + e);
            } finally {
                in.close();
            }
        } else {
            throw new FileNotFoundException("Inventory file was not found.");
        }
    }

    public HashMap<String, Tool> getTools() {
        return this.toolsInventory;
    }
}