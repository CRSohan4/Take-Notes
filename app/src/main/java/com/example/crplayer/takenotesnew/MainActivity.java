package com.example.crplayer.takenotesnew;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;

    private RecyclerView mNotesList;
    private GridLayoutManager gridLayoutManager;

    private DatabaseReference mNotesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotesList = (RecyclerView) findViewById(R.id.notes_list);


        gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);


        mNotesList.setHasFixedSize(true);
        mNotesList.setLayoutManager(gridLayoutManager);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            mNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(fAuth.getCurrentUser().getUid());
        }

        updateUI();
    }

    @Override
    public void onStart() {
        super.onStart();

        Query query = mNotesDatabase.orderByChild("timestamp");

        FirebaseRecyclerAdapter<NoteModel, NewViewHolder> fRecyclerAdapter = new FirebaseRecyclerAdapter<NoteModel, NewViewHolder>(

                NoteModel.class,
                R.layout.single_note_layout,
                NewViewHolder.class,
                query

        ) {
            @Override
            protected void populateViewHolder(final NewViewHolder viewHolder, NoteModel model, int position) {
                final String noteId = getRef(position).getKey();

                mNotesDatabase.child(noteId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("title") && dataSnapshot.hasChild("timestamp") && dataSnapshot.hasChild("content")) {
                            String title = dataSnapshot.child("title").getValue().toString();
                            String timeStamp = dataSnapshot.child("timestamp").getValue().toString();
                            String content = dataSnapshot.child("content").getValue().toString();

                            viewHolder.setNoteTitle(title);
                            viewHolder.setNoteContent(content);
                            //viewHolder.setNoteTime(timeStamp);    //its a dummy timeStamp

                            GetTimeAgo getTimeAgo = new GetTimeAgo();
                            viewHolder.setNoteTime(GetTimeAgo.getTimeAgo(Long.parseLong(timeStamp), getApplicationContext()));

                            viewHolder.noteCard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                                    intent.putExtra("noteId", noteId);
                                    startActivity(intent);

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };

        mNotesList.setAdapter(fRecyclerAdapter);

    }


    private void updateUI(){
        if(fAuth.getCurrentUser()!=null){
            Log.i("MainActivity", "fAuth != null" );
        }else{
            Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity", "fAauth == null");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.main_new_note_btn:
                Intent mainIntent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivity(mainIntent);
        }
        return true;
    }


}
