package com.wjc.jokerwanshoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wjc.jokerwanshoppingmall.app.MyAppliction;
import com.wjc.jokerwanshoppingmall.home.bean.GoodsBean;
import com.wjc.jokerwanshoppingmall.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 购物车数据存储类
 * 提供购物车商品的增删改查
 */
public class CartProvider {
    public static final String JSON_CART = "json_cart";
    private Context context;
    //优化过的HashMap集合（比HashMap内存效率高），其内部实现了一个矩阵压缩算法，
    // 很适合存储稀疏矩阵的。此外它的查找算法是二分法，提高了查找的效率。
    private SparseArray<GoodsBean> datas;

    private static CartProvider cartProvider;

    private CartProvider(Context context) {
        this.context = context;
        datas = new SparseArray<>(100);
        listToSparse();
    }

    public static CartProvider getInstance() {
        if (cartProvider == null) {
            cartProvider = new CartProvider(MyAppliction.getContext());
        }
        return cartProvider;
    }

    private void listToSparse() {
        List<GoodsBean> carts = getAllData();
        //放到sparseArry中
        if (carts != null && carts.size() > 0) {
            for (int i = 0; i < carts.size(); i++) {
                GoodsBean goodsBean = carts.get(i);
                datas.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
            }
        }
    }


    private List<GoodsBean> parsesToList() {
        List<GoodsBean> carts = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean shoppingCart = datas.valueAt(i);
                carts.add(shoppingCart);
            }
        }
        return carts;
    }

    public List<GoodsBean> getAllData() {
        return getDataFromLocal();
    }

    //本地获取json数据，并且通过Gson解析成list列表数据
    public List<GoodsBean> getDataFromLocal() {
        List<GoodsBean> carts = new ArrayList<>();
        //从本地获取缓存数据
        String savaJson = CacheUtils.getString(context, JSON_CART);
        if (!TextUtils.isEmpty(savaJson)) {
            //用Gson把json数据转换成List集合
            carts = new Gson().fromJson(savaJson, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return carts;

    }

    public void addData(GoodsBean cart) {

        //添加数据
        GoodsBean tempCart = datas.get(Integer.parseInt(cart.getProduct_id()));
        if (tempCart != null) {
            //如果SparseArray中已经存在此商品，在商品原来的数量基础上，加上新加进购物车的数量
            tempCart.setNumber(tempCart.getNumber() + cart.getNumber());
        } else {
            tempCart = cart;
            tempCart.setNumber(cart.getNumber());
        }

        datas.put(Integer.parseInt(tempCart.getProduct_id()), tempCart);


        //保存数据
        commit();
    }

    //保存数据
    private void commit() {
        //把parseArray转换成list
        List<GoodsBean> carts = parsesToList();
        //把List集合转换成String类型的json
        String json = new Gson().toJson(carts);

        // 保存
        CacheUtils.putString(context, JSON_CART, json);

    }


    public void deleteData(GoodsBean cart) {

        //删除数据
        datas.delete(Integer.parseInt(cart.getProduct_id()));

        //保存数据
        commit();
    }

    public void updataData(GoodsBean cart) {
        //修改数据
        datas.put(Integer.parseInt(cart.getProduct_id()), cart);
        //保存数据
        commit();
    }

}
