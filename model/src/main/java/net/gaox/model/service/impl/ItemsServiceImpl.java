package net.gaox.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.gaox.model.entity.Items;
import net.gaox.model.mapper.ItemsMapper;
import net.gaox.model.service.ItemsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@Service
public class ItemsServiceImpl extends ServiceImpl<ItemsMapper, Items> implements ItemsService {

}