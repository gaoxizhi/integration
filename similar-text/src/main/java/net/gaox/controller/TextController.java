package net.gaox.controller;

import lombok.extern.slf4j.Slf4j;
import net.gaox.dto.TextDTO;
import net.gaox.util.HanlpOnline;
import net.gaox.util.TextComparator;
import net.gaox.vo.TextVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p> 文本相似度 </p>
 *
 * @author gaox·Eric
 * @date 2023-04-17 20:36
 */
@Slf4j
@RestController
@RequestMapping
public class TextController {

    @GetMapping
    public TextVO get(@RequestParam("textA") String textA, @RequestParam("textB") String textB) {
        Double cosineSimilarity = TextComparator.getCosineSimilarity(textA, textB);
        TextVO textVO = new TextVO(textA, textB, cosineSimilarity);
        return textVO;
    }

    @PostMapping
    public TextVO post(@RequestBody TextDTO dto) {
        Double cosineSimilarity = TextComparator.getCosineSimilarity(dto.getTextA(), dto.getTextB());
        TextVO textVO = new TextVO(dto.getTextA(), dto.getTextB(), cosineSimilarity);
        return textVO;
    }

    @GetMapping("/online")
    public Map<String, List> online(@RequestParam("text") String text) {
        Map<String, List> map = HanlpOnline.onlineParse(text);
        return map;
    }

}
