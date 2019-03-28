package com.chumeng.yxfz.Dialog;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chumeng.yxfz.Entity.EntityListData;
import com.chumeng.yxfz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/9/13.
 *
 */
@SuppressLint("ValidFragment")
public class DialogListFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private List<EntityListData.DataBean> listitem;
    private TextView textView;
    private CallBackListener callBackListener;
    public DialogListFragment(){

    }
    public DialogListFragment(List<EntityListData.DataBean> listitem, TextView textView, CallBackListener callBackListener) {
        this.listitem = listitem;
        this.textView = textView;
        this.callBackListener = callBackListener;
    }

    public interface CallBackListener
    {
        void onPosition(EntityListData.DataBean dataBean);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_list_fragment,null,false);
        listView= (ListView) view.findViewById(R.id.listview_dialog);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> list = new ArrayList<String>();
        for (EntityListData.DataBean dataBean : listitem) {
            list.add(dataBean.getName());
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(), R.layout.list_item,list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        textView.setText(listitem.get(position).getName());
        if (callBackListener!=null){
            callBackListener.onPosition(listitem.get(position));
        }
    }
}
