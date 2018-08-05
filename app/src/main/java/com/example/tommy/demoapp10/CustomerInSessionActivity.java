package com.example.tommy.demoapp10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.Serializable;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class CustomerInSessionActivity extends AppCompatActivity
        implements ChatFragment.CustomOnClickListener {
    final private static String TAG = CustomerInSessionActivity.class.getSimpleName();

    private String SESSION_NAME;
    private String USER_NAME;
    private String USER_EMAIL;
    private String URL;

    private FrameLayout chatContainer;
    private FrameLayout streamSubscribeContainer;
    private FrameLayout productsContainer;

    private ChatFragment cf = new ChatFragment();
    private StreamSubscribeFragment ssf = new StreamSubscribeFragment();
    private ProductListFragment plf = new ProductListFragment();
    private ProductCheckFragment pcf =  new ProductCheckFragment();

    Bundle bundle = new Bundle(); // bundle

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_in_session);

        // Get Session and User Info
        Intent intent = getIntent();
        SESSION_NAME = intent.getStringExtra("session_name");
        URL = intent.getStringExtra("url");
        USER_EMAIL = intent.getStringExtra("user_email");
        USER_NAME = intent.getStringExtra("user_name");

        Bundle chatBundle = new Bundle();
        chatBundle.putString("chat_name", SESSION_NAME);
        chatBundle.putString("user_email", USER_EMAIL);
        chatBundle.putString("user_name", USER_NAME);
        cf.setArguments(chatBundle);

        Bundle streamBundle = new Bundle();
        streamBundle.putString("url", URL);
        ssf.setArguments(streamBundle);

        chatContainer = (FrameLayout)findViewById(R.id.container_chat);
        streamSubscribeContainer = (FrameLayout)findViewById(R.id.container_stream_subscribe);
        productsContainer = (FrameLayout)findViewById(R.id.container_products);

        // Set StreamSubscribeContainer's OnClickListener (클릭되면 다시 채팅창이 맨 앞으로오고, 방송창이 full screen이 되며,
        // productContainer가 화면에서 안보이게 됨.
        streamSubscribeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams)streamSubscribeContainer.getLayoutParams();
                params.width = MATCH_PARENT;
                params.height = MATCH_PARENT;
                params.bottomMargin = 0;
                params.rightMargin = 0;
                chatContainer.setVisibility(View.VISIBLE);
                productsContainer.setVisibility(View.INVISIBLE);
                streamSubscribeContainer.bringToFront();
                chatContainer.bringToFront();
            }
        });

        // Add C_SessionNormalFragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_chat, cf);
        ft.replace(R.id.container_stream_subscribe, ssf);
        ft.replace(R.id.container_products, plf);
        ft.commit();
    }

    // View의 height나 width의 dp값을 device에 맞는 pixel값으로 바꾸어줌
    public static int dpToPx(int dp, Context context){
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @Override
    public void onClicked(View view){

        switch (view.getId()){
            case R.id.product_list_btn: {
                chatContainer.setVisibility(View.INVISIBLE);
                productsContainer.bringToFront();
                productsContainer.setVisibility(View.VISIBLE);
                FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams)streamSubscribeContainer.getLayoutParams();
                params.width = dpToPx(150, this);
                params.height = dpToPx(250, this);
                params.bottomMargin = dpToPx(8, this);
                params.rightMargin = dpToPx(8, this);
                streamSubscribeContainer.bringToFront();
                break;
            }
        }
    }
}
