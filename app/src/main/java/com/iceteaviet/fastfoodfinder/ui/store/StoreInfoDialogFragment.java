package com.iceteaviet.fastfoodfinder.ui.store;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iceteaviet.fastfoodfinder.R;
import com.iceteaviet.fastfoodfinder.data.remote.store.model.Store;
import com.iceteaviet.fastfoodfinder.utils.FormatUtils;
import com.iceteaviet.fastfoodfinder.utils.PermissionUtils;
import com.iceteaviet.fastfoodfinder.utils.StringUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.iceteaviet.fastfoodfinder.ui.store.StoreDetailActivity.KEY_STORE;

/**
 * Created by taq on 26/11/2016.
 */

public class StoreInfoDialogFragment extends DialogFragment {

    public StoreDetailAdapter.CallDirectionViewHolder cdvh;
    @BindView(R.id.store_name)
    TextView tvStoreName;
    @BindView(R.id.view_detail)
    TextView tvViewDetail;
    @BindView(R.id.store_address)
    TextView tvStoreAddress;
    @BindView(R.id.call_direction)
    View vCallDirection;
    @BindView(R.id.save_this)
    Button btnAddToFavorite;

    private Store store;

    private StoreDialogActionListener mListener;


    public static StoreInfoDialogFragment newInstance(Store store) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_STORE, store);
        StoreInfoDialogFragment fragment = new StoreInfoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setDialogListen(StoreDialogActionListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_info, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        cdvh = new StoreDetailAdapter.CallDirectionViewHolder(vCallDirection);

        if (getArguments() != null)
            store = getArguments().getParcelable(KEY_STORE);

        if (store == null)
            return;

        tvStoreName.setText(store.getTitle());
        tvStoreAddress.setText(store.getAddress());
        tvViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(StoreDetailActivity.getIntent(getContext(), store));
            }
        });

        cdvh.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmpty(store.getTel())) {
                    if (PermissionUtils.isCallPhonePermissionGranted(getContext()))
                        startActivity(FormatUtils.getCallIntent(store.getTel()));
                    else
                        PermissionUtils.requestCallPhonePermission(StoreInfoDialogFragment.this);
                } else {
                    Toast.makeText(getActivity(), R.string.store_no_phone_numb, Toast.LENGTH_SHORT).show();
                }
            }
        });
        cdvh.btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDirection(store);
            }
        });

        btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAddToFavorite(store.getId());
                dismiss();
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (0.8 * size.x), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PermissionUtils.REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(FormatUtils.getCallIntent(store.getTel()));
                } else {
                    Toast.makeText(getContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
            }

            default:
                break;
        }
    }

    // tên chuối thiệt
    public interface StoreDialogActionListener {
        void onDirection(Store store);

        void onAddToFavorite(int storeId);
    }
}
