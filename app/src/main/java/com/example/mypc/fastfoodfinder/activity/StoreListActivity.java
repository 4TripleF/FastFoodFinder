package com.example.mypc.fastfoodfinder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mypc.fastfoodfinder.R;
import com.example.mypc.fastfoodfinder.adapter.StoreListAdapter;
import com.example.mypc.fastfoodfinder.helper.DividerItemDecoration;
import com.example.mypc.fastfoodfinder.model.Store.Store;
import com.example.mypc.fastfoodfinder.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MyPC on 11/30/2016.
 */
public class StoreListActivity extends AppCompatActivity {

    LinearLayoutManager layoutManager;
    StoreListAdapter adapter;
    @BindView(R.id.rv_top_list)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        ButterKnife.bind(this);

        adapter = new StoreListAdapter();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);
        loadData();
    }


    void loadData(){
        ArrayList<Store> stores = new ArrayList<>();
        stores.add(new Store("Circle K Le Thi Rieng","148 Le Thi Rieng, Ben Thanh Ward, District 1, Ho Chi Minh, Vietnam", "10.770379", "106.68912279999995","3925 6620", Constant.TYPE_CIRCLE_K));
        stores.add(new Store("FamilyMart - Hậu Giang","973 Hậu Giang, P. 11, Quận 6, TP. HCM","10.7457782220847","106.6261117905378", "3755 0439",Constant.TYPE_FAMILY_MART));
        stores.add(new Store("FamilyMart - Nguyễn Lương Bằng", "180 Nguyễn Lương Bằng, P. Tân Phú, Quận 7, TP. HCM","10.727042", "106.722703", "5417 3390",Constant.TYPE_FAMILY_MART));
        stores.add(new Store("Family Mart - Tạ Quang Bửu", "811 Tạ Quang Bửu, P. 5, Quận 8, TP. HCM","10.736488", "106.670374", "3835 3193",Constant.TYPE_FAMILY_MART));
        stores.add(new Store("Family Mart - Nguyễn Văn Công", "534 Nguyễn Văn Công, Phường 3, Quận Gò Vấp, TP. HCM","10.819417", "106.674821", "3835 3193",Constant.TYPE_FAMILY_MART));
        stores.add(new Store("Shop & Go - Phan Đình Phùng", "180 Phan Đình Phùng, P. 2, Quận Phú Nhuận, TP. HCM","10.7955070000000", "106.6825610000000", "38 353 193", Constant.TYPE_SHOP_N_GO));
        stores.add(new Store("Circle K Ly Tu Trong","238 Ly Tu Trong, Ben Thanh Ward, District 1, Ho Chi Minh, Vietnam","10.7721924", "106.69433409999999", "3822 7403", Constant.TYPE_CIRCLE_K));
        stores.add(new Store("Familymart - Đường D2", "39 Đường D2, P. 25, Quận Bình Thạnh, TP. HCM","10.80252", "106.715622", "35 126 283", Constant.TYPE_FAMILY_MART));
        stores.add(new Store("FamilyMart - 123 Nguyễn Đình Chiểu", "123 Nguyễn Đình Chiểu, Phường 6, Quận 3, TP. HCM","10.7775462","106.6892408999999","3835 3193", Constant.TYPE_FAMILY_MART));
        stores.add(new Store("FamilyMart - Tôn Dật Tiến", "Tôn Dật Tiên, Quận 7, TP. HCM","10.723322", "106.71498", "3835 3193",Constant.TYPE_FAMILY_MART));
        adapter.setDesS(stores);
    }
}