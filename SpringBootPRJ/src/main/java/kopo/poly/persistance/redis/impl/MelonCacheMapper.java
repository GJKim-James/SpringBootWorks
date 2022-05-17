package kopo.poly.persistance.redis.impl;

import kopo.poly.dto.MelonDTO;
import kopo.poly.persistance.redis.IMelonCacheMapper;
import kopo.poly.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component("MelonCacheMapper")
public class MelonCacheMapper implements IMelonCacheMapper {

    public final RedisTemplate<String, Object> redisDB;

    // RedisDB 객체 가져오기
    public MelonCacheMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;
    }

    @Override
    public int insertSong(List<MelonDTO> pList, String redisKey) throws Exception {

        log.info(this.getClass().getName() + ".insertSong(RedisDB에 멜론 Top100 노래 리스트 저장하기) Start!");

        int res = 0;

        // RedisDB에 저장될 키(key)
        String key = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // RedisDB의 키 데이터 타입을 String으로 정의(항상 String으로 설정함)
        redisDB.setKeySerializer(new StringRedisSerializer());
        // MelonDTO에 저장된 데이터를 자동으로 JSON 형태로 변경하기
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(MelonDTO.class));

        // 람다식으로 데이터 저장하기(멜론 Top100 노래들을 List 구조로 RedisDB에 저장)
        // 람다식은 순서에 상관없이 저장하기 때문에 오름차순(rightPush), 내림차순(leftPush)은 중요하지 않음
        // 저장되는 데이터의 순서가 중요하다면 람다식을 사용하지 말 것!
        pList.forEach(melon -> redisDB.opsForList().leftPush(key, melon));

        // 저장되는 데이터의 유효기간(TTL;Time To Live)은 1시간으로 정의
        redisDB.expire(redisKey, 1, TimeUnit.HOURS);

        res = 1;

        log.info(this.getClass().getName() + ".insertSong(RedisDB에 멜론 Top100 노래 리스트 저장하기) End!");

        return res;
    }

    @Override
    public boolean getExistKey(String key) throws Exception {

        // RedisDB에 저장된 키(key)가 존재한다면
        if (redisDB.hasKey(key)) {
            return true;

        } else {
            return false;

        }
    }

    @Override
    public List<MelonDTO> getSongList(String key) throws Exception {

        log.info(this.getClass().getName() + ".getSongList(RedisDB에서 멜론 Top100 노래 리스트 가져오기) Start!");

        // RedisDB의 키 데이터 타입을 String으로 정의(항상 String으로 설정함)
        redisDB.setKeySerializer(new StringRedisSerializer());
        // MelonDTO에 저장된 데이터를 자동으로 JSON 형태로 변경하기
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(MelonDTO.class));

        // 결과값을 저장할 객체
        List<MelonDTO> rList = null;

        if (redisDB.hasKey(key)) { // RedisDB에 저장된 키(key)가 존재한다면
            rList = (List) redisDB.opsForList().range(key, 0, -1); // 전체 데이터 가져와서 rList에 담기
            // (List)는 형 변환을 의미한다. 위에 있는 List<MelonDTO>와 동일한 형으로 변환된다.
            // 'List'는 Object(최상위 객체)를 의미하기 때문에 List<MelonDTO>도 받을 수 있다.
        }

        log.info(this.getClass().getName() + ".getSongList(RedisDB에서 멜론 Top100 노래 리스트 가져오기) End!");

        return rList;
    }
}
