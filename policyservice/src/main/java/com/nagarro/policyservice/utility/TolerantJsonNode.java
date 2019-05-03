package com.nagarro.policyservice.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;

import java.io.IOException;

public final class TolerantJsonNode {

    private final JsonNode jsonNode;

    public TolerantJsonNode(final String json){
        try {
            jsonNode = getObjectMapper().reader().readTree(json);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid json: "+json, e);
        }
    }

    public String asStringIfPresent(final String fieldName) {
        return asText(jsonNode.get(fieldName));
    }


    private static com.fasterxml.jackson.databind.ObjectMapper getObjectMapper() {
        final com.fasterxml.jackson.databind.ObjectMapper objectMapper =
                new com.fasterxml.jackson.databind.ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    private static String asText(final TreeNode treeNode) {
        if (treeNode instanceof NullNode) {
            return null;
        }
        return treeNode instanceof JsonNode ? ((JsonNode) treeNode).asText() : null;
    }
}
