package com.spring.redis.http.controller;

import com.spring.redis.entity.ClienteRedis;
import com.spring.redis.repository.ClienteREdisRepository;
import com.spring.redis.service.ClienteRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/cliente")
@RequiredArgsConstructor
public class ClienteControllerV2 {

    private final ClienteRedisService clienteRedisService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteRedis save(@RequestBody ClienteRedis clienteRedis){
        return clienteRedisService.save(clienteRedis);

    }

    @GetMapping
    public List<ClienteRedis> findAll(){
        return clienteRedisService.findAll();

    }
}
