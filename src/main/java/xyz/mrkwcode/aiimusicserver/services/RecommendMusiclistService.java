package xyz.mrkwcode.aiimusicserver.services;

import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.pojos.Musiclist;
import xyz.mrkwcode.aiimusicserver.pojos.PageBean;
import xyz.mrkwcode.aiimusicserver.utils.recommendUtil.dto.ProductDTO;
import xyz.mrkwcode.aiimusicserver.utils.recommendUtil.dto.RelateDTO;

import java.util.List;

public interface RecommendMusiclistService {

    /* 根据用户收藏进行歌单推荐 */
    List<Musiclist> recommendMusiclistByFavourite(Integer userId);

    /* 推荐歌曲 */
    List<Music> recommendMusic(Integer userId);

    /* 获取歌曲数据 */
    List<ProductDTO> getMusicData();

    /* 获取用户收藏歌曲 */
    List<RelateDTO> getFavouriteData();
}
