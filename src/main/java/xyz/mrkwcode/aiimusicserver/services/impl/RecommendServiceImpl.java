package xyz.mrkwcode.aiimusicserver.services.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.mrkwcode.aiimusicserver.DAOs.*;
import xyz.mrkwcode.aiimusicserver.pojos.Favourite;
import xyz.mrkwcode.aiimusicserver.pojos.Music;
// import xyz.mrkwcode.aiimusicserver.services.RecommendMusiclistService;
import xyz.mrkwcode.aiimusicserver.pojos.MusicToList;
import xyz.mrkwcode.aiimusicserver.pojos.Musiclist;
import xyz.mrkwcode.aiimusicserver.services.RecommendMusiclistService;
import xyz.mrkwcode.aiimusicserver.utils.recommendUtil.core.CoreMath;
import xyz.mrkwcode.aiimusicserver.utils.recommendUtil.dto.ProductDTO;
import xyz.mrkwcode.aiimusicserver.utils.recommendUtil.dto.RelateDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl implements RecommendMusiclistService {
    @Autowired
    private RankMapper rankMapper;

    @Autowired
    private MusiclistMapper musiclistMapper;

    @Autowired
    private FavouriteMapper favouriteMapper;

    @Autowired
    private MusicMapper musicMapper;

    @Autowired
    private MusicToListMapper musicToListMapper;

    @Override
    public List<RelateDTO> getFavouriteData(){
        List<RelateDTO> relateDTOList = Lists.newArrayList();
        List<Favourite> favouriteList = favouriteMapper.allFavourite();
        for (Favourite favourite : favouriteList) {
            RelateDTO relateDTO = new RelateDTO();
            relateDTO.setUserId(favourite.getUid());
            relateDTO.setProductId(favourite.getMid());
            //1-10随机数模拟用户听歌次数
            relateDTO.setIndex((int)(Math.random()*10+1));
            //relateDTO.setIndex(1);
            relateDTOList.add(relateDTO);
        }
        if(CollectionUtils.isEmpty(relateDTOList)){
            System.out.println("--------------------List<RelateDTO>为空！");
        }
        return relateDTOList;
    }
    @Override
    public List<ProductDTO> getMusicData() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        List<Music> musiclists = musicMapper.getMusicListDefault(null, null);
        for (Music music : musiclists) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(music.getMid());
            productDTOList.add(productDTO);
        }
        if(CollectionUtils.isEmpty(productDTOList)){
            System.out.println("----------------------List<ProductDTO>为空！");
        }
        return productDTOList;
    }

    @Override
    public List<Music> recommendMusic(Integer userId){
        CoreMath coreMath = new CoreMath();
        List<RelateDTO> relateDTOList = getFavouriteData();
        //执行推荐算法
        List<Integer> recommendations = coreMath.recommend(userId, relateDTOList);
        List<ProductDTO> productDTOList= getMusicData().stream().filter(e->recommendations.contains(e.getProductId())).collect(Collectors.toList());
        //如果推荐id为空
        if(CollectionUtils.isEmpty(productDTOList)){
            System.out.println("-----------推荐的歌曲id集为空！");
            return musicMapper.getMusicListDefault(null, null);
        }
        List<Music> musicList = new ArrayList<>();
        //获取歌曲id
        List<Integer> productIdList = productDTOList.stream().map(e -> e.getProductId()).collect(Collectors.toList());
        //根据歌曲id获取歌曲
        for (Integer productId : productIdList) {
            musicList.add(musicMapper.findByMid(productId));
        }
        return musicList;
    }
    //
    // @Override
    // public List<RelateDTO> getRankData() {
    //     List<RelateDTO> relateList = Lists.newArrayList();
    //     List<Rank> rankList = rankMapper.selectRanks();
    //     for (Rank rank : rankList) {
    //         RelateDTO relateDTO = new RelateDTO();
    //         relateDTO.setUserId(rank.getConsumerId().intValue());
    //         relateDTO.setProductId(rank.getSongListId().intValue());
    //         relateDTO.setIndex(rank.getScore());
    //         relateList.add(relateDTO);
    //     }
    //     if(CollectionUtils.isEmpty(relateList)){
    //         System.out.println("--------------------List<RelateDTO>为空！");
    //     }
    //     return relateList;
    // }
    //
    //
    // @Override
    // public List<ProductDTO> getMusiclistData() {
    //     List<ProductDTO> productDTOList = new ArrayList<>();
    //     List<SongList> songLists = songListMapper.allSongList();
    //     for (SongList songList : songLists) {
    //         ProductDTO productDTO = new ProductDTO();
    //         productDTO.setProductId(songList.getId());
    //         productDTOList.add(productDTO);
    //     }
    //     if(CollectionUtils.isEmpty(productDTOList)){
    //         System.out.println("----------------------List<ProductDTO>为空！");
    //     }
    //     return productDTOList;
    // }
    //
    //
    // @Override
    // public List<SongList> recommendMusiclistByRank(Integer userId){
    //     CoreMath coreMath = new CoreMath();
    //     List<RelateDTO> data = getRankData();
    //     //执行推荐算法
    //     List<Integer> recommendations = coreMath.recommend(userId, data);
    //     //根据返回的商品ids 从getProductData()进行过滤出 所有ProductDTO
    //     List<ProductDTO> productDTOS = getSongListData();
    //     List<ProductDTO> productDTOList= productDTOS.stream().filter(e->recommendations.contains(e.getProductId())).collect(Collectors.toList());
    //     //如果推荐id为空
    //     if(CollectionUtils.isEmpty(productDTOList)){
    //         System.out.println("-----------推荐的歌单id集为空！");
    //         return songListMapper.allSongList();
    //     }
    //     List<SongList> songList = new ArrayList<>();
    //     List<Integer> productIdList = productDTOList.stream().map(e -> e.getProductId()).collect(Collectors.toList());
    //     for (Integer productId : productIdList) {
    //         SongList songList1 = songListMapper.selectByPrimaryKey(productId);
    //         songList.add(songList1);
    //     }
    //     if(CollectionUtils.isEmpty(songList)){
    //
    //         return songListMapper.allSongList();
    //     }
    //     return songList;
    // }
    @Override
    public List<Musiclist> recommendMusiclistByFavourite(Integer uid){
        //获取推荐歌曲
        List<Music> musics = recommendMusic(uid);
        //如果推荐歌曲为空，就返回所有歌单
        if(CollectionUtils.isEmpty(musics)){
            return musiclistMapper.listedMusicListDefault(null, null, null);
        }
        //歌曲和歌单对应表
        List<MusicToList> listMusicLists = musicToListMapper.allMusicToList();
        List<Integer> mids = musics.stream().map(e -> e.getMid()).collect(Collectors.toList());
        List<MusicToList> listMusics = listMusicLists.stream().filter(e -> mids.contains(e.getMid())).collect(Collectors.toList());
        List<Musiclist> musiclists = new ArrayList<>();
        //获取歌单id集
        List<Integer> musicListIds = listMusics.stream().map(e -> e.getMlid()).collect(Collectors.toList());
        //根据歌单id获取歌单
        for (Integer musiclistId : musicListIds) {
            musiclists.add(musiclistMapper.findByMlid(musiclistId));
        }
        //如果推荐歌单为空，返回所有歌单
        if(CollectionUtils.isEmpty(musiclists)){
            return musiclistMapper.listedMusicListDefault(null, null, null);
        }
        return musiclists;
    }



}
