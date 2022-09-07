package com.spring.redis.service;

import com.spring.redis.entity.Cliente;
import com.spring.redis.entity.ClienteRedis;
import com.spring.redis.repository.ClienteREdisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@EnableScheduling
public class ClienteRedisService {

    private final int MINUTO = 1000 * 60;

    private  final long MINUTOS = MINUTO * 1;
    private final ClienteREdisRepository clienteREdisRepository;

    private final ModelMapper modelMapper;

    private final ClienteService clienteService;

    public ClienteRedis save(ClienteRedis clienteRedis){
        return clienteREdisRepository.save(clienteRedis);

    }

    public List<ClienteRedis> findAll(){
        return (List<ClienteRedis>) clienteREdisRepository.findAll();

    }

    @Scheduled(fixedDelay = MINUTOS)
    public void sincronizarClienteBancoDeDados(){
        //obter lista do banco
        List<ClienteRedis> clienteRedisList = findAll();
        if(CollectionUtils.isEmpty(clienteRedisList)){
            log.info("lista nula ou vazia");
        } else{
            //salvar a lista no banco relacional
            List<Cliente> clienteList = new ArrayList<>();
            clienteRedisList.stream().forEach(
                    clienteRedis -> {
                        log.info(clienteRedis);
                        Cliente cliente = modelMapper.map(clienteRedis, Cliente.class);
                        clienteList.add(cliente);

                    });
            clienteService.saveAll(clienteList);
            //remover do banco redis
            clienteREdisRepository.deleteAll(clienteRedisList);
        }



    }
}
