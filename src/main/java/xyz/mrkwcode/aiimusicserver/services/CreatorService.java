package xyz.mrkwcode.aiimusicserver.services;

import xyz.mrkwcode.aiimusicserver.pojos.Creator;

public interface CreatorService {
    Creator findByCreatorName(String creatorname);

    Creator ifExistFindByUid(Integer uid);

    void addCreator(Creator creator);
}
