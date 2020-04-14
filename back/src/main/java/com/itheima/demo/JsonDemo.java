package com.itheima.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.Cart;

public class JsonDemo {
    public static void main(String[] args) throws JsonProcessingException {
        Cart cart = new Cart();
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(cart);
        System.out.println(s);

    }
}
