package kn0077.kr.hs.emirim.dbex;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button butInit, butInsert, butSelect, butUpdate;
    EditText editName, editCount,editResultName, editResultCount;
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butInit=(Button)findViewById(R.id.but_init);
        butInsert=(Button)findViewById(R.id.but_insert);
        butSelect=(Button)findViewById(R.id.but_select);
        butUpdate=(Button)findViewById(R.id.but_update);
        editName=(EditText)findViewById(R.id.edit_group_name);
        editCount=(EditText)findViewById(R.id.edit_group_count);
        editResultName=(EditText)findViewById(R.id.edit_result_name);
        editResultCount=(EditText)findViewById(R.id.edit_result_count);

        //DB생성
        myHelper=new MyDBHelper(this);

        //기존의 테이블이 존재하면 삭제하고 테이블을 새로 생성한다.
        butInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB=myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB,1,2);
                sqlDB.close();
            }
        });

        butInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB=myHelper.getWritableDatabase();
                String sql="insert into idolTable values('"+editName.getText()+"',"+editCount.getText()+")";
                sqlDB.execSQL(sql);
                sqlDB.close();
                Toast.makeText(MainActivity.this, "저장됨",Toast.LENGTH_LONG).show();
            }
        });

        butUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB=myHelper.getWritableDatabase();
                String sql="update idolTable set idolCount="+editCount.getText()+" where idolName='"+editName.getText()+"'"; // 아이돌 테이블에 있는 아이돌카운트의 값을 수정한다. 어디 컬럼의 카운트? 아이돌 네임이 editName인 카운트
                sqlDB.execSQL(sql);
                sqlDB.close();
                Toast.makeText(MainActivity.this, "수정됨",Toast.LENGTH_LONG).show();
            }
        });


        butSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB=myHelper.getReadableDatabase();
                String sql="select * from idolTable";
                Cursor cursor = sqlDB.rawQuery(sql,null);
                String names="Idol 이름"+"\r\n"+"============="+"\r\n";
                String counts="Idol 인원수"+"\r\n"+"============="+"\r\n";
                while(cursor.moveToNext()!=false){
                    names+=cursor.getString(0)+"\r\n"; //index 0. 첫번째 컬럼(열)의 값을 환
                    counts += cursor.getInt(1)+"\r\n"; //index 1
                }
                editResultName.setText(names);
                editResultCount.setText(counts);
                cursor.close();
                sqlDB.close();
            }
        });

    }

    class MyDBHelper extends SQLiteOpenHelper{//class와 똑같으며 class 안에 있는 거라는 것만 다름. 매개변수 전달의 번거로움이 사라지며 변수 사용이 쉽다.

        public MyDBHelper(Context context) { //idolDB라는 이름의 데이터베이스가 생성된다.
            super(context, "idolDB", null, 1/*첫번째 버전. 같은 이름의 DB가 있으면 또 다시 새로운 DB가 생성되지 않는다. 즉 똑같은 DB로 쭈욱 간다. 만약 똑같은 이름의 새로운 DB로 싹 바꾸고 싶다면 버전만 바꿔주면 됨.*/); //
        }

        //idolTable 라는 이름의 테이블 생성
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql="create table idolTable(idolName text not null primary key, idolCount integer)";
            sqLiteDatabase.execSQL(sql);
        }

        // 이미 idolTable이 존재한다면 기존의 테이블을 삭제하고 새로 테이블을 만들 때
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            String sql="drop table if exists idolTable"; // 만약 idolTable 테이블이 존재할 경우 삭제하라
            sqLiteDatabase.execSQL(sql);
            onCreate(sqLiteDatabase);
        }

    }
}
