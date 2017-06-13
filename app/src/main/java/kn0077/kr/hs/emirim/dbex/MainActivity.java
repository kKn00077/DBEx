package kn0077.kr.hs.emirim.dbex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    class MyDBHelper extends SQLiteOpenHelper{//class와 똑같으며 class 안에 있는 거라는 것만 다름. 매개변수 전달의 번거로움이 사라지며 필드 사용이 쉽다.

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
            String sql="drop table if exist idolTable"; // 만약 idolTable 테이블이 존재할 경우 삭제하라
            sqLiteDatabase.execSQL(sql);
            onCreate(sqLiteDatabase);
        }

    }
}
