# 定义mapping

```shell
# 创建索引
PUT /product
# 创建索引mapping
PUT /product/_mapping
{
  "properties": {
    "id": {
      "type": "integer"
    },
    "name": {
      "type": "text",
      "analyzer": "ik_smart",
      "search_analyzer": "ik_max_word"
    },
    "category": {
      "type": "text",
      "analyzer": "ik_smart",
      "search_analyzer": "ik_max_word"
    },
    "price": {
      "type": "double"
    },
    "code": {
      "type": "text",
      "analyzer": "ik_smart",
      "search_analyzer": "ik_max_word"
    },
    "place": {
      "type": "keyword"
    },
    "detail": {
      "properties": {
        "brand": {
          "type": "keyword"
        },
        "ingredient": {
          "type": "text",
          "analyzer": "ik_smart",
          "search_analyzer": "ik_max_word"
        },
        "releaseTime": {
          "type": "date"
        },
        "netContent": {
          "type": "double"
        }
      }
    }
  }
}

# 增加 嵌套数据
PUT /product/_doc/68181
{
  "category": "运动户外",
  "code": "40825021491",
  "id": 68181,
  "name": "OnitsukaTiger 男女连帽卫衣 运动休闲外套潮 OKS211多色",
  "place": "上海",
  "price": 1090,
  "detail": {
    "brand": "风格",
    "ingredient": "锦纶、氨纶、麻、新疆长绒棉",
    "releaseTime": "2023-12-01",
    "netContent": 256
  }
}
```