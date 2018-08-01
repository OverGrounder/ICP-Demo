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

public class CustomerInSessionActivity extends AppCompatActivity
        implements ChatFragment.CustomOnClickListener {
    final private static String TAG = CustomerInSessionActivity.class.getSimpleName();

    private int PORT_NUM;
    private String STREAM_NAME;
    private String HOST_ADDRESS;
    private String APPLICATION_NAME;
    private String USER_NAME;
    private String USER_EMAIL;
    private String CHAT_NAME;

    /*private C_SessionNormalFragment csn = new C_SessionNormalFragment();
    private C_SessionPListFragment cspl = new C_SessionPListFragment();
    private C_SessionPCheckFragment cspc = new C_SessionPCheckFragment();*/

    private FrameLayout chatContainer;
    private FrameLayout streamSubscribeContainer;
    private FrameLayout productsContainer;

    private ChatFragment cf = new ChatFragment();
    private StreamSubscribeFragment ssf = new StreamSubscribeFragment();
    private ProductListFragment plf = new ProductListFragment();
    private ProductCheckFragment pcf =  new ProductCheckFragment();

    // private ChatFragment cf = new ChatFragment();
    // private StreamSubscribeFragment ssf = new StreamSubscribeFragment();

    Bundle bundle = new Bundle(); // bundle

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_in_session);

        // Get Session and User Info
        Intent intent = getIntent();
        STREAM_NAME = intent.getStringExtra("stream_name");
        HOST_ADDRESS = intent.getStringExtra("host_address");
        APPLICATION_NAME = intent.getStringExtra("application_name");
        USER_EMAIL = intent.getStringExtra("user_email");
        USER_NAME = intent.getStringExtra("user_name");
        PORT_NUM = intent.getIntExtra("port_num", 1935);
        CHAT_NAME = intent.getStringExtra("chat_name");

        Bundle chatBundle = new Bundle();
        chatBundle.putString("chat_name", CHAT_NAME);
        chatBundle.putString("user_email", USER_EMAIL);
        chatBundle.putString("user_name", USER_NAME);
        cf.setArguments(chatBundle);

        Bundle streamBundle = new Bundle();
        streamBundle.putString("stream_name", STREAM_NAME);
        streamBundle.putString("application_name", APPLICATION_NAME);
        streamBundle.putString("host_address", HOST_ADDRESS);
        streamBundle.putInt("port_num", PORT_NUM);

        chatContainer = (FrameLayout)findViewById(R.id.container_chat);
        streamSubscribeContainer = (FrameLayout)findViewById(R.id.container_stream_subscribe);
        productsContainer = (FrameLayout)findViewById(R.id.container_products);

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
