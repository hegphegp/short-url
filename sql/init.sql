CREATE TABLE `surl_short_url_mapping` (
  `id` bigint(20) NOT NULL,
  `short_url_keyid` varchar(10) DEFAULT NULL,
  `short_url_keyid_long` bigint(20) DEFAULT NULL,
  `original_url` varchar(500) DEFAULT NULL,
  `original_url_hashcode` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);