package kopo.poly.service;

import kopo.poly.dto.RedisDTO;

public interface IMyRedisService {

    /**
     * RedisDB에 String 타입 저장하기
     */
    int saveRedisString() throws Exception;

    /**
     * RedisDB에서 String 타입 가져오기
     */
    RedisDTO getRedisString() throws Exception;

    /**
     * RedisDB에 String 타입의 JSON 형태로 저장하기
     */
    int saveRedisStringJSON() throws Exception;

}
