package com.example.secondhand.service;

import com.example.secondhand.dao.ItemDao;
import com.example.secondhand.dao.ItemDaoImpl;
import com.example.secondhand.entity.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;

    public ItemServiceImpl() {
        this.itemDao = new ItemDaoImpl();
    }

    @Override
    public int publishItem(Item item) throws Exception {
        // 验证必要字段
        if (item.getTitle() == null || item.getTitle().trim().isEmpty()) {
            throw new Exception("商品标题不能为空");
        }
        if (item.getPrice() == null || item.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("商品价格必须大于0");
        }

        return itemDao.insert(item);
    }

    @Override
    public Optional<Item> getItemById(int id) throws Exception {
        return itemDao.findById(id);
    }

    @Override
    public List<Item> getAllItems() throws Exception {
        return itemDao.findAll();
    }

    @Override
    public List<Item> getItemsByUserId(int userId) throws Exception {
        return itemDao.findByUserId(userId);
    }

    @Override
    public List<Item> searchItems(String keyword) throws Exception {
        // 模糊搜索实现
        if (keyword == null || keyword.trim().isEmpty()) {
            return itemDao.findAll();
        }
        return itemDao.findByKeyword(keyword.trim());
    }

    @Override
    public List<Item> getItemsByCategory(String category) throws Exception {
        return itemDao.findByCategory(category);
    }

    @Override
    public List<Item> getItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) throws Exception {
        if (minPrice == null) minPrice = BigDecimal.ZERO;
        if (maxPrice == null) maxPrice = new BigDecimal("99999999");

        if (minPrice.compareTo(maxPrice) > 0) {
            throw new Exception("最低价格不能高于最高价格");
        }

        return itemDao.findByPriceRange(minPrice, maxPrice);
    }

    @Override
    public boolean updateItem(Item item) throws Exception {
        // 验证必要字段
        if (item.getTitle() == null || item.getTitle().trim().isEmpty()) {
            throw new Exception("商品标题不能为空");
        }
        if (item.getPrice() == null || item.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("商品价格必须大于0");
        }

        return itemDao.update(item) > 0;
    }

    @Override
    public boolean deleteItem(int id) throws Exception {
        return itemDao.delete(id) > 0;
    }

    @Override
    public boolean updateItemStatus(int id, String status) throws Exception {
        return itemDao.updateStatus(id, status) > 0;
    }
}