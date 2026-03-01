package com.infra.service.controller;

import com.infra.service.dto.RedisRequestDTO;
import com.infra.service.service.RedisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {
    private final RedisService redisService;

    public RedisController(RedisService redisService){
        this.redisService = redisService;
    }

    @PostMapping("/string")
    public ResponseEntity<?> saveKey(@RequestBody RedisRequestDTO dto){
        redisService.saveKey(
                dto.getModule(),
                dto.getKey(),
                dto.getValue(),
                dto.getTtl()
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/string")
    public ResponseEntity<?> get(@RequestParam String module,@RequestParam String key){
        return ResponseEntity.ok(redisService.get(module,key));
    }

    @DeleteMapping("/string")
    public ResponseEntity<?> delete(@RequestParam String module,@RequestParam String key){
        return ResponseEntity.ok(redisService.delete(module, key));
    }

    @GetMapping("/exist")
    public ResponseEntity<?> exist(@RequestParam String module,@RequestParam String key){
        return ResponseEntity.ok(redisService.exist(module, key));
    }
}
