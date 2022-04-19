package kopo.poly.persistance.redis;

import kopo.poly.dto.RedisDTO;

public interface IMyRedisMapper {

    /**
     * RedisDB에 String 타입 저장하기
     *
     * @param redisKey Redis 저장 키
     * @param pDTO 저장할 정보
     * @return 저장 성공 여부
     */
    int saveRedisString(String redisKey, RedisDTO pDTO) throws Exception;

    /**
     * RedisDB에서 String 타입 가져오기
     *
     * @param redisKey 가져올 redisKey
     * @return 결과값
     */
    RedisDTO getRedisString(String redisKey) throws Exception;

    /**
     * RedisDB에 String 타입의 JSON 형태로 저장하기
     *
     * @param redisKey Redis 저장 키
     * @param pDTO 저장할 정보
     * @return 결과값
     */
    int saveRedisStringJSON(String redisKey, RedisDTO pDTO) throws Exception;

}
