package com.vollino.poll.service.vote;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bruno Vollino
 */
@Repository
public interface VoteRepository extends CrudRepository<Vote, VoteId> {
}