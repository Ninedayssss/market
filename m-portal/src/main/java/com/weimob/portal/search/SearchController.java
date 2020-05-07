package com.weimob.portal.search;

import com.weimob.common.vo.PageResult;
import com.weimob.goods.entity.Spu;
import com.weimob.search.api.SearchService;
import com.weimob.search.entity.SearchRequest;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itsNine
 * @create 2020-04-15-12:19
 */
@RestController
@RequestMapping("/search/")
public class SearchController {
    @Reference(version = "1.0.0")
    private SearchService searchService;

    @PostMapping("page")
    public ResponseEntity<PageResult<Spu>> search(@RequestBody SearchRequest request){
        return ResponseEntity.ok(searchService.search(request));
    }
}
