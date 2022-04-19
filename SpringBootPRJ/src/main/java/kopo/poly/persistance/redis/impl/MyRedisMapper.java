package kopo.poly.persistance.redis.impl;

import kopo.poly.dto.RedisDTO;
import kopo.poly.persistance.redis.IMyRedisMapper;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component("MyRedisMapper")
public class MyRedisMapper implements IMyRedisMapper {

    // kopo.poly.config RedisConfiguration에서 설정한 RedisDB 객체 사용
    public final RedisTemplate<String, Object> redisDB;

    public MyRedisMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;
    }

    @Override
    public int saveRedisString(String redisKey, RedisDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".saveRedisString Start!");

        int res = 0;

        String saveData = CmmUtil.nvl(pDTO.getTest_text()); // 저장할 값

        /*
         * redis 저장 및 읽기에 대한 데이터 타입 지정(String 타입으로 지정함)
         * RedisDB에 데이터 저장하기 전에 반드시 저장될 데이터 타입을 사용한 타입과 동일하게 지정해주어야 한다.
         */
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new StringRedisSerializer()); // String 타입

        if (!redisDB.hasKey(redisKey)) { // 데이터가 존재하지 않으면 저장하기

            // 데이터 저장하기
            redisDB.opsForValue().set(redisKey, saveData);

            // RedisDB에 저장되는 데이터의 유효시간 설정(TTL;Time To Live 설정)
            // 2일이 지나면, 자동으로 데이터가 삭제되도록 설정함
            redisDB.expire(redisKey, 2, TimeUnit.DAYS);

            log.info("Save Data!!");

            res = 1;
        }

        log.info(this.getClass().getName() + ".saveRedisString End!");

        return res;
    }

    @Override
    public RedisDTO getRedisString(String redisKey) throws Exception {

        log.info(this.getClass().getName() + ".getRedisString Start!");

        log.info("String redisKey : " + redisKey);
        RedisDTO rDTO = new RedisDTO();

        /*
         * redis 저장 및 읽기에 대한 데이터 타입 지정(String 타입으로 지정함)
         * RedisDB에 데이터 저장하기 전에 반드시 저장될 데이터 타입을 사용한 타입과 동일하게 지정해주어야 한다.
         */
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new StringRedisSerializer()); // String 타입

        if (redisDB.hasKey(redisKey)) {

            String res = (String) redisDB.opsForValue().get(redisKey);

            log.info("res : " + res);

            // RedisDB에 저장된 데이터를 DTO에 저장하기
            rDTO.setTest_text(res);
        }

        log.info(this.getClass().getName() + ".getRedisString End!");

        return rDTO;
    }

    @Override
    public int saveRedisStringJSON(String redisKey, RedisDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".saveRedisStringJSON Start!");

        int res = 0;

        // RedisDB의 키 데이터 타입을 String으로 정의(항상 String으로 설정함)
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

        // RedisDTO에 저장된 데이터를 자동으로 JSON으로 변경하기
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        if (!redisDB.hasKey(redisKey)) {

            // 데이터 저장하기
            redisDB.opsForValue().set(redisKey, pDTO);

            // RedisDB에 저장되는 데이터의 유효시간 설정(TTL;Time To Live 설정)
            // 2일이 지나면, 자동으로 데이터가 삭제되도록 설정함
            redisDB.expire(redisKey, 2, TimeUnit.DAYS);

            log.info("Save Data!!");

            res = 1;
        }

        log.info(this.getClass().getName() + ".saveRedisStringJSON End!");

        return res;
    }

}
