package net.gaox.model.controller;


import net.gaox.model.entity.Item;
import net.gaox.model.service.ItemService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 商品表 前端控制器 </p>
 *
 * @author gaox·Eric
 * @since 2019-07-13
 */
@RestController
@RequestMapping("/item")
public class ItemController {
    @Resource
    ItemService itemService;

    @RequestMapping("")
    public List<Item> list() {
        List<Item> list = itemService.list(null);
        list.stream().forEach(s -> System.out.println(s.toString()));
        return list;
    }

    @PostMapping()
    public Item add(@RequestBody Item item) {
        Assert.notNull(item, "不能空啊！");
        System.out.println(item);
        itemService.save(item);
        return item;
    }
}