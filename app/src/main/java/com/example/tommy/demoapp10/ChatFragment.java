package com.example.tommy.demoapp10;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatFragment extends Fragment {
    private String CHAT_NAME;
    private String USER_NAME;
    private String USER_EMAIL;

    private ListView chat_view;
    private EditText chat_edit;
    private Button chat_send;
    private Button product_list_btn;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =firebaseDatabase.getReference();

    // CustomOnClickListener
    public interface CustomOnClickListener {
        public void onClicked(View clickedView);
    }

    private CustomOnClickListener customOnClickListener;

    public ChatFragment(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        // Reference widget ID
        chat_view = view.findViewById(R.id.chat_view);
        chat_send = view.findViewById(R.id.chat_send);
        chat_edit = view.findViewById(R.id.chat_edit);
        product_list_btn = view.findViewById(R.id.product_list_btn);

        // Set User's Auth info and Chat_name
        USER_NAME = getArguments().getString("user_name");
        USER_EMAIL = getArguments().getString("user_email");
        CHAT_NAME = getArguments().getString("chat_name");

        databaseReference.child("chat").child(CHAT_NAME).child("");
        // 채팅방 입장
        openChat(CHAT_NAME);

        //메시지 전송 버튼에 대한 클릭 리스너 지정
        chat_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(chat_edit.getText().toString().equals("")) return;

                ChatDTO chat = new ChatDTO(USER_NAME, chat_edit.getText().toString());
                databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat);
                chat_edit.setText("");
            }
            });

        // 상품목록 버튼에 대한 클릭 리스너 지정
        product_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {buttonClicked(view);}
        });

        return view;
    }

    public void buttonClicked(View view) {
        customOnClickListener.onClicked(view);
    }

    private void addMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter){
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.add(chatDTO.getUserName() + ": " + chatDTO.getMessage());
    }

    @Override
    @Deprecated
    public void onAttach(Activity activity){
        super.onAttach(activity);
        customOnClickListener = (CustomOnClickListener)activity;
    }


    private void removeMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter){
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.remove(chatDTO.getUserName() + ": " + chatDTO.getMessage());
    }

    private void openChat(String chatName){
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1);
        chat_view.setAdapter(adapter);

        databaseReference.child("chat").child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addMessage(dataSnapshot, adapter);

                // Scroll to Bottom
                chat_view.smoothScrollToPosition(adapter.getCount());
                Log.e("LOG","s:" + s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeMessage(dataSnapshot, adapter);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
