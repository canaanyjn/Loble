package com.canaan.lockbible.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.Model.Verse;
import com.canaan.lockbible.R;
import com.canaan.lockbible.Tools.DateUtils;
import com.canaan.lockbible.Tools.SharedPreferenUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by canaan on 2015/4/10 0010.
 */
public class AddVerseFragment extends BaseFragment {
    @InjectView(R.id.fragment_add_verse_address_et)
    EditText addressEdittext;
    @InjectView(R.id.fragment_add_verse_content_et)
    EditText contentEdittext;
    @InjectView(R.id.fragment_add_verse_bt)
    Button confirmButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_verse_layout,container,false);
        ButterKnife.inject(getActivity(),v);


        return v;
    }

    @OnClick (R.id.fragment_add_verse_bt)
    public void confirm(){
        if (addressEdittext.getText().toString().equals("")){
            Toast.makeText(getActivity(),"请输入经节",Toast.LENGTH_SHORT).show();
            return;
        }
        if (contentEdittext.getText().toString().equals("")){
            Toast.makeText(getActivity(),"请输入经文",Toast.LENGTH_SHORT).show();
            return;
        }
        Verse verse = new Verse();
        verse.setDate(DateUtils.getDate());
        verse.setVerseAddress(addressEdittext.getText().toString());
        verse.setVerseContent(contentEdittext.getText().toString());
        mDbManager.addVerse(verse);

        SharedPreferenUtils.saveString(getActivity(), Constants.TAG_LAST_VERSE_ADDRESS,
                addressEdittext.getText().toString());
        SharedPreferenUtils.saveString(getActivity(),Constants.TAG_LAST_VERSE_CONTENT,
                contentEdittext.getText().toString());
        getActivity().setResult(1);
        getActivity().finish();
    }
}
