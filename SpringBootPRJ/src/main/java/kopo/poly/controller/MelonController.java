package kopo.poly.controller;

import kopo.poly.dto.MelonDTO;
import kopo.poly.service.IMelonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class MelonController {

    @Resource(name = "MelonService")
    private IMelonService melonService;

    /**
     * 멜론 노래 리스트 저장하기
     */
    @GetMapping(value = "melon/collectMelonSong")
    public String collectMelonRank() throws Exception {

        log.info(this.getClass().getName() + ".collectMelonRank Start!");

        // 수집 결과 출력
        String msg;

        int res = melonService.collectMelonSong();

        if (res == 1) {
            msg = "success";

        } else {
            msg = "fail";

        }

        log.info(this.getClass().getName() + ".collectMelonRank End!");

        return msg;

    }

    /**
     * 오늘 수집된 멜론 노래 리스트 가져오기
     */
    @GetMapping(value = "melon/getSongList")
    public List<MelonDTO> getSongList() throws Exception {

        log.info(this.getClass().getName() + ".getSongList Start!");

        List<MelonDTO> rList = melonService.getSongList();

        log.info(this.getClass().getName() + ".getSongList End!");

        return rList;

    }

    /**
     * 가수별 수집된 노래의 수 가져오기
     */
    @GetMapping(value = "melon/getSingerSongCnt")
    public List<MelonDTO> getSingerSongCnt() throws Exception {

        log.info(this.getClass().getName() + ".getSingerSongCnt Start!");

        List<MelonDTO> rList = melonService.getSingerSongCnt();

        log.info(this.getClass().getName() + ".getSingerSongCnt End!");

        return rList;

    }

    /**
     * 원하는 가수와 노래 정보 가져오기
     */
    @GetMapping(value = "melon/getSingerSong")
    public List<MelonDTO> getSingerSong() throws Exception {

        log.info(this.getClass().getName() + ".getSingerSong Start!");

        List<MelonDTO> rList = melonService.getSingerSong();

        log.info(this.getClass().getName() + ".getSingerSong End!");

        return rList;
    }

    /**
     * 멜론 노래 리스트 저장하기
     */
    @GetMapping(value = "melon/collectMelonSongMany")
    public String collectMelonSongMany() throws Exception {

        log.info(this.getClass().getName() + ".collectMelonSongMany Start!");

        // 수집 결과 출력
        String msg;

        int res = melonService.collectMelonSongMany();

        if (res == 1) {
            msg = "멜론 노래 리스트 저장하기 success";

        } else {
            msg = "멜론 노래 리스트 저장하기 fail";
        }

        log.info(this.getClass().getName() + ".collectMelonSongMany End!");

        return msg;
    }

    /**
     * 가수 이름 '방탄소년단'을 'BTS'로 변경하기
     */
    @GetMapping(value = "melon/updateBTSName")
    public String updateBTSName() throws Exception {

        log.info(this.getClass().getName() + ".updateBTSName Start!");

        // 결과 출력
        String msg;

        int res = melonService.updateBTSName();

        if (res == 1) {
            msg = "'방탄소년단'을 'BTS'로 변경하기 success";

        } else {
            msg = "'방탄소년단'을 'BTS'로 변경하기 fail";

        }

        log.info(this.getClass().getName() + ".updateBTSName End!");

        return msg;
    }

    /**
     * '방탄소년단' 결과에 'nickname' 필드 추가하고 'BTS' 값 넣기; "nickname" : "BTS"
     */
    @GetMapping(value = "melon/AddBTSNickname")
    public String AddBTSNickname() throws Exception {

        log.info(this.getClass().getName() + ".AddBTSNickname Start!");

        // 결과 출력
        String msg;

        int res = melonService.updateAddBTSNickname();

        if (res == 1) {
            msg = "'nickname' 필드 추가하고 'BTS'라는 값 넣어주기 success";

        } else {
            msg = "'nickname' 필드 추가하고 'BTS'라는 값 넣어주기 fail";

        }

        log.info(this.getClass().getName() + ".AddBTSNickname End!");

        return msg;
    }

    /**
     * '방탄소년단' 결과에 'member' 필드 추가하고 'List 형태의 멤버 이름' 값으로 넣기; "member" : ["정국", "뷔", ..., "RM"]
     */
    @GetMapping(value = "melon/AddBTSMember")
    public String AddBTSMember() throws Exception {

        log.info(this.getClass().getName() + ".AddBTSMember Start!");

        // 결과 출력
        String msg;

        int res = melonService.updateAddBTSMember();

        if (res == 1) {
            msg = "'member' 필드 추가하고 'List 형태의 멤버 이름' 값으로 넣어주기 success";

        } else {
            msg = "'member' 필드 추가하고 'List 형태의 멤버 이름' 값으로 넣어주기 fail";

        }

        log.info(this.getClass().getName() + ".AddBTSMember End!");

        return msg;
    }

    /**
     * 가수의 노래 삭제하기
     */
    @GetMapping(value = "melon/deleteSong")
    public String deleteSong() throws Exception {

        log.info(this.getClass().getName() + ".deleteSong Start!");

        // 수집 결과 출력
        String msg;

        int res = melonService.deleteSong();

        if (res == 1) {
            msg = "가수 이름 삭제하기 success";

        } else {
            msg = "가수 이름 삭제하기 fail";

        }

        log.info(this.getClass().getName() + ".deleteSong End!");

        return msg;
    }
}
