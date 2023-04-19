/** Created by Sagar.Mohanty
 * Apr 18, 2023
 * go-train-schedule
 */
package com.gotrain.schedule.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

/**
 * @author Sagar.Mohanty
 * go-train-schedule
 *	Apr 18, 2023
 */
@SpringBootTest
public class CacheTest {

  @Autowired
  private CacheManager cacheManager;

  @Test
  public void testCache() {
    // Put a value in the cache
    cacheManager.getCache("myCache").put("key", "value");

    // Check if the value is in the cache
    assertThat(cacheManager.getCache("myCache").get("key")).isNotNull();

    // Remove the value from the cache
    cacheManager.getCache("myCache").evict("key");

    // Check if the value was removed from the cache
    assertThat(cacheManager.getCache("myCache").get("key")).isNull();
  }
}