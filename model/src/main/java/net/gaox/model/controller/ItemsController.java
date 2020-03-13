package net.gaox.model.controller;


import net.gaox.model.entity.Items;
import net.gaox.model.service.ItemsService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@RestController
@RequestMapping("/items")
public class ItemsController {
    @Resource
    ItemsService itemsService;

    @RequestMapping("")
    public List<Items> list() {
        List<Items> list = itemsService.list(null);
        list.stream().forEach(s -> System.out.println(s.toString()));
        return list;
    }

    @PostMapping()
    public Items add(@RequestBody Items items) {
        Assert.notNull(items, "不能空啊！");
        System.out.println(items);
        itemsService.save(items);
        return items;
    }
}