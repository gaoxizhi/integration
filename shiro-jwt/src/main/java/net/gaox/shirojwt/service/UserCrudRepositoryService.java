package net.gaox.shirojwt.service;

import net.gaox.shirojwt.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 UserCrudRepositoryService
 * @author gaox·Eric
 * @date 2019/5/4 21:02
 */
public interface UserCrudRepositoryService extends CrudRepository<User,Long> {

//    void saves(User user);
}