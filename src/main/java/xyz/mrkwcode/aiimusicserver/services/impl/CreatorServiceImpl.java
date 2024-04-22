package xyz.mrkwcode.aiimusicserver.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mrkwcode.aiimusicserver.DAOs.CreatorMapper;
import xyz.mrkwcode.aiimusicserver.pojos.Creator;
import xyz.mrkwcode.aiimusicserver.services.CreatorService;

@Slf4j
@Service
public class CreatorServiceImpl implements CreatorService {

    @Autowired
    private CreatorMapper creatorMapper;
    @Override
    public Creator findByCreatorName(String creatorname) {
        return creatorMapper.findByCreatorName(creatorname);
    }

    public Creator ifExistFindByUid(Integer uid) {
        return creatorMapper.ifExistFindByUid(uid);
    }

    @Override
    public void addCreator(Creator creator) {
        creatorMapper.addCreator(creator);
    }
}
