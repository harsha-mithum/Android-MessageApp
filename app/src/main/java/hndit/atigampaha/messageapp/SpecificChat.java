package hndit.atigampaha.messageapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SpecificChat extends AppCompatActivity {

    EditText getMessage;
    ImageButton sendMessageBtn;
    CardView senMessageCardView;
    androidx.appcompat.widget.Toolbar toolbar;
    ImageView userImageView;
    TextView userName;
    Intent intent;
    String receiverName, senderName, receiverUid, senderUid;
    FirebaseDatabase firebaseDatabase;
    String senderRoom, receiverRoom;
    ImageButton backBtn;
    RecyclerView recView;
    String currentTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    MessagesAdapter messagesAdapter;
    ArrayList<Messages> messagesArrayList;
    private String enteredMessage;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_chat);

        getMessage = findViewById(R.id.getMessage);
        senMessageCardView = findViewById(R.id.cardViewSpecChat);
        sendMessageBtn = findViewById(R.id.imageViewSendMessage);
        toolbar = findViewById(R.id.toolbarSpecChat);
        userName = findViewById(R.id.specUser);
        userImageView = findViewById(R.id.specUserImageView);
        backBtn = findViewById(R.id.btnBachSpecChat);

        messagesArrayList = new ArrayList<>();
        recView = findViewById(R.id.recView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recView.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(SpecificChat.this, messagesArrayList);
        recView.setAdapter(messagesAdapter);


        intent = getIntent();

        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Toolbar is Clicked", Toast.LENGTH_SHORT).show();


            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");


        senderUid = firebaseAuth.getUid();
        receiverUid = getIntent().getStringExtra("receiveruid");
        receiverName = getIntent().getStringExtra("name");


        senderRoom = senderUid + receiverUid;
        receiverRoom = receiverUid + senderUid;


        DatabaseReference databaseReference = firebaseDatabase.getReference().child("chats").child(senderRoom).child("messages");
        messagesAdapter = new MessagesAdapter(SpecificChat.this, messagesArrayList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Messages messages = snapshot1.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        userName.setText(receiverName);
        String uri = intent.getStringExtra("imageuri");
        if (uri.isEmpty()) {
            Toast.makeText(getApplicationContext(), "null is recieved", Toast.LENGTH_SHORT).show();
        } else {
            Picasso.get().load(uri).into(userImageView);
        }


        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enteredMessage = getMessage.getText().toString();
                if (enteredMessage.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter message first", Toast.LENGTH_SHORT).show();
                } else {
                    Date date = new Date();
                    currentTime = simpleDateFormat.format(calendar.getTime());
                    Messages messages = new Messages(enteredMessage, firebaseAuth.getUid(), date.getTime(), currentTime);
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("chats")
                            .child(senderRoom)
                            .child("messages")
                            .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            firebaseDatabase.getReference()
                                    .child("chats")
                                    .child(receiverRoom)
                                    .child("messages")
                                    .push()
                                    .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }
                    });

                    getMessage.setText(null);

                }

            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        messagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (messagesAdapter != null) {
            messagesAdapter.notifyDataSetChanged();
        }
    }


}