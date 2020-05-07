package com.weimob.portal.goods;

import com.weimob.goods.api.GoodsService;
import com.weimob.goods.entity.Spu;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-15-12:18
 */
@RestController
@RequestMapping("/item/")
public class GoodsController {
    @Reference(version = "1.0.0")
    private GoodsService goodsService;

    /**
     * 根据spu的id集合查询spu列表
     * @param ids
     * @return
     */
    @GetMapping("spu/list/ids")
    public ResponseEntity<List<Spu>> querySpuListByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(goodsService.querySpuListByIds(ids));
    }
}
