package com.iceteaviet.fastfoodfinder.ui.profile;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.iceteaviet.fastfoodfinder.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MyPC on 11/30/2016.
 */
public class DialogCreateNewList extends DialogFragment {
    private static final String KEY_LIST_NAME = "list_name";

    @BindView(R.id.ivQuit)
    ImageView ivQuit;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.icon1)
    CircleImageView icon1;
    @BindView(R.id.icon2)
    CircleImageView icon2;
    @BindView(R.id.icon3)
    CircleImageView icon3;
    @BindView(R.id.icon4)
    CircleImageView icon4;
    @BindView(R.id.icon5)
    CircleImageView icon5;
    @BindView(R.id.icon6)
    CircleImageView icon6;
    @BindView(R.id.icon7)
    CircleImageView icon7;
    @BindView(R.id.icon8)
    CircleImageView icon8;
    @BindView(R.id.icon9)
    CircleImageView icon9;
    @BindView(R.id.icon10)
    CircleImageView icon10;

    private ArrayList<String> listName;
    private OnCreateListListener mListener;
    private int idIconSource = R.drawable.ic_profile_list_1;

    public static DialogCreateNewList newInstance(ArrayList<String> listName) {
        DialogCreateNewList frag = new DialogCreateNewList();
        Bundle args = new Bundle();
        args.putStringArrayList(KEY_LIST_NAME, listName);
        frag.setArguments(args);
        return frag;
    }

    public void setOnButtonClickListener(OnCreateListListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            listName = getArguments().getStringArrayList(KEY_LIST_NAME);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_create_newlist, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        getIdIconSource();
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName.getText().length() < 1) {
                    Toast.makeText(getContext(), R.string.list_name_cannot_empty, Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = true;
                    for (int i = 0; i < listName.size(); i++) {
                        if (edtName.getText().toString().equals(listName.get(i))) {
                            Toast.makeText(getContext(), R.string.list_name_already_exists, Toast.LENGTH_SHORT).show();
                            check = false;
                        }
                    }
                    if (check) {
                        mListener.onButtonClick(edtName.getText().toString(), idIconSource);
                        listName.add(edtName.getText().toString());
                        dismiss();
                    }
                }
            }
        });


    }

    public void getIdIconSource() {
        icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idIconSource = R.drawable.ic_profile_list_2;
                icon1.setScaleX(1.25f);
                icon1.setScaleY(1.25f);
                icon1.setAlpha(1f);
                icon2.setScaleX(1f);
                icon2.setScaleY(1f);
                icon2.setAlpha(0.5f);
                icon3.setScaleX(1f);
                icon3.setScaleY(1f);
                icon3.setAlpha(0.5f);
                icon4.setScaleX(1f);
                icon4.setScaleY(1f);
                icon4.setAlpha(0.5f);
                icon5.setScaleX(1f);
                icon5.setScaleY(1f);
                icon5.setAlpha(0.5f);
                icon6.setScaleX(1f);
                icon6.setScaleY(1f);
                icon6.setAlpha(0.5f);
                icon7.setScaleX(1f);
                icon7.setScaleY(1f);
                icon7.setAlpha(0.5f);
                icon8.setScaleX(1f);
                icon8.setScaleY(1f);
                icon8.setAlpha(0.5f);
                icon9.setScaleX(1f);
                icon9.setScaleY(1f);
                icon9.setAlpha(0.5f);
                icon10.setScaleX(1f);
                icon10.setScaleY(1f);
                icon10.setAlpha(0.5f);


            }
        });
        icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idIconSource = R.drawable.ic_profile_list_4;
                icon1.setScaleX(1f);
                icon1.setScaleY(1f);
                icon1.setAlpha(0.5f);
                icon2.setScaleX(1.25f);
                icon2.setScaleY(1.25f);
                icon2.setAlpha(1f);
                icon3.setScaleX(1f);
                icon3.setScaleY(1f);
                icon3.setAlpha(0.5f);
                icon4.setScaleX(1f);
                icon4.setScaleY(1f);
                icon4.setAlpha(0.5f);
                icon5.setScaleX(1f);
                icon5.setScaleY(1f);
                icon5.setAlpha(0.5f);
                icon6.setScaleX(1f);
                icon6.setScaleY(1f);
                icon6.setAlpha(0.5f);
                icon7.setScaleX(1f);
                icon7.setScaleY(1f);
                icon7.setAlpha(0.5f);
                icon8.setScaleX(1f);
                icon8.setScaleY(1f);
                icon8.setAlpha(0.5f);
                icon9.setScaleX(1f);
                icon9.setScaleY(1f);
                icon9.setAlpha(0.5f);
                icon10.setScaleX(1f);
                icon10.setScaleY(1f);
                icon10.setAlpha(0.5f);

            }
        });
        icon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idIconSource = R.drawable.ic_profile_list_5;
                icon1.setScaleX(1f);
                icon1.setScaleY(1f);
                icon1.setAlpha(0.5f);
                icon2.setScaleX(1f);
                icon2.setScaleY(1f);
                icon2.setAlpha(0.5f);
                icon3.setScaleX(1.25f);
                icon3.setScaleY(1.25f);
                icon3.setAlpha(1f);
                icon4.setScaleX(1f);
                icon4.setScaleY(1f);
                icon4.setAlpha(0.5f);
                icon5.setScaleX(1f);
                icon5.setScaleY(1f);
                icon5.setAlpha(0.5f);
                icon6.setScaleX(1f);
                icon6.setScaleY(1f);
                icon6.setAlpha(0.5f);
                icon7.setScaleX(1f);
                icon7.setScaleY(1f);
                icon7.setAlpha(0.5f);
                icon8.setScaleX(1f);
                icon8.setScaleY(1f);
                icon8.setAlpha(0.5f);
                icon9.setScaleX(1f);
                icon9.setScaleY(1f);
                icon9.setAlpha(0.5f);
                icon10.setScaleX(1f);
                icon10.setScaleY(1f);
                icon10.setAlpha(0.5f);

            }
        });
        icon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idIconSource = R.drawable.ic_profile_list_6;
                icon1.setScaleX(1f);
                icon1.setScaleY(1f);
                icon1.setAlpha(0.5f);
                icon2.setScaleX(1f);
                icon2.setScaleY(1f);
                icon2.setAlpha(0.5f);
                icon3.setScaleX(1f);
                icon3.setScaleY(1f);
                icon3.setAlpha(0.5f);
                icon4.setScaleX(1.25f);
                icon4.setScaleY(1.25f);
                icon4.setAlpha(1f);
                icon5.setScaleX(1f);
                icon5.setScaleY(1f);
                icon5.setAlpha(0.5f);
                icon6.setScaleX(1f);
                icon6.setScaleY(1f);
                icon6.setAlpha(0.5f);
                icon7.setScaleX(1f);
                icon7.setScaleY(1f);
                icon7.setAlpha(0.5f);
                icon8.setScaleX(1f);
                icon8.setScaleY(1f);
                icon8.setAlpha(0.5f);
                icon9.setScaleX(1f);
                icon9.setScaleY(1f);
                icon9.setAlpha(0.5f);
                icon10.setScaleX(1f);
                icon10.setScaleY(1f);
                icon10.setAlpha(0.5f);

            }
        });
        icon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idIconSource = R.drawable.ic_profile_list_7;
                icon1.setScaleX(1f);
                icon1.setScaleY(1f);
                icon1.setAlpha(0.5f);
                icon2.setScaleX(1f);
                icon2.setScaleY(1f);
                icon2.setAlpha(0.5f);
                icon3.setScaleX(1f);
                icon3.setScaleY(1f);
                icon3.setAlpha(0.5f);
                icon4.setScaleX(1f);
                icon4.setScaleY(1f);
                icon4.setAlpha(0.5f);
                icon5.setScaleX(1.25f);
                icon5.setScaleY(1.25f);
                icon5.setAlpha(1f);
                icon6.setScaleX(1f);
                icon6.setScaleY(1f);
                icon6.setAlpha(0.5f);
                icon7.setScaleX(1f);
                icon7.setScaleY(1f);
                icon7.setAlpha(0.5f);
                icon8.setScaleX(1f);
                icon8.setScaleY(1f);
                icon8.setAlpha(0.5f);
                icon9.setScaleX(1f);
                icon9.setScaleY(1f);
                icon9.setAlpha(0.5f);
                icon10.setScaleX(1f);
                icon10.setScaleY(1f);
                icon10.setAlpha(0.5f);
            }
        });
        icon6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idIconSource = R.drawable.ic_profile_list_8;
                icon1.setScaleX(1f);
                icon1.setScaleY(1f);
                icon1.setAlpha(0.5f);
                icon2.setScaleX(1f);
                icon2.setScaleY(1f);
                icon2.setAlpha(0.5f);
                icon3.setScaleX(1f);
                icon3.setScaleY(1f);
                icon3.setAlpha(0.5f);
                icon4.setScaleX(1f);
                icon4.setScaleY(1f);
                icon4.setAlpha(0.5f);
                icon5.setScaleX(1f);
                icon5.setScaleY(1f);
                icon5.setAlpha(0.5f);
                icon6.setScaleX(1.25f);
                icon6.setScaleY(1.25f);
                icon6.setAlpha(1f);
                icon7.setScaleX(1f);
                icon7.setScaleY(1f);
                icon7.setAlpha(0.5f);
                icon8.setScaleX(1f);
                icon8.setScaleY(1f);
                icon8.setAlpha(0.5f);
                icon9.setScaleX(1f);
                icon9.setScaleY(1f);
                icon9.setAlpha(0.5f);
                icon10.setScaleX(1f);
                icon10.setScaleY(1f);
                icon10.setAlpha(0.5f);
            }
        });
        icon7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idIconSource = R.drawable.ic_profile_list_9;
                icon1.setScaleX(1f);
                icon1.setScaleY(1f);
                icon1.setAlpha(0.5f);
                icon2.setScaleX(1f);
                icon2.setScaleY(1f);
                icon2.setAlpha(0.5f);
                icon3.setScaleX(1f);
                icon3.setScaleY(1f);
                icon3.setAlpha(0.5f);
                icon4.setScaleX(1f);
                icon4.setScaleY(1f);
                icon4.setAlpha(0.5f);
                icon5.setScaleX(1f);
                icon5.setScaleY(1f);
                icon5.setAlpha(0.5f);
                icon6.setScaleX(1f);
                icon6.setScaleY(1f);
                icon6.setAlpha(0.5f);
                icon7.setScaleX(1.25f);
                icon7.setScaleY(1.25f);
                icon7.setAlpha(1f);
                icon8.setScaleX(1f);
                icon8.setScaleY(1f);
                icon8.setAlpha(0.5f);
                icon9.setScaleX(1f);
                icon9.setScaleY(1f);
                icon9.setAlpha(0.5f);
                icon10.setScaleX(1f);
                icon10.setScaleY(1f);
                icon10.setAlpha(0.5f);
            }
        });
        icon8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idIconSource = R.drawable.ic_profile_list_10;
                icon1.setScaleX(1f);
                icon1.setScaleY(1f);
                icon1.setAlpha(0.5f);
                icon2.setScaleX(1f);
                icon2.setScaleY(1f);
                icon2.setAlpha(0.5f);
                icon3.setScaleX(1f);
                icon3.setScaleY(1f);
                icon3.setAlpha(0.5f);
                icon4.setScaleX(1f);
                icon4.setScaleY(1f);
                icon4.setAlpha(0.5f);
                icon5.setScaleX(1f);
                icon5.setScaleY(1f);
                icon5.setAlpha(0.5f);
                icon6.setScaleX(1f);
                icon6.setScaleY(1f);
                icon6.setAlpha(0.5f);
                icon7.setScaleX(1f);
                icon7.setScaleY(1f);
                icon7.setAlpha(0.5f);
                icon8.setScaleX(1.25f);
                icon8.setScaleY(1.25f);
                icon8.setAlpha(1f);
                icon9.setScaleX(1f);
                icon9.setScaleY(1f);
                icon9.setAlpha(0.5f);
                icon10.setScaleX(1f);
                icon10.setScaleY(1f);
                icon10.setAlpha(0.5f);
            }
        });
        icon9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idIconSource = R.drawable.ic_profile_list_11;
                icon1.setScaleX(1f);
                icon1.setScaleY(1f);
                icon1.setAlpha(0.5f);
                icon2.setScaleX(1f);
                icon2.setScaleY(1f);
                icon2.setAlpha(0.5f);
                icon3.setScaleX(1f);
                icon3.setScaleY(1f);
                icon3.setAlpha(0.5f);
                icon4.setScaleX(1f);
                icon4.setScaleY(1f);
                icon4.setAlpha(0.5f);
                icon5.setScaleX(1f);
                icon5.setScaleY(1f);
                icon5.setAlpha(0.5f);
                icon6.setScaleX(1f);
                icon6.setScaleY(1f);
                icon6.setAlpha(0.5f);
                icon7.setScaleX(1f);
                icon7.setScaleY(1f);
                icon7.setAlpha(0.5f);
                icon8.setScaleX(1f);
                icon8.setScaleY(1f);
                icon8.setAlpha(0.5f);
                icon9.setScaleX(1.25f);
                icon9.setScaleY(1.25f);
                icon9.setAlpha(1f);
                icon10.setScaleX(1f);
                icon10.setScaleY(1f);
                icon10.setAlpha(0.5f);
            }
        });
        icon10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idIconSource = R.drawable.ic_profile_list_3;
                icon1.setScaleX(1f);
                icon1.setScaleY(1f);
                icon1.setAlpha(0.5f);
                icon2.setScaleX(1f);
                icon2.setScaleY(1f);
                icon2.setAlpha(0.5f);
                icon3.setScaleX(1f);
                icon3.setScaleY(1f);
                icon3.setAlpha(0.5f);
                icon4.setScaleX(1f);
                icon4.setScaleY(1f);
                icon4.setAlpha(0.5f);
                icon5.setScaleX(1f);
                icon5.setScaleY(1f);
                icon5.setAlpha(0.5f);
                icon6.setScaleX(1f);
                icon6.setScaleY(1f);
                icon6.setAlpha(0.5f);
                icon7.setScaleX(1f);
                icon7.setScaleY(1f);
                icon7.setAlpha(0.5f);
                icon8.setScaleX(1f);
                icon8.setScaleY(1f);
                icon8.setAlpha(0.5f);
                icon9.setScaleX(1f);
                icon9.setScaleY(1f);
                icon9.setAlpha(0.5f);
                icon10.setScaleX(1.25f);
                icon10.setScaleY(1.25f);
                icon10.setAlpha(1f);
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //request
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public interface OnCreateListListener {
        void onButtonClick(String name, int idIconSource);
    }
}
