package com.dh.config;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingjdbc.core.constant.ShardingPropertiesConstant;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.Data;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
@AutoConfigureAfter(DataSource.class)
@Data
//@ConfigurationProperties(prefix = "sharding")
public class ShardingJdbcDatasourceConfig {

    private final DataSource dataSource;

    private DataSource shardingDataSource;

    private Map<String, Object> tables;


    @Autowired
    public ShardingJdbcDatasourceConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @PostConstruct
    public void shardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
        shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");

        Properties properties = new Properties();
        //设置显示sql
        properties.put(ShardingPropertiesConstant.SQL_SHOW.getKey(), Boolean.TRUE.toString());
        shardingDataSource = ShardingDataSourceFactory
                .createDataSource(createDataSourceMap(), shardingRuleConfig, new HashMap<>(1), properties);
    }


    private List<TableRuleConfiguration> getTableRuleConfiguration() {
        List<TableRuleConfiguration> rules = new ArrayList<>(tables.size());

        tables.forEach((table, v) -> {
            TableRuleConfiguration rule = new TableRuleConfiguration();
            //逻辑表名称
            rule.setLogicTable(table);

            rule.setActualDataNodes(((LinkedHashMap<String, String>) v).get("actual-data-nodes"));

            LinkedHashMap<String, Object> strategy = (LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) v)
                    .get("table-strategy");

            LinkedHashMap<String, String> inlineMap = (LinkedHashMap<String, String>) (strategy.get("inline"));
            //源名 + 表名
            InlineShardingStrategyConfiguration inline = new InlineShardingStrategyConfiguration(
                    inlineMap.get("sharding-column"), inlineMap.get("algorithm-expression"));
            rule.setTableShardingStrategyConfig(inline);
            rules.add(rule);
        });

        return rules;
    }

    @Bean
    public SqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mappers/**/*.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactoryBean.setDataSource(this.getShardingDataSource());
        return sqlSessionFactoryBean;
    }

    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(1);
        result.put("ds0", dataSource);
        return result;
    }

    public DataSource getShardingDataSource() {
        return this.shardingDataSource;
    }


    TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_order");
        result.setActualDataNodes("ds0.t_order_${0..1}");
        InlineShardingStrategyConfiguration inline = new InlineShardingStrategyConfiguration(
                "order_id", "t_order_${order_id % 2}");
        result.setTableShardingStrategyConfig(inline);
        return result;
    }

    TableRuleConfiguration getOrderItemTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_order_item");
        result.setActualDataNodes("ds0.t_order_item_${0..1}");
        InlineShardingStrategyConfiguration inline = new InlineShardingStrategyConfiguration(
                "order_id", "{order_id % 2}");
        result.setTableShardingStrategyConfig(inline);
        return result;
    }
}
