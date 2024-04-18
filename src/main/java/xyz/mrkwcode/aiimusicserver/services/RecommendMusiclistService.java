package xyz.mrkwcode.aiimusicserver.services;

import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.pojos.Musiclist;
import xyz.mrkwcode.aiimusicserver.utils.recommendUtil.dto.ProductDTO;
import xyz.mrkwcode.aiimusicserver.utils.recommendUtil.dto.RelateDTO;

import java.util.List;

public interface RecommendMusiclistService {
//     /* 获取评分记录 */
//     List<RelateDTO> getRankData();
//
//     /* 获取歌单数据 */
//     List<ProductDTO> getMusiclistData();
//
//     /* 根据用户评分推荐歌单 */
//     List<Musiclist> recommendMusiclistByRank(Integer userId);
//
    /* 根据用户收藏进行歌单推荐 */
    List<Musiclist> recommendMusiclistByFavourite(Integer userId);

    /* 推荐歌曲 */
    List<Music> recommendMusic(Integer userId);

    /* 获取歌曲数据 */
    List<ProductDTO> getMusicData();

    /* 获取用户收藏歌曲 */
    List<RelateDTO> getFavouriteData();

}
